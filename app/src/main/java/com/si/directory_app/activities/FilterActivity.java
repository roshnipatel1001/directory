package com.si.directory_app.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.si.directory_app.R;
import com.si.directory_app.adapters.expand_list.ParentExpandableAdapter;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.ActivityFilterBinding;
import com.si.directory_app.models.product_list.ProductModel;
import com.si.directory_app.models.product_list.ProductsModel;
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

public class FilterActivity extends AppCompatActivity {

    private Context context;
    private static final String TAG = "FilterActivity";
    private ActivityFilterBinding binding;
    private Gson gson;
    private ParentExpandableAdapter listAdapter;
    private String near;
    private List<ProductsModel> productsModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        context = this;
        gson = new Gson();
        inIt();
    }

    private void inIt() {

        search_filter_list();
        ExpandList();
        onClick();
    }

    private void onClick() {

        binding.btNear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    near = "1";
                    Log.e(TAG, "onCheckedChanged: " + near);

                } else {

                    near = "0";
                    Log.e(TAG, "onCheckedChanged: " + near);
                }

            }

        });


        binding.btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_filter();
            }
        });
    }

    private void ExpandList() {
        listAdapter = new ParentExpandableAdapter(context, productsModelList);
        binding.Expandable.setLayoutManager(new LinearLayoutManager(context));
        binding.Expandable.setHasFixedSize(false);
        binding.Expandable.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    private void search_filter_list() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID, Session.getId(context));

        Retro.service(context).search_filter_list(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG, "onResponse: " + response);
                productsModelList.clear();
                ProductModel productModel = gson.fromJson(JsonUtil.resp(response, TAG), ProductModel.class);
                if (productModel.getStatus() == 1) {

                    Toast.makeText(context, productModel.getMessage(), Toast.LENGTH_SHORT).show();
                    productsModelList.addAll(productModel.getData());


                } else {
                    Toast.makeText(context, productModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void search_filter() {

        StringBuilder sb = new StringBuilder();

        String prefix = "";
        for (int i = 0; i < listAdapter.getProductsList().size(); i++) {
            for (int j = 0; j < listAdapter.getProductsList().get(i).getSubcategory().size(); j++) {
                boolean isCheck = listAdapter.getProductsList().get(i).getSubcategory().get(j).isChecked();
                String strId = listAdapter.getProductsList().get(i).getSubcategory().get(j).getId();
                if (isCheck) {
                    sb.append(prefix);
                    prefix = ",";
                    sb.append(strId);
                }
            }
        }

        String categoryIds = sb.toString();
        if (categoryIds.endsWith(",")) {
            categoryIds = categoryIds.substring(0, categoryIds.length() - 1);
        }


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID, Session.getId(context));
        hashMap.put(Appconst.CATEGORY_ID, categoryIds);
        hashMap.put("nearby", near);

        Log.e(TAG, "search_filter: " + hashMap);

        Retro.service(context).search_filter(hashMap).enqueue(new Callback<ResponseBody>() {
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
