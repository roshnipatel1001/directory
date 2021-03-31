package com.si.directory_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.si.directory_app.R;
import com.si.directory_app.databinding.ActivityShortlistedBinding;
import com.si.directory_app.fragments.LikedRetailerFragment;
import com.si.directory_app.fragments.LikedSubFragment;

import java.util.ArrayList;
import java.util.List;

public class ShortlistedActivity extends AppCompatActivity {
    private ActivityShortlistedBinding binding;
    private static final String TAG = "ShortlistedActivity";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding= DataBindingUtil.setContentView(this,R.layout.activity_shortlisted);
      context = this;
      inIt();
    }

    private  void inIt(){

     setupViewPager(binding.ViewPager);
        binding.tabs.setupWithViewPager(binding.ViewPager);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LikedSubFragment(), "sub Category");
        adapter.addFragment(new LikedRetailerFragment(), "Retailer");
        viewPager.setAdapter(adapter);
    }




}
