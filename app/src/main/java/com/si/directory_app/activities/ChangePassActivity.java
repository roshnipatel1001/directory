package com.si.directory_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.si.directory_app.R;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.ActivityChangePassBinding;
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

public class ChangePassActivity extends AppCompatActivity {

    private Context context;
    private ActivityChangePassBinding binding;
    private static final String TAG = "ChangePassActivity";
    private  String newPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_change_pass);
        context= this;
        inIt();

    }

    private  void inIt(){
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

        String password = binding.etPAss.getText().toString().trim();
        String cnfpassword = binding.etConfirm.getText().toString().trim();
        String oldPassword = binding.etOld.getText().toString().trim();


        boolean correct = false;
        View focusview = null;



        if (TextUtils.isEmpty(password)) {
            correct = true;
            binding.tiPass.setError("Please enter password");
            focusview = binding.tiPass;
        } else if (password.length() < 8) {
            correct = true;
            binding.tiPass.setError("Password must be 8 characters");
            focusview = binding.tiPass;
        }
        if (TextUtils.isEmpty(oldPassword)) {

            correct = true;
            binding.tiOld.setError("old password is required!");
            focusview = binding.tiOld;
        } else if (oldPassword.length() < 8){

            correct = true;
            binding.tiOld.setError("Old password must be 8 characters");
            focusview = binding.tiOld;
        }

        if (TextUtils.isEmpty(cnfpassword)) {
            correct = true;
            binding.tiConfirm.setError("Confirm Password can not be empty!");
            focusview = binding.tiConfirm;
        } else if (!password.equals(cnfpassword)) {
            correct = true;
            binding.tiConfirm.setError("Confirm password doesn't match");
            focusview = binding.tiConfirm;
        }


        if (TextUtils.isEmpty(password)) {
            correct = true;
            binding.tiOld.setError("Please enter password");
            focusview = binding.tiOld;
        } else if (password.length() <  8) {
            correct = true;
            binding.tiOld.setError("Password must be 8 characters");
            focusview = binding.tiOld;
        }

        if (correct) {
            focusview.requestFocus();
        } else {
            //api
            change_password_user(password,oldPassword);
        }
    }

    private void textWatcher() {

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



        binding.etConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (!binding.etPAss.getText().toString().trim().equalsIgnoreCase(binding.etConfirm.getText().toString().trim())) {
                        binding.tiConfirm.setError("Confirm password doesn't match");
                    } else {
                        binding.tiConfirm.setError(null);
                    }
                } else {
                    binding.tiConfirm.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        binding.etOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    binding.tiOld.setError("Please enter your old password");
                } else if (s.length() < 8) {
                    binding.tiOld.setError("Old password must be 8 characters");
                } else {
                    binding.tiOld.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void change_password_user(String password , String oldPassword) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.PASSWORD, password);
        hashMap.put(Appconst.OldPass,oldPassword);
        hashMap.put(Appconst.UID, Session.getId(context));
        Log.e(TAG, "change_password_user: " + hashMap);

        Retro.service(context).change_password_user(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res = response.body().string().trim();
                    Log.e(TAG, "onResponseChange: "+res );
                    JSONObject jsonObject = new JSONObject(res);
                    Integer stat = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    Log.e(TAG, "onResponse" + res);


                    if (stat == 1) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, DashBordActivity.class);
                        intent.putExtra("change","1");
                        startActivity(intent);
                        finish();


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
