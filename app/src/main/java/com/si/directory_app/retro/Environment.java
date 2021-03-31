package com.si.directory_app.retro;


import com.si.directory_app.BuildConfig;

public class Environment {
    /**
     * change the environment of the application
     * for Development it is false
     * for Staging it is true
     * */
    public static boolean environment = false;

    public static String getBaseUrl(){
        String baseurl = "";
        if (environment){
            return  baseurl = "http://34.223.228.22/directory_stag/user/";
        }else {
            return  baseurl = "http://34.223.228.22/directory/";
        }
    }

    public static String getVerisonName(){
        String version = "";
        if (environment){
            return  version = "Version : S :- "+ BuildConfig.VERSION_NAME;
        }else {
            return  version = "Version : D :- "+ BuildConfig.VERSION_NAME;
        }
    }
}
