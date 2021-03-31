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
import com.si.directory_app.adapters.SubCategoryAdapter;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.ActivitySubCatBinding;
import com.si.directory_app.fragments.SearchFragment;
import com.si.directory_app.models.sub_list.SubCatModel;
import com.si.directory_app.models.sub_list.SubCategoryModel;
import com.si.directory_app.retro.JsonUtil;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.EqualSpacing;
import com.si.directory_app.utills.Session;
import com.si.directory_app.utills.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCatActivity extends AppCompatActivity {
    private ActivitySubCatBinding binding;
    private List<SubCategoryModel> subCategoryModelList = new ArrayList<>();
    private SubCategoryAdapter subCategoryAdapter;
    private Gson gson;
    private String id , category,subId,subCat;
    private Context context;
    private static final String TAG = "SubCatActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_sub_cat);
       context = this;
        gson = new Gson();
        inIt();
    }


    private void inIt() {

        Intent intent = getIntent();

        if (intent.getStringExtra("id")!=null){

            id = getIntent().getStringExtra("id");


        }else if (intent.getStringExtra("typeId")!=null){

            id = getIntent().getStringExtra("typeId");
        }

         if (intent.getStringExtra("cat_name")!=null){

            category = getIntent().getStringExtra("cat_name");
        }
        else if (intent.getStringExtra("title")!=null){

            category = getIntent().getStringExtra("title");
        }


        binding.tvTitle.setText(category);



        subcategory_list();
        setupRv();
        onClick();

    }

    private void onClick(){

        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DashBordActivity.class);
                intent.putExtra("search1","1");
                startActivity(intent);
                finish();
            }
        });


    }

    private void setupRv () {

        subCategoryAdapter = new SubCategoryAdapter(context, subCategoryModelList);
        GridLayoutManager GridLayoutManager = new GridLayoutManager(context, 2);
        binding.subRecycler.setLayoutManager(GridLayoutManager);
        binding.subRecycler.setHasFixedSize(true);
        binding.subRecycler.addItemDecoration(new EqualSpacing(16, context));
        binding.subRecycler.setAdapter(subCategoryAdapter);
        subCategoryAdapter.notifyDataSetChanged();

    }



    private void subcategory_list(){

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID,Session.getId(context));
        hashMap.put(Appconst.CATEGORY_ID,id);

        Log.e(TAG, "subcategory_list: "+hashMap);

        Retro.service(context).subcategory_list(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                SubCatModel subCatModel = gson.fromJson(JsonUtil.resp(response, TAG), SubCatModel.class);
                if (subCatModel.getStatus() == 1) {

                    Toast.makeText(context, subCatModel.getMessage(), Toast.LENGTH_SHORT).show();
                    subCategoryAdapter.setSubCategoryModelList(subCatModel.getData());

                    subCategoryAdapter.setOnItemClickListener(new SubCategoryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            Log.e(TAG, "onItemClick:CheckClick ");

                            subId= subCatModel.getData().get(position).getId();
                            Session.setSubId(context,subId);
                            subCat = subCatModel.getData().get(position).getTitle();


                            Intent intent = new Intent(context,RetailersListActivity.class);
                            intent.putExtra("idSub",subId);
                            intent.putExtra("subName",subCat);
                            startActivity(intent);

                        }
                    });


                }

                else {
                    Toast.makeText(context, subCatModel.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
