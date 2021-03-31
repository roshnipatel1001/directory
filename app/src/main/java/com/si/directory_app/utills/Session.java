package com.si.directory_app.utills;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    public static final String MyPREFERENCES = "sessionHandling";
    private static final String id = "id", accessToken = "accessToken", name = "name", email = "email", profile_pic = "ProfilePic", pass = "pass";
    private static final String time = "time", address = "address", subId = "subId";
    private static SharedPreferences sharedpreferences;
    private static SharedPreferences.Editor editor;


    public static String getAccessToken(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Session.accessToken, "");
    }

    public static void setAccessToken(Context context, String accessToken) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(Session.accessToken, accessToken);
        editor.apply();
    }

    //------------------------

    public static String getId(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Session.id, "");
    }

    public static void setId(Context context, String id) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(Session.id, id);
        editor.apply();
    }


    public static String getProfile_pic(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Session.profile_pic, "");
    }

    public static void setProfile_pic(Context context, String profile_pic) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(Session.profile_pic, profile_pic);
        editor.apply();
    }

    public static String getName(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Session.name, "");
    }

    public static void setName(Context context, String name) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(Session.name, name);
        editor.apply();
    }

    public static String getSubId(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Session.subId, "");
    }

    public static void setSubId(Context context, String subId) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(Session.subId, subId);
        editor.apply();
    }


    public static String getEmail(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Session.email, "");
    }

    public static void setEmail(Context context, String email) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(Session.email, email);
        editor.apply();
    }


    public static String getTime(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Session.time, "");
    }

    public static void setTime(Context context, String time) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(Session.time, time);
        editor.apply();
    }


    public static String getAddress(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Session.address, "");
    }

    public static void setAddress(Context context, String address) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(Session.address, address);
        editor.apply();
    }

    public static String getPass(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Session.pass, "");
    }

    public static void setPass(Context context, String pass) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(Session.pass, pass);
        editor.apply();
    }


    public static void clearSession(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();

    }
}
