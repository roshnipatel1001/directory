package com.si.directory_app.retro;

import android.util.Log;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Tag;

public class JsonUtil {
    private static final String TAG = "JsonUtil";
    public static String STATUS = "status";
    public static String MESSAGE = "message";

    public static JSONObject mainjson(Response<ResponseBody> response,String tag){
        JSONObject jsonObject = null;
        try {
            String resp =  response.body().string().trim();
            Log.e(tag, ": ->> "+resp);
            jsonObject = new JSONObject(resp);
            return  jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

    public static String resp (Response<ResponseBody> response,String tag){
        try {
            String resp =  response.body().string().trim();
            Log.e(tag, "resp: "+resp );
            return  resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "";
    }

/*    public static List<Class<?>> listfromjson(Class<?> cls,String data) {
        Gson gson = new Gson();
        List<cls> clsArrayList = new ArrayList<>();
        gson.fromJson(data, new TypeToken<ArrayList<cls>>() {}.getType());
        return  clsArrayList;*/

}
