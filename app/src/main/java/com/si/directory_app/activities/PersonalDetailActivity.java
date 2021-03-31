package com.si.directory_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.si.directory_app.R;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.ActivityPersonalDetailBinding;
import com.si.directory_app.fragments.ProfileFragment;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.Session;
import com.si.directory_app.utills.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDetailActivity extends AppCompatActivity {
    private ActivityPersonalDetailBinding binding;
    private Context context;
    private static final String TAG = "PersonalDetailActivity";
    Double latitude = 0.0, longitude = 0.0;
    private String ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     binding = DataBindingUtil.setContentView(this,R.layout.activity_personal_detail);
     context = this;
     inIt();
    }

    private void  inIt(){
        initializeTextWatcher();

        binding.etName.setText(Session.getName(context));
        binding.etEmail.setText(Session.getEmail(context));
        binding.etAdd.setText(Session.getAddress(context));
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
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String address = binding.etAdd.getText().toString().trim();

        String latlong = getAddressFromLocation(address,context);

        Log.e(TAG, "validation: "+latlong);


        Log.e(TAG, "validation: "+latitude );
        Log.e(TAG, "validation: "+longitude);


        boolean correct = false;
        View focusview = null;

        if (TextUtils.isEmpty(name)) {
            correct = true;
            binding.tiName.setError("Please enter name");
            focusview = binding.tiName;
        }
        if (!TextUtils.isEmpty(email)) {
            if ((!Utility.isValidEmail(email))) {
                correct = true;
                binding.tiEmail.setError("Invalid Email!");
                focusview = binding.etEmail;
            }
        }
        if (TextUtils.isEmpty(address)) {
            correct = true;
            binding.tiAdd.setError("Please enter your address");
            focusview = binding.tiAdd;
        }

        if (correct) {
            focusview.requestFocus();
        } else {
           updated_persnal_detail(name,email,address);
        }
    }



    private void initializeTextWatcher() {
        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 0) {
                    binding.tiName.setError("Name can not be empty");

                } else {

                    binding.tiName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        binding.etAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 0) {
                    binding.tiAdd.setError("Address can not be empty");

                } else {

                    binding.tiAdd.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = String.valueOf(s);
                if (email.length() > 0) {
                    if (Utility.isValidEmail(email)) {
                        binding.tiEmail.setError(null);
                        binding.tiEmail.setEndIconDrawable(R.drawable.ic_right);
                    } else {
                        binding.tiEmail.setError("Please enter valid email");
                        binding.tiEmail.setEndIconDrawable(null);
                    }
                } else {
                    binding.tiEmail.setError(null);
                }
            }
        });
    }



    private  void  updated_persnal_detail(String name ,String email , String address){


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN,Session.getAccessToken(context));
        hashMap.put(Appconst.EMAIL,email);
        hashMap.put(Appconst.UID,Session.getId(context));
        hashMap.put(Appconst.NAME,name);
        hashMap.put(Appconst.ADDRESS,address);
        hashMap.put(Appconst.LATITUDE,latitude+"");
        hashMap.put(Appconst.LONGITUDE,longitude+"");


        Log.e(TAG, ": updated_persnal_detail" + hashMap);

        Retro.service(context).updated_persnal_detail(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String res = null;

                try {
                    res = response.body().string().trim();
                    JSONObject json = new JSONObject(res);

                    Integer stat = json.getInt("status");
                    String message = json.getString("message");
                    Log.e(TAG, "onResponse: " + res);
                    Log.e(TAG, "onResponse=====" + stat);
                    JSONArray data = json.getJSONArray("data");
                    JSONObject dataobj = data.getJSONObject(0);


                    if (stat == 1) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponseUpdate: "+res);
                       Session.setName(context,dataobj.getString(Appconst.NAME));
                       Session.setEmail(context,dataobj.getString(Appconst.EMAIL));
                       Session.setAddress(context,dataobj.getString(Appconst.ADDRESS));
                        Intent intent = new Intent(context, DashBordActivity.class);
                        intent.putExtra("detail","1");
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


    public String getAddressFromLocation(final String locationAddress, final Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String result = null;
        try {
            List addressList = geocoder.getFromLocationName(locationAddress, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = (Address) addressList.get(0);
                StringBuilder sb = new StringBuilder();
                sb.append(address.getLatitude()).append("\n");
                sb.append(address.getLongitude()).append("\n");

                latitude = address.getLatitude();
                longitude = address.getLongitude();

                ss = String.valueOf(address.getLatitude())+","+String.valueOf(address.getLongitude());


            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to connect to Geocoder", e);
        }
        return ss;
    }
}
