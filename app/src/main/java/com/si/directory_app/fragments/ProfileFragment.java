package com.si.directory_app.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.si.directory_app.R;
import com.si.directory_app.activities.ChangePassActivity;
import com.si.directory_app.activities.DashBordActivity;
import com.si.directory_app.activities.LoginActivity;
import com.si.directory_app.activities.PersonalDetailActivity;
import com.si.directory_app.activities.ShortlistedActivity;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.FragmentProfileBinding;
import com.si.directory_app.databinding.UploadCellBinding;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.CameraGalleryComman;
import com.si.directory_app.utills.ImageAdapter;
import com.si.directory_app.utills.Session;
import com.si.directory_app.utills.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    public ProfileFragment() {
    }

    private FragmentProfileBinding binding;
    private static final String TAG = "ProfileFragment";
    private CameraGalleryComman cameraGalleryComman;
    private Context context;
    private View view;
    private File dp;
    private String dpName = "";
    private String profile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        context = getActivity();
        view = binding.getRoot();


        inIt();
        return view;

    }

    private void inIt() {

        onClick();

        binding.tvName.setText(Session.getName(context));
        binding.tvName2.setText(Session.getName(context));
        binding.tvEmail.setText(Session.getEmail(context));

        String url = Retro.PROFILE_IMAGE+Session.getId(context)+"/"+Session.getProfile_pic(context);
        ImageAdapter.profile(binding.ivProfile,url);

        Log.e(TAG, "inIt: "+Session.getProfile_pic(context));


        cameraGalleryComman = new CameraGalleryComman(context, this);
        cameraGalleryComman.setEnableFreeStyleCrop(true);


    }


    private void onClick() {

        binding.ivCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDialog();
            }
        });

        binding.changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ChangePassActivity.class));
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout_popup();
            }
        });

        binding.shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ShortlistedActivity.class));
            }
        });

        binding.llEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PersonalDetailActivity.class));
            }
        });

        binding.llName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, PersonalDetailActivity.class));

            }
        });


    }


    private void uploadDialog() {
        //View dialogView = getLayoutInflater().inflate(R.layout.category_sheet, null);
        final UploadCellBinding order = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.upload_cell, (ViewGroup) binding.getRoot(), false);
        final BottomSheetDialog dialog = new BottomSheetDialog(context);


        order.llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraGalleryComman.pickFromGallery();
                dialog.dismiss();
            }
        });
        order.llRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_image();
                dialog.dismiss();
            }
        });

        order.llCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraGalleryComman.onLaunchCamera();
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setContentView(order.getRoot());
        dialog.show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraGalleryComman.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Log.e(TAG, "onResumepicc: ================"+cameraGalleryComman.outputImageFile().toString() );


        File Image_Path = null;
        if (cameraGalleryComman.outputImageFile() != null) {

            Log.e(TAG, "onResumePic: " + cameraGalleryComman.outputImageFile());
           // Log.e(TAG, "onResumePic: before -> " + cameraGalleryComman.outputImageFile().length() / 1024 + "  kb");

            try {

                if ((cameraGalleryComman.outputImageFile().length() / 1024) < 1024) {
                    Image_Path = cameraGalleryComman.outputImageFile();
                } else if ((cameraGalleryComman.outputImageFile().length() / 1024) > 1024 && (cameraGalleryComman.outputImageFile().length() / 1024) < 2048) {
                    Image_Path = new Compressor(context).setQuality(50).compressToFile(cameraGalleryComman.outputImageFile());
                } else if ((cameraGalleryComman.outputImageFile().length() / 1024) > 3072) {
                    Image_Path = new Compressor(context).setQuality(75).compressToFile(cameraGalleryComman.outputImageFile());
                }
                dp = Image_Path;

            } catch (IOException e) {
                e.printStackTrace();
            }

           // Log.e(TAG, "onResume: after -> " + Image_Path.length() / 1024 + "  kb");

            if (dp != null) {
                dpName = dp.getName();
                ImageAdapter.loadProfileImageFile(binding.ivProfile,Image_Path);
                update_profile_image();
            }


        }
    }


    private void logout() {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.UID, Session.getId(context));

        Log.e(TAG, "logoutapi: " + hashMap);

        Retro.service(context).logout(hashMap).enqueue(new Callback<ResponseBody>() {
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

                        Session.clearSession(context);
                        Utility.clearAllActivity(context, LoginActivity.class);


                    } else {

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

    /**
     * Api UpdateDog Called
     */
    private void update_profile_image() {

        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please wait..");
        pd.show();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        builder.addFormDataPart(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        builder.addFormDataPart(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        builder.addFormDataPart(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        builder.addFormDataPart(Appconst.UID, Session.getId(context));


        if (!dpName.equals("")) {
            builder.addFormDataPart("image", dp.getName(), RequestBody.create(MediaType.parse("image*//*"), dp));
        }

        Log.e(TAG, "Action key(): " + builder);
        final MultipartBody requestbody = builder.build();

        Retro.service(context).update_profile_image(requestbody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String respo = null;
                try {
                    pd.dismiss();
                    respo = response.body().string().trim();
                    Log.e(TAG, "onResponse: Action = " + respo);
                    JSONObject jsonObject = new JSONObject(respo);
                    if (jsonObject.getString("status").equals("1")) {
                        String image = jsonObject.getString("data");

                        String url = Retro.PROFILE_IMAGE+Session.getId(context)+"/"+image;
                        Log.e(TAG, "onResponse: "+url );
                        ImageAdapter.profile(binding.ivProfile,url);
                        Session.setProfile_pic(context,image);

                        Log.e(TAG, "onResponsePic: "+image);
                    } else if (jsonObject.getString("status").equals("0")) {
                        pd.dismiss();
                        Toast.makeText(context, jsonObject.getString("response_message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: Failure Api ");
            }
        });
    }


    private void logout_popup() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.logout_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.btNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();
                // dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void delete_image(){


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.UID, Session.getId(context));
        hashMap.put(Appconst.TYPE,"profile");

        Log.e(TAG, "delete_image: " + hashMap);

        Retro.service(context).delete_image(hashMap).enqueue(new Callback<ResponseBody>() {
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
                         Session.setProfile_pic(context,"");




                    } else {

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
