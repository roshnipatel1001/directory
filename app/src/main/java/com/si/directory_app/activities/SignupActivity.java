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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.si.directory_app.R;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.ActivitySignupBinding;
import com.si.directory_app.databinding.UploadCellBinding;
import com.si.directory_app.models.registration.RegistrationModel;
import com.si.directory_app.retro.JsonUtil;
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

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private Context context;
    private static final String TAG = "SignupActivity";
    private String email = "",pass = "", name= "",token="",popup= "";
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup);
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
   binding.btSignup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        validation();


     //  success_popup();
    }
});

   binding.llSignIn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           startActivity(new Intent(context,LoginActivity.class));
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
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPAss.getText().toString().trim();
        String cnfpassword = binding.etConPass.getText().toString().trim();

        boolean correct = false;
        View focusview = null;

        if (TextUtils.isEmpty(name)) {
            correct = true;
            binding.tiNAme.setError("Please enter name");
            focusview = binding.tiNAme;
        }

        if (TextUtils.isEmpty(email)) {
            correct = true;
            binding.tiEmail.setError("Please enter email");
            focusview = binding.tiEmail;
        } else if ((!Utility.isValidEmail(email))) {
            correct = true;
            binding.tiEmail.setError("Invalid Email!");
            focusview = binding.tiEmail;
        }


        if (TextUtils.isEmpty(password)) {
            correct = true;
            binding.tiPass.setError("Please enter password");
            focusview = binding.tiPass;
        } else if (password.length() < 8) {
            correct = true;
            binding.tiPass.setError("Password must be 8 characters");
            focusview = binding.tiPass;
        }
        if (TextUtils.isEmpty(cnfpassword)) {
            correct = true;
            binding.tiConPass.setError("Confirm Password can not be empty!");
            focusview = binding.tiConPass;
        } else if (!password.equals(cnfpassword)) {
            correct = true;
            binding.tiConPass.setError("Confirm password doesn't match");
            focusview = binding.tiConPass;
        }

        if (correct) {
            focusview.requestFocus();
        } else {
           registration(name,email,password);
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

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    binding.tiNAme.setError("Please enter Name");
                }
                else {
                    binding.tiNAme.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.etConPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (!binding.etPAss.getText().toString().trim().equalsIgnoreCase(binding.etConPass.getText().toString().trim())) {
                        binding.tiConPass.setError("Confirm password doesn't match");
                    } else {
                        binding.tiConPass.setError(null);
                    }
                } else {
                    binding.tiConPass.setError(null);
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

                    if (stat == 1) {
                       // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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


    private void registration(String name,String email,String password){

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.ACCESS_TOKEN,Session.getAccessToken(context));
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.EMAIL,email);
        hashMap.put(Appconst.PASSWORD,password);
        hashMap.put(Appconst.NAME,name);

        Log.e(TAG, "registration: "+hashMap);

        Retro.service(context).registration(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                RegistrationModel registrationModel = gson.fromJson(JsonUtil.resp(response,TAG), RegistrationModel.class);
                if (registrationModel.getStatus() == 1){
                   // Toast.makeText(context, registrationModel.getMessage(), Toast.LENGTH_SHORT).show();

                    Session.setId(context, registrationModel.getData().getId());
                    Log.e(TAG, "onResponse: ====="+registrationModel.getData().getId());
                    Session.setEmail(context,registrationModel.getData().getEmail());
                    Session.setName(context,registrationModel.getData().getFullName());
                    Session.setPass(context,registrationModel.getData().getPassword());

                    Intent intent = new Intent(context, DashBordActivity.class);
                    intent.putExtra("a","1");
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(context, registrationModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });




    }




}
