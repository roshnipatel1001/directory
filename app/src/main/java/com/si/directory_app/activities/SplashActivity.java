package com.si.directory_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.si.directory_app.R;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.ActivitySplashBinding;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.Session;
import com.si.directory_app.utills.Utility;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding ;
    private Context context;
    private String token,id;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        context = this;
        inIt();

    }

    private void inIt() {
        handler();
    }

    private void handler() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

               check();

            }
        }, 2000);
    }


    private  void check(){
        id = Session.getId(context);

        if (id.equals("")){
            Log.e(TAG, "check");

            generate_access_token();

        }
        else {

            startActivity(new Intent(context, DashBordActivity.class));
            finishAffinity();
            Log.e(TAG, "uncheck" +id);
        }


    }

    private void generate_access_token(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);


        Retro.service(context).generate_access_token(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG, "onResponse: " + response);
                String res = null;
                try {
                    res = response.body().string().trim();
                    JSONObject json = new JSONObject(res);

                    Integer stat = json.getInt("status");
                    String message = json.getString("message");
                    Log.e(TAG, "onResponse" + res);
                    Log.e(TAG, "onResponse: "+stat);

                    if (stat == 1) {
                     //   Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        token = json.getString("access_token");
                        Session.setAccessToken(context,token);
                        Log.e(TAG, "onResponse: tokenn"+token);

                        Intent intent = new Intent(context,WalkOverActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else {

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse" + res);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "onResponse" + res);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



}
