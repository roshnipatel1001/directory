package com.si.directory_app.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.si.directory_app.R;
import com.si.directory_app.adapters.NotificationAdapter;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.FragmentNotificationBinding;
import com.si.directory_app.models.notification.NotificationModel;
import com.si.directory_app.models.notification.NotificationsModel;
import com.si.directory_app.models.search.SearchingModel;
import com.si.directory_app.retro.JsonUtil;
import com.si.directory_app.retro.Retro;
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

public class NotificationFragment extends Fragment {
    public NotificationFragment() { }
    private Context context;
    private View view;
    private FragmentNotificationBinding binding;
    private List<NotificationModel> notificationModelList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private static final String TAG = "NotificationFragment";
    private Gson gson;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_notification, container, false);
        gson = new Gson();
        context = getActivity();
        view = binding.getRoot();

        inIt();
        return view;
    }

    private void inIt(){

        notification_list();
        setUpRv();



    }

    private void setUpRv(){

        notificationAdapter = new NotificationAdapter(context, notificationModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.notificationRecycler.setLayoutManager(linearLayoutManager);
        binding.notificationRecycler.setHasFixedSize(true);
        binding.notificationRecycler.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();


    }

    private void notification_list(){

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID, Session.getId(context));

        Log.e(TAG, "notification_list: "+hashMap);

        Retro.service(context).notification_list(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                NotificationsModel notificationsModel = gson.fromJson(JsonUtil.resp(response, TAG), NotificationsModel.class);
                if (notificationsModel.getStatus() == 1) {

                    Toast.makeText(context, notificationsModel.getMessage(), Toast.LENGTH_SHORT).show();
                    notificationAdapter.setNotificationModelList(notificationsModel.getData());

                } else {
                    Toast.makeText(context, notificationsModel.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}
