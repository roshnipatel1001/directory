package com.si.directory_app.retro;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UrlServices {


    @FormUrlEncoded
    @POST("generate_access_token")
    Call<ResponseBody> generate_access_token(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("registration")
    Call<ResponseBody>registration(@FieldMap HashMap<String, String> hashMap);


    @FormUrlEncoded
    @POST("forgot_password_request")
    Call<ResponseBody>forgot_password_request(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("logout")
    Call<ResponseBody>logout(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("change_password_user")
    Call<ResponseBody>change_password_user(@FieldMap HashMap<String, String> hashMap);


    @FormUrlEncoded
    @POST("updated_persnal_detail")
    Call<ResponseBody>updated_persnal_detail(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("category_list")
    Call<ResponseBody>category_list(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("subcategory_list")
    Call<ResponseBody>subcategory_list(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("retailer_list")
    Call<ResponseBody>retailer_list(@FieldMap HashMap<String, String> hashMap);


    @FormUrlEncoded
    @POST("retailer_category_list")
    Call<ResponseBody>retailer_category_list(@FieldMap HashMap<String, String> hashMap);


    @FormUrlEncoded
    @POST("subscribe")
    Call<ResponseBody>subscribe(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("subscribe_list")
    Call<ResponseBody>subscribe_list(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("delete_image")
    Call<ResponseBody>delete_image(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("user_rating")
    Call<ResponseBody>user_rating(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("user_retailer_details")
    Call<ResponseBody>user_retailer_details(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("search")
    Call<ResponseBody>search(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("notification_list")
    Call<ResponseBody>notification_list(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("notification_update")
    Call<ResponseBody>notification_update(@FieldMap HashMap<String, String> hashMap);


    @FormUrlEncoded
    @POST("search_filter")
    Call<ResponseBody>search_filter(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("search_filter_list")
    Call<ResponseBody>search_filter_list(@FieldMap HashMap<String, String> hashMap);


    @POST("update_profile_image")
    Call<ResponseBody> update_profile_image (@Body RequestBody files);

}
