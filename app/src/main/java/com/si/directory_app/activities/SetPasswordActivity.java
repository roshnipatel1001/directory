package com.si.directory_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.si.directory_app.R;
import com.si.directory_app.databinding.ActivitySetPasswordBinding;
import com.si.directory_app.utills.Utility;

public class SetPasswordActivity extends AppCompatActivity {
    private ActivitySetPasswordBinding binding;
    private Context context;
    private static final String TAG = "SetPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_set_password);
       context = this;
       inIt();
    }

    private void inIt(){
        onClick();

    }
    private void onClick(){

        setupwebView("http://34.223.228.22/directory/auth/reset_password");
    }


    private void setupwebView(String url) {
        binding.progressbarr.setVisibility(View.VISIBLE);
        binding.progressbarr.setProgress(0);
        binding.webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView1, int newProgress) {
                binding.progressbarr.setProgress(newProgress);
                if (newProgress == 100) {
                    binding.progressbarr.setVisibility(View.GONE);
                }
            }
        });
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        binding.webView.getSettings().setLightTouchEnabled(false);
        binding.webView.loadUrl(url);
        binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "shouldOverrideUrlLoading: " + url);
                return false;
            }
        });
    }





}
