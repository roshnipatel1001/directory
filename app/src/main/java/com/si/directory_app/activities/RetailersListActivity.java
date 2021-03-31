package com.si.directory_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.si.directory_app.R;
import com.si.directory_app.adapters.RetailerListAdapter;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.ActivityRetailersListBinding;
import com.si.directory_app.models.retailer_list.RetailerListModel;
import com.si.directory_app.models.retailer_list.RetailerModel;
import com.si.directory_app.retro.JsonUtil;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.Session;
import com.si.directory_app.utills.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailersListActivity extends AppCompatActivity {
    private ActivityRetailersListBinding binding;
    private static final String TAG = "RetailersListActivity";
    private Context context;
    private List<RetailerListModel> retailerListModelList = new ArrayList<>();
    private RetailerListAdapter listAdapter;
    private Gson gson;
    private String id,subTitle, retailerId,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_retailers_list);
       context = this;
       gson = new Gson();

       inIt();
    }

    private void inIt(){



        Intent intent = getIntent();

        if (intent.getStringExtra("idSub")!=null){

            id = getIntent().getStringExtra("idSub");


        }else if (intent.getStringExtra("typeId")!=null){

            id = getIntent().getStringExtra("typeId");
            binding.tvTitle.setVisibility(View.GONE);
        }


        title = getIntent().getStringExtra("title");
        Log.e(TAG, "inIt: "+title);
        binding.tvSearch.setText(title);


        subTitle = getIntent().getStringExtra("subName");
        binding.tvTitle.setText(subTitle);
        Log.e(TAG, "inIt: ");

        retailer_list();
        setupRv();
        onClick();

    }

    private void setupRv() {

        listAdapter = new RetailerListAdapter(context, retailerListModelList);
        GridLayoutManager GridLayoutManager = new GridLayoutManager(context,2);
        binding.retailerRecycler.setLayoutManager(GridLayoutManager);
        binding.retailerRecycler.setHasFixedSize(true);
        binding.retailerRecycler.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

    }

    private void onClick() {


        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DashBordActivity.class);
                intent.putExtra("search2","1");
                startActivity(intent);
                finish();
            }
        });

        binding.btFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,FilterActivity.class));
            }
        });


    }

    private void retailer_list() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE,Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY,Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID,Session.getId(context));
        hashMap.put(Appconst.CATEGORY_ID,id);

        Log.e(TAG, "retailer_list: "+hashMap);

        Retro.service(context).retailer_list(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                RetailerModel retailerModel = gson.fromJson(JsonUtil.resp(response, TAG), RetailerModel.class);
                if (retailerModel.getStatus() == 1) {

                    Toast.makeText(context, retailerModel.getMessage(), Toast.LENGTH_SHORT).show();
                      listAdapter.setRetailerListModelList(retailerModel.getData());

                      listAdapter.setOnItemClickListener(new RetailerListAdapter.OnItemClickListener() {
                          @Override
                          public void onItemClick(View view, int position) {

                              retailerId = retailerModel.getData().get(position).getId();

                              Intent intent = new Intent(context,RetailerProfileActivity.class);
                              intent.putExtra("retailerId",retailerId);
                              startActivity(intent);
                          }
                      });
                }

                else {
                    Toast.makeText(context, retailerModel.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }



}

