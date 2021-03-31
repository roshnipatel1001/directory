package com.si.directory_app.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.si.directory_app.R;
import com.si.directory_app.adapters.LikedRetailerAdapter;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.FragmentLikedRetailerBinding;
import com.si.directory_app.models.liked_cat.LikedCatModel;
import com.si.directory_app.models.liked_retailer.LikeRetailModel;
import com.si.directory_app.models.liked_retailer.LikedRetailerModel;
import com.si.directory_app.retro.JsonUtil;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.EqualSpacing;
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


public class LikedRetailerFragment extends Fragment {
    public LikedRetailerFragment() {}
    private Context context;
    private View view;
    private static final String TAG = "LikedRetailerFragment";
    private List<LikedRetailerModel> likedRetailerModelList = new ArrayList<>();
    private LikedRetailerAdapter likedRetailerAdapter;
    private FragmentLikedRetailerBinding binding;
    private Gson gson;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_liked_retailer, container, false);

        context = getActivity();
        view = binding.getRoot();
        gson = new Gson();

        inIt();
        return view;
    }


    private void inIt() {

        subscribe_list();

       setupRv();

        Log.e(TAG, "inIt: ");

    }


    private void setupRv () {

        likedRetailerAdapter = new LikedRetailerAdapter(context, likedRetailerModelList);
        GridLayoutManager GridLayoutManager = new GridLayoutManager(context, 2);
        binding.likeRecycler.setLayoutManager(GridLayoutManager);
        binding.likeRecycler.setHasFixedSize(true);
        binding.likeRecycler.addItemDecoration(new EqualSpacing(16, context));
        binding.likeRecycler.setAdapter(likedRetailerAdapter);
        likedRetailerAdapter.notifyDataSetChanged();

        likedRetailerAdapter.setOnItemClickListener(new LikedRetailerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String id = likedRetailerAdapter.getLikedRetailerModel().get(position).getId();
                if (view.getId() == R.id.btLike){
                    Log.e(TAG, "onItemClick====: ");
                    subscribe(id,position);
                }

            }
        });

    }

    private void subscribe_list(){

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID,Session.getId(context));
        hashMap.put(Appconst.TYPE,"retailer");

        Log.e(TAG, "subscribeRetailer: "+hashMap);

        Retro.service(context).subscribe_list(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                LikeRetailModel likeRetailModel = gson.fromJson(JsonUtil.resp(response, TAG), LikeRetailModel.class);
                        if (likeRetailModel.getStatus() == 1) {

                            Toast.makeText(context, likeRetailModel.getMessage(), Toast.LENGTH_SHORT).show();
                            likedRetailerAdapter.setLikedRetailerModelList(likeRetailModel.getData());

                        }

                        else {

                            Toast.makeText(context, likeRetailModel.getMessage(), Toast.LENGTH_SHORT).show();

                        }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void subscribe(String id,int position) {

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

                    if (stat == 1 || stat == 0) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                        likedRetailerAdapter.clearAdapter();
                        subscribe_list();
                    }
                    else {
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
