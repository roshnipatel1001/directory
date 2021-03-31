package com.si.directory_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.si.directory_app.R;
import com.si.directory_app.adapters.ProfileFacilityAdapter;
import com.si.directory_app.adapters.ProfileGalleryAdapter;
import com.si.directory_app.adapters.ProfileProductAdapter;
import com.si.directory_app.adapters.SpottedSaleAdapter;
import com.si.directory_app.adapters.TimeAdapter;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.ActivityRetailerProfileBinding;
import com.si.directory_app.models.profile.ProfileFacilityModel;
import com.si.directory_app.models.profile.ProfileGalleryModel;
import com.si.directory_app.models.profile.ProfileProductModel;
import com.si.directory_app.models.profile.Retailer;
import com.si.directory_app.models.profile.RetailerProfileModel;
import com.si.directory_app.models.profile.SpottedSale;
import com.si.directory_app.models.profile.Time;
import com.si.directory_app.retro.JsonUtil;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.EqualSpacing;
import com.si.directory_app.utills.ImageAdapter;
import com.si.directory_app.utills.Session;
import com.si.directory_app.utills.Utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailerProfileActivity extends AppCompatActivity {
    private ActivityRetailerProfileBinding binding;
    private Context context;
    private static final String TAG = "RetailerProfileActivity";
    private List<ProfileProductModel> profileProductModelList = new ArrayList<>();
    private ProfileProductAdapter profileProductAdapter;
    private List<ProfileGalleryModel> profileGalleryModelList = new ArrayList<>();
    private ProfileGalleryAdapter profileGalleryAdapter;
    private List<ProfileFacilityModel> profileFacilityModelList = new ArrayList<>();
    private ProfileFacilityAdapter profileFacilityAdapter;
    private List<Time> timeList = new ArrayList<>();
    private TimeAdapter timeAdapter;
    private List<SpottedSale> spottedSaleList = new ArrayList<>();
    private SpottedSaleAdapter spottedSaleAdapter;
    private String id,rating;
    private Gson gson;
    private boolean clicked;
    private long val = 1;
    private Retailer retailer;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     binding = DataBindingUtil.setContentView(this,R.layout.activity_retailer_profile);
     gson = new Gson();
     retailer = new Retailer();
     context = this;
     inIt();
    }

    private void inIt(){


        Intent intent = getIntent();

        if (getIntent().getStringExtra("retailerId")!=null){

            id = getIntent().getStringExtra("retailerId");


        }else if (intent.getStringExtra("typeId")!=null){

            id = getIntent().getStringExtra("typeId");
        }


        user_retailer_details();
        setupRv();
        onClick();
    }

    private  void  onClick(){

        binding.btRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating_popup();
            }
        });

        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void setupRv() {

        profileProductAdapter = new ProfileProductAdapter(context, profileProductModelList);
        GridLayoutManager GridLayoutManager = new GridLayoutManager(context,2);
        binding.productRecycler.setLayoutManager(GridLayoutManager);
        binding.productRecycler.setHasFixedSize(true);
        binding.productRecycler.setAdapter(profileProductAdapter);
       profileProductAdapter.notifyDataSetChanged();


       profileGalleryAdapter = new ProfileGalleryAdapter(context,profileGalleryModelList,retailer);
        GridLayoutManager GridLayoutManager1 = new GridLayoutManager(context,2);
       binding.galleryRecycler.setLayoutManager(GridLayoutManager1);
       binding.galleryRecycler.setHasFixedSize(true);
        binding.galleryRecycler.setAdapter(profileGalleryAdapter);
        profileGalleryAdapter.notifyDataSetChanged();

        profileFacilityAdapter = new ProfileFacilityAdapter(context,profileFacilityModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.facilityRecycler.setLayoutManager(linearLayoutManager);
        binding.facilityRecycler.setHasFixedSize(true);
        binding.facilityRecycler.setAdapter(profileFacilityAdapter);
        profileFacilityAdapter.notifyDataSetChanged();

        timeAdapter = new TimeAdapter(context,timeList);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        binding.timeRecycler.setLayoutManager(linearLayoutManager2);
        binding.timeRecycler.addItemDecoration(new EqualSpacing(8,context));
        binding.timeRecycler.setHasFixedSize(true);
        binding.timeRecycler.setAdapter(timeAdapter);
        timeAdapter.notifyDataSetChanged();


        spottedSaleAdapter = new SpottedSaleAdapter(context,spottedSaleList);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context);
        binding.saleRecycler.setLayoutManager(linearLayoutManager3);
        binding.saleRecycler.setHasFixedSize(true);
        binding.saleRecycler.setAdapter(spottedSaleAdapter);
        spottedSaleAdapter.notifyDataSetChanged();


    }

    private void rating_popup() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.rating_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RatingBar ratingBar = dialog.findViewById(R.id.rating);

        dialog.findViewById(R.id.ivCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });

        dialog.findViewById(R.id.btSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 rating  = String.valueOf(ratingBar);

                 user_rating();

                 dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void user_retailer_details(){
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please wait..");
        pd.show();


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE,Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY,Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID,Session.getId(context));
        hashMap.put(Appconst.RETAILER_ID,id);

        Log.e(TAG, "user_retailer_details: "+hashMap);


        Retro.service(context).user_retailer_details(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                RetailerProfileModel profileModel = gson.fromJson(JsonUtil.resp(response, TAG), RetailerProfileModel.class);

                if (profileModel.getStatus() == 1) {
                  //  Toast.makeText(context, profileModel.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                    binding.ivName.setText(profileModel.getData().getRetailer().getFullName());
                    binding.tvRating.setText(profileModel.getData().getRetailer().getRating()+" Rating");
                    binding.tvAddress.setText(profileModel.getData().getRetailer().getAddress());

                    String url = Retro.IMAGE_BASEURL + "users" + "/" + profileModel.getData().getRetailer().getId() + "/" + profileModel.getData().getRetailer().getProfileImg();

                    Log.e(TAG, "onBindViewHolder: " + url);

                    ImageAdapter.profile(binding.ivRetailer, url);



                    profileProductAdapter.setProfileProductModelList(profileModel.getData().getCategory());
                    profileFacilityAdapter.setProfileFacilityModelList(profileModel.getData().getFacilities());
                    profileGalleryAdapter.setProfileGalleryModelList(profileModel.getData().getGallery(),profileModel.getData().getRetailer());
                    timeAdapter.setTimeList(profileModel.getData().getTime());
                    spottedSaleAdapter.setSpottedSaleList(profileModel.getData().getSpottedSale());


                    if (profileModel.getData().getRetailer().getSubstatus().equals(val)) {

                        binding.btLike.setImageResource(R.drawable.ic_white_heart);


                    } else {
                        binding.btLike.setImageResource(R.drawable.ic_like);

                    }


                    binding.btLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // subscribe(mSubCategoryModel.getId());

                            if (clicked) {
                                clicked = false;
                                binding.btLike.setImageResource(R.drawable.ic_like);
                                subscribe(profileModel.getData().getRetailer().getId());


                            } else {
                                clicked = true;
                                binding.btLike.setImageResource(R.drawable.ic_white_heart);
                                success_popup();
                                subscribe(profileModel.getData().getRetailer().getId());


                            }

                        }
                    });



                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 0; i< profileModel.getData().getStaff().size();i++){
                        stringBuilder.append(profileModel.getData().getStaff().get(i).getName()+" ,");

                    }

                   binding.tvStaff.setText(stringBuilder.toString());


                    Log.e(TAG, "onResponse:ProfileFacilityModel"+profileModel.getData().getFacilities());


                }else {
                    Toast.makeText(context, profileModel.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void success_popup() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.subscribe_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.findViewById(R.id.btOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void user_rating(){


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE,Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY,Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID,Session.getId(context));
        hashMap.put(Appconst.RETAILER_ID,id);
        hashMap.put(Appconst.CATEGORY_ID,Session.getSubId(context));
        hashMap.put(Appconst.RATING ,rating);

        Retro.service(context).user_rating(hashMap).enqueue(new Callback<ResponseBody>() {
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
                          Toast.makeText(context, message, Toast.LENGTH_SHORT).show();




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


    private void subscribe(String id) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID, Session.getId(context));
        hashMap.put(Appconst.TYPE, "retailer");
        hashMap.put(Appconst.TYPE_ID, id);

        Log.e(TAG, "get_subscribe" + hashMap);

        Retro.service(context).subscribe(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String res = null;
                try {
                    res = response.body().string().trim();
                    JSONObject jsonObject = new JSONObject(res);
                    Integer stat = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    Log.e(TAG, "onResponse" + res);


                    if (stat == 1) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                        String data = jsonObject.getString("data");





                    } else{
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse" + res);
                    }

                    //  notifyItemChanged(pos);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "onResponseChange: " + res);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }


}


