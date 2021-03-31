package com.si.directory_app.retro;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class Retro {
    public static final String BASEURL_API = Environment.getBaseUrl() + "api/";
    public static final String IMAGE_BASEURL = Environment.getBaseUrl() + "assets/uploads/";
    public static final String PROFILE_IMAGE = IMAGE_BASEURL + "users/";

    public static UrlServices service(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .addInterceptor(logging)
                .addInterceptor(new NetworkConnection(context))
                .addInterceptor(new ResponseStatus(context))
                .build();

        UrlServices service = new Retrofit.Builder()
                .baseUrl(BASEURL_API).client(okHttpClient)
                .build().create(UrlServices.class);
        return service;
    }
}
