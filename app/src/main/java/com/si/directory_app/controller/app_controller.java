package com.si.directory_app.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


public class app_controller extends MultiDexApplication {
    private static final String TAG = "Controller";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //RealmController.InitRealm(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e(TAG, "onTrimMemory: here we are 1 " );
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onTrimMemory: here we are 2 " );
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Log.e(TAG, "onTrimMemory: here we are 3 " );

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //ofline();
        Log.e(TAG, "onTrimMemory: here we are " );
    }

    /** APi Offline Called Here.....*/
    /*private void ofline() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.DEVICE_ID, Utility.getDeviceId(getApplicationContext()));
        hashMap.put(AppConstants.DEVICE_TYPE, AppConstants.DEVICE_TYPE_VALUE);
        hashMap.put(AppConstants.API_KEY, AppConstants.API_KEY_VALUE);
        hashMap.put(AppConstants.ACCESS_TOKEN, Session.getAcceccToken(getApplicationContext()));

        hashMap.put(AppConstants.USER_ID, Session.getUId(getApplicationContext()));

        Log.e(TAG, "offline key(): " + hashMap);

        RetrofitClient.service(getApplicationContext()).offline_status(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String respo = null;
                try {

                    respo = response.body().string().trim();
                    Log.e(TAG, "onResponse: offline = " + respo);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: Failure Api ");
            }
        });

    }*/

}
