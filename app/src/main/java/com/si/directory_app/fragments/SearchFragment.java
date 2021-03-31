package com.si.directory_app.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
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
import com.si.directory_app.activities.RetailerProfileActivity;
import com.si.directory_app.activities.RetailersListActivity;
import com.si.directory_app.activities.SubCatActivity;
import com.si.directory_app.adapters.SearchAdapter;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.FragmentSearchBinding;
import com.si.directory_app.models.search.SearchModel;
import com.si.directory_app.models.search.SearchingModel;
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


public class SearchFragment extends Fragment {
    public SearchFragment() {
    }

    private Context context;
    private static final String TAG = "SearchFragment";
    private FragmentSearchBinding binding;
    private View view;
    private List<SearchModel> searchModelList = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private Gson gson;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        gson = new Gson();
        context = getActivity();
        view = binding.getRoot();

        inIt();
        return view;
    }

    private void inIt() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                search(text);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });

        setupRv();
    }


    private void setupRv() {

        searchAdapter = new SearchAdapter(context, searchModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.searchRecycler.setLayoutManager(linearLayoutManager);
        binding.searchRecycler.addItemDecoration(new EqualSpacing(16,context));
        binding.searchRecycler.setHasFixedSize(true);
        binding.searchRecycler.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String catType = searchModelList.get(position).getType();
                if (catType.equals("category")) {

                    Intent intent = new Intent(context, SubCatActivity.class);
                    intent.putExtra("typeId",searchModelList.get(position).getId()+"");
                    intent.putExtra("title",searchModelList.get(position).getTitle());
                    startActivity(intent);

                } else if (catType.equals("retailer")){

                    Intent intent = new Intent(context, RetailerProfileActivity.class);
                    intent.putExtra("typeId",searchModelList.get(position).getId()+"");
                    startActivity(intent);
                }else {

                    Intent intent = new Intent(context, RetailersListActivity.class);
                    intent.putExtra("typeId",searchModelList.get(position).getId()+"");
                    intent.putExtra("title",searchModelList.get(position).getTitle());
                    startActivity(intent);
                }
            }
        });


    }

    private void search(String query) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID, Session.getId(context));
        hashMap.put(Appconst.SEARCH, query);

        Log.e(TAG, "search: " + hashMap);

        Retro.service(context).search(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                searchModelList.clear();
                SearchingModel searchingModel = gson.fromJson(JsonUtil.resp(response, TAG), SearchingModel.class);
                if (searchingModel.getStatus() == 1) {
                    searchModelList.addAll(searchingModel.getData());
                    Toast.makeText(context, searchingModel.getMessage(), Toast.LENGTH_SHORT).show();
                    //searchAdapter.setSearchModelList(searchModelList);

                } else {
                    Toast.makeText(context, searchingModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

}
