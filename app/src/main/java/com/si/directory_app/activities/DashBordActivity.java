package com.si.directory_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.si.directory_app.R;
import com.si.directory_app.databinding.ActivityDashBordBinding;
import com.si.directory_app.fragments.CategoryListFragment;
import com.si.directory_app.fragments.NotificationFragment;
import com.si.directory_app.fragments.ProfileFragment;
import com.si.directory_app.fragments.SearchFragment;

public class DashBordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDashBordBinding binding;
    private Context context;
    private static final String TAG = "DashBordActivity";
    private String value,flag="0",popup ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dash_bord);
        context = this;
        inIt();

    }

    private void inIt() {

        pushFragment(new CategoryListFragment());
        check();
        binding.bottomBar.ivHome.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.bottomBar.tvHome.setTextColor(getResources().getColor(R.color.colorPrimary));


    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.llHome){

            pushFragment(new CategoryListFragment());
            setDefaultColor();
            binding.bottomBar.ivHome.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            binding.bottomBar.tvHome.setTextColor(getResources().getColor(R.color.colorPrimary));


        }else if(v.getId()==R.id.llSearch){

            pushFragment(new SearchFragment());
            setDefaultColor();
            binding.bottomBar.ivSearch.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            binding.bottomBar.tvSearch.setTextColor(getResources().getColor(R.color.colorPrimary));

        }else if(v.getId()==R.id.llNotification){

            pushFragment(new NotificationFragment());
            setDefaultColor();
            binding.bottomBar.ivNotif.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            binding.bottomBar.tvNotif.setTextColor(getResources().getColor(R.color.colorPrimary));

        }else if(v.getId()==R.id.llProfile){

            pushFragment(new ProfileFragment());
            setDefaultColor();
            binding.bottomBar.ivProfile.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            binding.bottomBar.tvProfile.setTextColor(getResources().getColor(R.color.colorPrimary));

        }

    }

    private void setDefaultColor() {

        binding.bottomBar.ivHome.setColorFilter(ContextCompat.getColor(context, R.color.textGreyBE), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.bottomBar.tvHome.setTextColor(getResources().getColor(R.color.textGreyBE));

        binding.bottomBar.ivProfile.setColorFilter(ContextCompat.getColor(context, R.color.textGreyBE), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.bottomBar.tvProfile.setTextColor(getResources().getColor(R.color.textGreyBE));

        binding.bottomBar.ivNotif.setColorFilter(ContextCompat.getColor(context, R.color.textGreyBE), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.bottomBar.tvNotif.setTextColor(getResources().getColor(R.color.textGreyBE));

        binding.bottomBar.ivSearch.setColorFilter(ContextCompat.getColor(context, R.color.textGreyBE), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.bottomBar.tvSearch.setTextColor(getResources().getColor(R.color.textGreyBE));


    }

    private  void  check() {

       Intent intent = getIntent();
       String detail = intent.getStringExtra("detail");
        Log.e(TAG, "check:=== "+detail);
        String signup = intent.getStringExtra("a");
        String change = intent.getStringExtra("change");
        String search1 = intent.getStringExtra("search1");
        String search2 = intent.getStringExtra("search2");
        if (detail!= null){

            pushFragment(new ProfileFragment());
        }else if (signup!= null){

            success_popup();
        }else if (change!=null){

            pushFragment(new ProfileFragment());
        }else if (search1!=null){

            pushFragment(new SearchFragment());
        }
        else if (search2!=null){

            pushFragment(new SearchFragment());
        }

    }

    private void success_popup() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.signup_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.findViewById(R.id.btOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    protected void pushFragment(Fragment fragment) {
        Log.e(TAG, "pushFragment: >>>>>>>>>>>>>>>>>>>>>>>" );
        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
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
