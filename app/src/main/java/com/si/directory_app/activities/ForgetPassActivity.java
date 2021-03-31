package com.si.directory_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.si.directory_app.R;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.ActivityForgetPassBinding;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.Session;
import com.si.directory_app.utills.Utility;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassActivity extends AppCompatActivity {
    private Context context;
    private ActivityForgetPassBinding binding;
    private static final String TAG = "ForgetPassActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_forget_pass);
        context = this;
        inIt();
    }

    private void inIt(){

        textWatcher();
        onClick();


    }

    private void onClick(){
        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();


            }
        });

    }


    private void validation() {
        binding.tiEmail.setError(null);

        String email = binding.etEmail.getText().toString().trim();


        boolean x = true;

        if (email.isEmpty()) {
            x = false;
            binding.tiEmail.setError(getString(R.string.error_empty_email));
            binding.tiEmail.requestFocus();
        } else if (!Utility.isValidEmail(email)) {
            x = false;
            binding.tiEmail.setError(getString(R.string.error_valid_email));
            binding.tiEmail.requestFocus();
        }


        if (x) {
            /** Api call*/
           forgot_password_request(email);
        }
    }

    private void textWatcher() {

        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Utility.isValidEmail(String.valueOf(s))) {
                    binding.tiEmail.setError(null);
                    binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_right, 0);
                } else {
                    binding.tiEmail.setError("Please enter valid Email");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void success_popup() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.forgt_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.findViewById(R.id.btOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(context,LoginActivity.class));
                finish();

            }
        });

        dialog.show();
    }


    private void forgot_password_request(String email){


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN,Session.getAccessToken(context));
        hashMap.put(Appconst.EMAIL,email);

        Log.e(TAG, "forgot_password: " + hashMap);

        Retro.service(context).forgot_password_request(hashMap).enqueue(new Callback<ResponseBody>() {
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

                    if (stat == 1) {
                      //  Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                        success_popup();

                        //startActivity(new Intent(context,SetPasswordActivity.class));


                    }else {

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse" + res);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
