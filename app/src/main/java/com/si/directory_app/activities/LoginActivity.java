package com.si.directory_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.si.directory_app.R;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.ActivityLoginBinding;
import com.si.directory_app.models.login.LoginModel;
import com.si.directory_app.models.registration.RegistrationModel;
import com.si.directory_app.retro.JsonUtil;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.Session;
import com.si.directory_app.utills.Utility;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding ;
    private Context context;
    private String email = "", password = "";
    private static final String TAG = "LoginActivity";
    private Gson gson;
    private String token,image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        context = this;
        gson = new Gson();

        inIt();

    }

    private void inIt() {
        generate_access_token();
        textWatcher();
        onClick();

    }

    private void onClick() {
        binding.llSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SignupActivity.class));
            }
        });

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();

            }
        });

        binding.btForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,ForgetPassActivity.class));
            }
        });

        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,WalkOverActivity.class));
                finish();
            }
        });

    }
    private void validation() {
        binding.tiEmail.setError(null);
        binding.tiPass.setError(null);

        String email = binding.etEmail.getText().toString().trim();
        String pass = binding.etPAss.getText().toString().trim();

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


        if (pass.isEmpty()) {
            x = false;
            binding.tiPass.setError(getString(R.string.error_empty_pass));
            binding.tiPass.requestFocus();

        } else if (pass.length() < 8) {
            x = false;
            binding.tiPass.setError(getString(R.string.error_length_pass));
            binding.tiPass.requestFocus();

        }

        if (x) {
            /** Api call*/

            login(email,pass);

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
                    binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_right,0);
                } else {
                    binding.tiEmail.setError("Please enter valid Email");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.etPAss.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    binding.tiPass.setError("Please enter Password");
                } else if (s.length() < 8) {
                    binding.tiPass.setError("Please should 8 character long");
                } else {
                    binding.tiPass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                        //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        token = json.getString("access_token");
                        Session.setAccessToken(context,token);
                        Log.e(TAG, "onResponse: tokenn"+token);


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





    private void login(String email,String pass){


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.EMAIL,email);
        hashMap.put(Appconst.PASSWORD,pass);
        hashMap.put(Appconst.ACCESS,"user");

        Log.e(TAG, "login: "+hashMap);

        Retro.service(context).login(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e(TAG, "onResponse===: "+response);


                LoginModel loginModel = gson.fromJson(JsonUtil.resp(response,TAG), LoginModel.class);
                if (loginModel.getStatus() == 1){
                   // Toast.makeText(context, loginModel.getMessage(), Toast.LENGTH_SHORT).show();

                    Session.setId(context, loginModel.getData().getId());
                    Session.setEmail(context,loginModel.getData().getEmail());
                    Session.setName(context,loginModel.getData().getFullName());
                   Session.setPass(context,loginModel.getData().getPassword());
                   Session.setAddress(context,loginModel.getData().getAddress());
                   Session.setProfile_pic(context,loginModel.getData().getProfileImg());


                   Intent intent = new Intent(context, DashBordActivity.class);

                    startActivity(intent);
                    finish();


                }else {
                    Toast.makeText(context, loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}
