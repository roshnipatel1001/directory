package com.si.directory_app.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.si.directory_app.R;
import com.si.directory_app.activities.SubCatActivity;
import com.si.directory_app.adapters.CategoryListAdapter;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.FragmentCategoryListBinding;
import com.si.directory_app.models.cat_list.CategoryDatum;
import com.si.directory_app.models.cat_list.CategoryModel;
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

public class CategoryListFragment extends Fragment {

    private FragmentCategoryListBinding binding;
    private Context context;
    private View view;
    private CategoryListAdapter adapter;
    private static final String TAG = "CategoryListFragment";
    private Gson gson;
    String id,catName;
    int positon;

    public CategoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_list, container, false);
        context = getActivity();
        view = binding.getRoot();
        gson = new Gson();

        inIt();
        return view;

    }

    private void inIt() {
        category_list();
        setupRv();

    }


    private void setupRv() {

        List<CategoryDatum> categoryDatumList = new ArrayList<>();
        adapter = new CategoryListAdapter(context, categoryDatumList);
        GridLayoutManager GridLayoutManager = new GridLayoutManager(context, 3);
        binding.rv.setLayoutManager(GridLayoutManager);
        binding.rv.setHasFixedSize(true);
        binding.rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void category_list() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(context));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(context));
        hashMap.put(Appconst.UID, Session.getId(context));

        Log.e(TAG, "get_programs_details: " + hashMap);

        Retro.service(context).category_list(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                CategoryModel categoryModel = gson.fromJson(JsonUtil.resp(response, TAG), CategoryModel.class);
                if (categoryModel.getStatus() == 1) {
                    adapter.setCategoryListModelList(categoryModel.getData());


                    adapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            Log.e(TAG, "onItemClick:CheckClick ");

                            id= categoryModel.getData().get(position).getId();
                            catName = categoryModel.getData().get(position).getTitle();

                            Log.e(TAG, "onItemClick: "+id);
                            Log.e(TAG, "onItemClick: "+catName);

                            Intent intent = new Intent(context, SubCatActivity.class);
                            intent.putExtra("id",id);
                            intent.putExtra("cat_name",catName);
                            startActivity(intent);

//                            Bundle bundle = new Bundle();
//                            bundle.putString("id",id);
//                            bundle.putString("cat_name",catName);
//                            SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
//
//                            subCategoryFragment.setArguments(bundle);
//
//                            pushFragment(subCategoryFragment);
                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    protected void pushFragment(Fragment fragment) {

        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (fragmentTransaction != null) {
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(fragment.getClass().toString());
                fragmentTransaction.commit();
            }
        }
    }


}







