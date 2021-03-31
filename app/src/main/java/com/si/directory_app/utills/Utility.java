package com.si.directory_app.utills;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.si.directory_app.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utility {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final String TAG = Utility.class.getSimpleName();
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public static final int SPLASH = 3000;
    private static String PREFERENCES = "user";
    private static boolean hasImmersive;
    private static boolean cached = false;
    private static Dialog dialog;
    private static int position=-1;
    //for date comparsion
    public static String DATEAFTER = "after";
    public static String DATEBEFORE = "before";
    public static String DATEEQUAL = "equal";

   // String givenDateString = "Tue Apr 23 16:08:28 GMT+05:30 2013";
   // SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")

    public static String FORMAT_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static String FORMAT_YYYYMMdd = "yyyy-MM-dd";
    public static String FORMAT_MMMddYYYY = "MMM dd, yyyy";
    public static String FORMAT_MMddYY = "MM-dd-yy";
    public static String FORMAT_YYYYMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_HHmmss = "HH:mm:ss";
    public static String FORMAT_HHmm = "HH:mm";
    public static String FORMAT_YYYYMMMMMdd = "yyyyy-MMMMM-dd"; //  2001.July.04

    private static final String[] m_Countries = {
            "AD",
            "AE",
            "AF",
            "AL",
            "AM",
            "AN",
            "AO",
            "AQ",
            "AR",
            "AT",
            "AU",
            "AW",
            "AZ",
            "BA",
            "BD",
            "BE",
            "BF",
            "BG",
            "BH",
            "BI",
            "BJ",
            "BL",
            "BN",
            "BO",
            "BR",
            "BT",
            "BW",
            "BY",
            "BZ",
            "CA",
            "CC",
            "CD",
            "CF",
            "CG",
            "CH",
            "CI",
            "CK",
            "CL",
            "CM",
            "CN",
            "CO",
            "CR",
            "CU",
            "CV",
            "CX",
            "CY",
            "CZ",
            "DE",
            "DJ",
            "DK",
            "DZ",
            "EC",
            "EE",
            "EG",
            "ER",
            "ES",
            "ET",
            "FI",
            "FJ",
            "FK",
            "FM",
            "FO",
            "FR",
            "GA",
            "GB",
            "GE",
            "GH",
            "GI",
            "GL",
            "GM",
            "GN",
            "GQ",
            "GR",
            "GT",
            "GW",
            "GY",
            "HK",
            "HN",
            "HR",
            "HT",
            "HU",
            "ID",
            "IE",
            "IL",
            "IM",
            "IN",
            "IQ",
            "IR",
            "IT",
            "JO",
            "JP",
            "KE",
            "KG",
            "KH",
            "KI",
            "KM",
            "KP",
            "KR",
            "KW",
            "KZ",
            "LA",
            "LB",
            "LI",
            "LK",
            "LR",
            "LS",
            "LT",
            "LU",
            "LV",
            "LY",
            "MA",
            "MC",
            "MD",
            "ME",
            "MG",
            "MH",
            "MK",
            "ML",
            "MM",
            "MN",
            "MO",
            "MR",
            "MT",
            "MU",
            "MV",
            "MW",
            "MX",
            "MY",
            "MZ",
            "NA",
            "NC",
            "NE",
            "NG",
            "NI",
            "NL",
            "NO",
            "NP",
            "NR",
            "NU",
            "NZ",
            "OM",
            "PA",
            "PE",
            "PF",
            "PG",
            "PH",
            "PK",
            "PL",
            "PM",
            "PN",
            "PR",
            "PT",
            "PW",
            "PY",
            "QA",
            "RO",
            "RS",
            "RU",
            "RW",
            "SA",
            "SB",
            "SC",
            "SD",
            "SE",
            "SG",
            "SH",
            "SI",
            "SK",
            "SL",
            "SM",
            "SN",
            "SO",
            "SR",
            "ST",
            "SV",
            "SY",
            "SZ",
            "TD",
            "TG",
            "TH",
            "TJ",
            "TK",
            "TL",
            "TM",
            "TN",
            "TO",
            "TR",
            "TV",
            "TW",
            "TZ",
            "UA",
            "UG",
            "US",
            "UY",
            "UZ",
            "VA",
            "VE",
            "VN",
            "VU",
            "WF",
            "WS",
            "YE",
            "YT",
            "ZA",
            "ZM",
            "ZW"
    };

    private static final String[] m_Codes = {
            "376",
            "971",
            "93",
            "355",
            "374",
            "599",
            "244",
            "672",
            "54",
            "43",
            "61",
            "297",
            "994",
            "387",
            "880",
            "32",
            "226",
            "359",
            "973",
            "257",
            "229",
            "590",
            "673",
            "591",
            "55",
            "975",
            "267",
            "375",
            "501",
            "1",
            "61",
            "243",
            "236",
            "242",
            "41",
            "225",
            "682",
            "56",
            "237",
            "86",
            "57",
            "506",
            "53",
            "238",
            "61",
            "357",
            "420",
            "49",
            "253",
            "45",
            "213",
            "593",
            "372",
            "20",
            "291",
            "34",
            "251",
            "358",
            "679",
            "500",
            "691",
            "298",
            "33",
            "241",
            "44",
            "995",
            "233",
            "350",
            "299",
            "220",
            "224",
            "240",
            "30",
            "502",
            "245",
            "592",
            "852",
            "504",
            "385",
            "509",
            "36",
            "62",
            "353",
            "972",
            "44",
            "91",
            "964",
            "98",
            "39",
            "962",
            "81",
            "254",
            "996",
            "855",
            "686",
            "269",
            "850",
            "82",
            "965",
            "7",
            "856",
            "961",
            "423",
            "94",
            "231",
            "266",
            "370",
            "352",
            "371",
            "218",
            "212",
            "377",
            "373",
            "382",
            "261",
            "692",
            "389",
            "223",
            "95",
            "976",
            "853",
            "222",
            "356",
            "230",
            "960",
            "265",
            "52",
            "60",
            "258",
            "264",
            "687",
            "227",
            "234",
            "505",
            "31",
            "47",
            "977",
            "674",
            "683",
            "64",
            "968",
            "507",
            "51",
            "689",
            "675",
            "63",
            "92",
            "48",
            "508",
            "870",
            "1",
            "351",
            "680",
            "595",
            "974",
            "40",
            "381",
            "7",
            "250",
            "966",
            "677",
            "248",
            "249",
            "46",
            "65",
            "290",
            "386",
            "421",
            "232",
            "378",
            "221",
            "252",
            "597",
            "239",
            "503",
            "963",
            "268",
            "235",
            "228",
            "66",
            "992",
            "690",
            "670",
            "993",
            "216",
            "676",
            "90",
            "688",
            "886",
            "255",
            "380",
            "256",
            "1",
            "598",
            "998",
            "39",
            "58",
            "84",
            "678",
            "681",
            "685",
            "967",
            "262",
            "27",
            "260",
            "263"
    };

    public static void hideSoftKeyboardActivity(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideKeyboard(Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = ((Activity) mContext).getCurrentFocus();
        if (focusedView != null) {
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static Integer generateRandomId(){
        int random = (int)(Math.random() * 50 + 1);
        return random;
    }

    public static void show_loader(ConstraintLayout layout) {
        layout.setVisibility(View.VISIBLE);
    }

    public static void hide_loader(ConstraintLayout layout) {
        layout.setVisibility(View.GONE);
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);

            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public static void showTransparentStatusBar(AppCompatActivity activity) {
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
                setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
            }
            if (Build.VERSION.SDK_INT >= 19) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            //make fully Android Transparent Status bar
            if (Build.VERSION.SDK_INT >= 21) {
                setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

    }

    public static Integer rendomGenrate(){
        int random = (int)(Math.random() * 50 + 1);
        return random;
    }

    public static String boroBearDateFormat(String dddd) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dddd);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("d MMM yy"); //todo---> ("EEEE d MMM") for(MONDAY 28 MAY)
        String date = df.format(date1);
        return date;
    }
/*
    public static void hideKeyboardtwo(View view ,final Context context) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    View focusedView = ((Activity) context).getCurrentFocus();
                    if (focusedView != null) {
                        imm.hideSoftInputFromWindow(focusedView.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboardtwo(innerView,context);
            }
        }
    }
*/



    /**
     * Determines whether one Location reading is better than the current Location fix
     *
     * @param location            The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    public static boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 6;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return false;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return false;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private static boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    public static String getUUID() {
        return java.util.UUID.randomUUID().toString();
    }


    public static String timeLeft(int delivery_time, String updatedDate) {

        try {

            int minutes = delivery_time;
            long millis_minutes = minutes * 60 * 1000;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = sdf.parse(updatedDate);
            long totalMinutes = millis_minutes + date.getTime();
            SimpleDateFormat sdf1 = new SimpleDateFormat();
            sdf1.setTimeZone(TimeZone.getTimeZone("UTC"));
            long leftMinutes = totalMinutes - getUTCDateTimeAslong();
            return leftMinutes + "";
        } catch (Exception e) {
            Log.e("Error in Utility 63", "" + e);
            e.printStackTrace();
        }
        return "";
    }


    public static long getUTCDateTimeAslong() {

        Date dateTime1 = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("IST"));
            Date date = new Date();
            SimpleDateFormat dateParser = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            dateTime1 = null;
            try {
                dateTime1 = dateParser.parse(format.format(date));
            } catch (ParseException e) {
                Log.e("Error in Utility 84", "" + e);
                e.printStackTrace();
            }
        } catch (java.text.ParseException e) {
            Log.e("Error in Utility 88", "" + e);
            e.printStackTrace();
        }

        return dateTime1.getTime();
    }


    public static void logLargeString(String str) {
        if (str.length() > 3000) {
            System.out.print(str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            System.out.print(str);
        }
    }

    public static int getNavBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @SuppressLint("NewApi")
    public static boolean hasImmersive(Context ctx) {

        if (!cached) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                hasImmersive = false;
                cached = true;
                return false;
            }
            Display d = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            hasImmersive = (realWidth > displayWidth) || (realHeight > displayHeight);
            cached = true;
        }

        return hasImmersive;
    }

    public static String getDeviceType(Context mContext) {
        String ua = new WebView(mContext).getSettings().getUserAgentString();
        if (ua.contains("Mobile")) {
            System.out.println("Type:Mobile");
            return "ANDROID MOBILE";
            // Your code for Mobile
        } else {
            // Your code for TAB
            System.out.println("Type:TAB");
            return "ANDROID TAB";
        }
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static Boolean write(String fname, String fcontent) {
        try {
            String fpath = "/sdcard/" + fname + ".txt";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.close();
            Log.d("Suceess", "Sucess");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static Boolean writehtml(String fname, String fcontent) {
        try {

            String fpath = "/sdcard/" + fname + ".html";

            File file = new File(fpath);

            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.close();

            Log.d("Suceess", "Sucess");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    public static boolean isValidPhone(String phone)
    {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            if(phone.length() < 8 || phone.length() > 13)
            {
                check = false;

            }
            else
            {
                check = true;

            }
        }
        else
        {
            check=false;
        }
        return check;
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = "1755018674765832";
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public static void setBooleanPreferences(Context context, String key,
                                             boolean isCheck) {
        SharedPreferences setting = (SharedPreferences) context
                .getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean(key, isCheck);
        editor.commit();
    }

    public static boolean getBooleanPreferences(Context context, String key) {
        SharedPreferences setting = (SharedPreferences) context
                .getSharedPreferences(PREFERENCES, 0);
        return setting.getBoolean(key, false);
    }

    public static void setStringPreferences(Context context, String key,
                                            String value) {
        SharedPreferences setting = (SharedPreferences) context
                .getSharedPreferences(PREFERENCES, 0);

        SharedPreferences.Editor editor = setting.edit();

        editor.putString(key, value);
        editor.commit();

    }

    public static String getStringPreferences(Context context, String key) {

        SharedPreferences setting = (SharedPreferences) context
                .getSharedPreferences(PREFERENCES, 0);
        return setting.getString(key, "");

    }

    public static void setIntegerPreferences(Context context, String key,
                                             int value) {
        SharedPreferences setting = (SharedPreferences) context
                .getSharedPreferences(PREFERENCES, 0);

        SharedPreferences.Editor editor = setting.edit();

        editor.putInt(key, value);
        editor.commit();

    }

    public static void clearAllSharedPreferences(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }


    public static void clearStringFromclearAllSharedPreferences(Context context, String string) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                PREFERENCES, Context.MODE_PRIVATE).edit();
        if (string != null) {
            editor.putString(string, null);
        } else {
            editor.remove(string);
        }
        editor.commit();

    }


    public static int getIntegerPreferences(Context context, String key) {

        SharedPreferences setting = (SharedPreferences) context
                .getSharedPreferences(PREFERENCES, 0);
        return setting.getInt(key, 0);

    }

    public static void showAlert(Context mContext, String title, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(msg);
        // Set behavior of negative button

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });

        AlertDialog alert = builder.create();
        try {
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(Context mContext, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle(mContext.getString(R.string.app_name));
        builder.setMessage(msg);
        // Set behavior of negative button

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });

        AlertDialog alert = builder.create();
        try {
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showProgressHUD(Context context, String title,
                                       String message) {
        try {

            if (title == null)
                title = "";
            if (message == null)
                message = "";
            dialog = ProgressDialog.show(context, title, message);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   /* public static void startAnimLoader(ConstraintLayout layout){
        AVLoadingIndicatorView avLoadingIndicatorView = layout.findViewById(R.id.avloader);
        avLoadingIndicatorView.show();
        // or avi.smoothToShow();
    }

    public static void stopAnimLoader(ConstraintLayout layout){
        AVLoadingIndicatorView avLoadingIndicatorView = layout.findViewById(R.id.avloader);
        avLoadingIndicatorView.hide();
        // or avi.smoothToHide();
    }
*/

    /*public static void showProgressHUD(Context context) {
        try {
            dialog = new ProgressDialog(context , R.style.MyAlertDialogStyle);
            dialog = ProgressDialog.show(context, "", "Please Wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void showLoader(View layout){
        layout.setVisibility(View.VISIBLE);
    }
    public static void hideLoader(View layout){
        layout.setVisibility(View.GONE);
    }

    public static void showProgressHUD(Context context, String message) {
        try {
            if (message == null)
                message = "";
            dialog = ProgressDialog.show(context, context.getString(R.string.app_name), message);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressHud() {
        try {
            if (dialog != null) {
                dialog.dismiss();
                dialog.cancel();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*public static boolean connectionStatus(Context mContext) {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
            if (activeNetwork != null) {
                Log.e(TAG, "activeNetwork.getTypeName(); " + activeNetwork.getTypeName());
                if (activeNetwork.getTypeName().equalsIgnoreCase("WIFI")) {


                    if (activeNetwork.isConnectedOrConnecting()) {
                        return true;
                    } else {
                        return false;
                    }

                } else if (activeNetwork.getTypeName().equalsIgnoreCase("MOBILE")) {

                    if (activeNetwork.isConnectedOrConnecting()) {
                        return true;
                    } else {
                        return false;
                    }

                } else {

                    return false;

                }
            }
        }
        return false;
    }*/

    /**
     * String myBase64Image = encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 100);
     * Bitmap myBitmapAgain = decodeBase64(myBase64Image);
     *
     * @param compressFormat
     * @param quality
     * @return
     */
    public static String encodeToBase64(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
    }

    /**
     * String myBase64Image = encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 100);
     * Bitmap myBitmapAgain = decodeBase64(myBase64Image);
     *
     * @param input
     * @return
     */
    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String getCompleteAddressthroughLatLngString(Context mContext, double LATITUDE, double LONGITUDE) {

        String strAdd = "";
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(" ");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

   /* public static HashMap<String, Object> getModeltoMap(Object object) {
        Gson gson = new Gson();
        String temp = gson.toJson(object);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map = (HashMap<String, Object>) gson.fromJson(temp, map.getClass());
        return map;
    }*/

    public static boolean isValidEmailAddress(String emailAddress) {
        String expression = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();


    }
    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public static boolean isValidPassword(String Password) {
        String expression = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";  //--TOdo ws like --> "(?=.*[0-9@#$%^&+=])"  "^(?=.*[a-zA-Z])(?=.*[0-9@#$%^&+=]).{8,}$"
        CharSequence inputStr = Password;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
/*
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
*/
    public static boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        if(m.matches())
            return true;
        else
            return false;
    }


    public static String roundoff(Double amount) {
        return String.format("%.2f", amount);
    }



    public static String makeSHA1Hash(String input)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.reset();
        byte[] buffer = input.getBytes("UTF-8");
        md.update(buffer);
        byte[] digest = md.digest();

        String hexStr = "";
        for (int i = 0; i < digest.length; i++) {
            hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
        }
        return hexStr;
    }

    public static void fullscreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void nointernetmsg(final View v, final Activity activity) {
        Snackbar snackbar = Snackbar
                .make(v, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
                        activity.startActivity(intent);
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        //TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        //textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public static String isValid(String passwordhere) {

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        String error = "";

        if (passwordhere.length() < 8) {
            error = "Password lenght must have alleast 8 character !!";
        }
        if (!specailCharPatten.matcher(passwordhere).find()) {
            error = "Password must have atleast one specail character !!";
        }
        if (!UpperCasePatten.matcher(passwordhere).find()) {
            error = "Password must have atleast one uppercase character !!";
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            error = "Password must have atleast one lowercase character !!";
        }
        if (!digitCasePatten.matcher(passwordhere).find()) {
            error = "Password must have atleast one digit character !!";
        }
        return error;
    }

    public static boolean isSpecailCharPatten(String paswword) {
        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        return !specailCharPatten.matcher(paswword).find();
    }

    public static boolean isUpperCasePatten(String paswword) {
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        return !UpperCasePatten.matcher(paswword).find();
    }

    public static boolean isLowerCasePatten(String password) {
        Pattern lowerCasePatten = Pattern.compile("[a-z]");
        return !lowerCasePatten.matcher(password).find();
    }

    public static boolean isDigitCasePatten(String password) {
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        return !digitCasePatten.matcher(password).find();
    }

    public static boolean isContainspain(String password) {
        Pattern digitCasePatten = Pattern.compile("[ ^([6-9]{1})([0-9]{9})]");
        return !digitCasePatten.matcher(password).find();
    }

    public static void nointernettoast(Context context) {
        Toast.makeText(context, "No internet!", Toast.LENGTH_SHORT).show();
    }

    public static LinearLayoutManager linearLayoutManager(Context context) {
        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        return linearLayout;
    }

    public static boolean isFirst(Context context) {
        final SharedPreferences reader = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_first", true);
        if (first) {
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_first", false);
            editor.commit();
        }
        return first;
    }

    public static String round_off_double(String value) {
        double datavalue = Double.parseDouble(value);
        double newKB = Math.round(datavalue * 100D) / 100D;
        String correctvalue = String.valueOf(newKB);
        return correctvalue;
    }

    public static String round_off_float(float d, int decimalPlace) {
        String value = "";
        value = String.valueOf(BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue());
        return value;
    }



    public static boolean checkChar(EditText editText) {
        char x = editText.getText().toString().charAt(0);

        if (x == '6' || x == '7' || x == '9')
            return true;

        return false;
    }

    public static String capitalize(String capString) {

        // String chars = capitalize("hello dream world");  Output: Hello Dream World

        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }



    public static String comma(String data) {
        String abc = data;
        abc = abc.replace(".", ",") + " €";
        return abc;
    }

    public static String normal(String data) {
        String abc = data + " €";
        return abc;
    }

    public static String reverse_string(String string) {
        String str[] = string.split("\\s*,\\s*");
        String finalStr = "";
        for (int i = str.length - 1; i >= 0; i--) {
            finalStr += str[i] + ",";
        }
        return finalStr;
    }

    /**
     * This method returns true if the collection is null or is empty.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * This method returns true of the map is null or is empty.
     *
     * @param map
     * @return true | false
     */
    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * This method returns true if the objet is null.
     *
     * @param object
     * @return true | false
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        return false;
    }

    /**
     * This method returns true if the input array is null or its length is zero.
     *
     * @param array
     * @return true | false
     */
    public static boolean isEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * This method returns true if the input string is null or its length is zero.
     *
     * @param string
     * @return true | false
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.trim().length() == 0) {
            return true;
        }
        return false;
    }


    public static boolean isSpecialCharacter(Character c) {
        return c.toString().matches("[^a-z A-Z0-9]");
    }

    public static String extractYoutubeVideoId(String ytUrl) {
        String vId = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(ytUrl);

        if (matcher.find()) {
            vId = matcher.group();
        }
        Log.e(TAG, "extractYoutubeVideoId: " + vId);
        return vId;

    }

    public static void setTimePickerInterval(TimePicker timePicker) {
        final Calendar c = Calendar.getInstance();
        final int minute = c.get(Calendar.MINUTE);
        Log.e(TAG, "setTimePickerInterval: " + minute);

        try {
            int TIME_PICKER_INTERVAL = 5;
            NumberPicker minutePicker = (NumberPicker) timePicker.findViewById(Resources.getSystem().getIdentifier(
                    "minute", "id", "android"));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            minutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            int cbc = setval(minute);
            minutePicker.setValue(cbc);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e);
        }
    }

    public static int setval(int min) {
        int correct_min = 0;

        if (min>0 && min<5){
            correct_min = 0;
        }else if (min>5 && min<10){
            correct_min = 1;
        }else if (min>10 && min<15){
            correct_min = 2;
        }else if (min>15 && min<20){
            correct_min = 3;
        }else if (min>20 && min<25){
            correct_min = 4;
        }else if (min>25 && min<30){
            correct_min = 5;
        }else if (min>30 && min<35){
            correct_min = 6;
        }else if (min>35 && min<40){
            correct_min = 7;
        }else if (min>40 && min<45){
            correct_min = 8;
        }else if (min>45 && min<50){
            correct_min = 9;
        }else if (min>50 && min<55){
            correct_min = 10;
        }else if (min>55 && min<60){
            correct_min = 11;
        }
        Log.e(TAG, "setval: "+correct_min );
        return correct_min;

    }


    public static String getCountryCode( String name){

        if(name!=null && !name.equals("")){

            for(int i =0; i<m_Countries.length; i++){
                if(name.toUpperCase().equals(m_Countries[i])){
                    position = i;
                }
            }

        }
        if(position==-1){
            return "";
        }else {
            return m_Codes[position];
        }

    }


    public static String getGooglePlayStoreUrl(Activity activity) {
        String id = activity.getApplicationInfo().packageName; // current google
        // play is using
        // package name
        // as id
        return "market://details?id=" + id;
    }

    public static String getCurrentVersion(Activity activity) {
        // int currentVersionCode = 0;
        String versionname = "";
        PackageInfo pInfo;
        try {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            // currentVersionCode = pInfo.versionName;
            versionname = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // return 0
        }
        return versionname;
    }


    /**************wdullar timepicker and datepicker dialog *******************************************************/

  /*  public static Timepoint[] generateTimepoints(int minutesInterval) {
        List<Timepoint> timepoints = new ArrayList<>();

        for (int minute = 0; minute <= 1440; minute += minutesInterval) {
            int currentHour = minute / 60;
            int currentMinute = minute - (currentHour > 0 ? (currentHour * 60) : 0);
            if (currentHour == 24)
                continue;
            timepoints.add(new Timepoint(currentHour, currentMinute));
        }
        return timepoints.toArray(new Timepoint[timepoints.size()]);
    }*/



    // todo date and time util -------------------------------------------------------------

    public static String getFormatedDate(String strDate, String sourceFormate, String destinyFormate) {
        SimpleDateFormat df;
        df = new SimpleDateFormat(sourceFormate);
        Date date = null;
        try {
            date = df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        df = new SimpleDateFormat(destinyFormate);
        return df.format(date);
    }

    public static String change_format(String timeeee, String input_frotmat, String output_fromat) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat(input_frotmat).parse(timeeee);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
       // DateFormat df = new SimpleDateFormat(output_fromat);
        SimpleDateFormat df = new SimpleDateFormat(output_fromat, Locale.US);
        TimeZone zone = TimeZone.getTimeZone("GMT+5:30");
        df.setTimeZone(zone);
        return df.format(date1);
    }

    public static String getFirstDayofWeek() {
        // String str=""+Calendar.getInstance().get(field);
        String str = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = new Date();
        try {
            myDate = sdf.parse(str);
        } catch (ParseException pe) {
            // Do Something
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        System.out.println("getFirstDayofWeek:" + sdf.format(cal.getTime()));
        return sdf.format(cal.getTime());
    }

    public static String getDBCurrentDate() {
        return new SimpleDateFormat("HHmmssZ").format(new Date());
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getCurrentDateFormat() {
        return new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").format(new Date());
    }

    public static String getCurrentDateddmmyyyy() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    public static String getCurrentDateMMMddyyyy() {
        return new SimpleDateFormat("MMM dd, yyyy").format(new Date());
    }

    public static String getCurrentMMddyy() {
        return new SimpleDateFormat("MM-dd-yy").format(new Date());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }
    public static String getAfterOneDay() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    }

    public static String getLastDayofWeek() {
        // String str="2015-05-12";
        String str = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = new Date();
        try {
            myDate = sdf.parse(str);
        } catch (ParseException pe) {
            // Do Something
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.set(Calendar.DAY_OF_WEEK, 7);
        return sdf.format(cal.getTime());
    }

    public static String getCurrentMMddyyyy() {
        return new SimpleDateFormat("MM-dd-yyyy").format(new Date());
    }

    public static String timezone() {
        Date date = new Date();
        Log.e(TAG, "timezone:date " + date);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        Log.e(TAG, "Date and time in Madrid: " + df.format(date));
        return df.format(date);
    }

    public static String changetime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ti = "";
        try {
            Date date1 = format.parse(time);
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
            Log.e(TAG, "Date and time in Madrid: " + df.format(date1));
            ti = df.format(time);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ti;

    }

    public static String current_time(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    public static String current_time_informat(String format){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    public static String days() {
        String correct_day = "";
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        int intdays = calendar.get(Calendar.DAY_OF_WEEK);
        switch (intdays) {
            case 1:
                correct_day = "7";
                break;
            case 2:
                correct_day = "1";
                break;
            case 3:
                correct_day = "2";
                break;
            case 4:
                correct_day = "3";
                break;
            case 5:
                correct_day = "4";
                break;
            case 6:
                correct_day = "5";
                break;
            case 7:
                correct_day = "6";
                break;
        }

        return correct_day;

    }

    public static String days_from_date(String datee) {
        String correct_day = "";
        Calendar calendar = Calendar.getInstance();
        String dobSplit[] = datee.split("-");
        calendar.set(Integer.parseInt(dobSplit[0]), Integer.parseInt(dobSplit[1]) - 1, Integer.parseInt(dobSplit[2]));
        Log.e(TAG, "days_from_date: " + Integer.parseInt(dobSplit[0]) + Integer.parseInt(dobSplit[1]) + Integer.parseInt(dobSplit[2]));
        int intdays = calendar.get(Calendar.DAY_OF_WEEK);
        switch (intdays) {
            case 1:
                correct_day = "7";
                break;
            case 2:
                correct_day = "1";
                break;
            case 3:
                correct_day = "2";
                break;
            case 4:
                correct_day = "3";
                break;
            case 5:
                correct_day = "4";
                break;
            case 6:
                correct_day = "5";
                break;
            case 7:
                correct_day = "6";
                break;
        }

        Log.e(TAG, "days_from_date: " + correct_day);
        return correct_day;

    }

    public static String getDay(String day, String month, String year) {
        String[] dates = new String[]{"SUNDAY", "MONDAY", "TUESDAY", //
                "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(year), //
                Integer.parseInt(month) - 1, // <-- add -1
                Integer.parseInt(day));
        int date_of_week = cal.get(Calendar.DAY_OF_WEEK);
        return dates[date_of_week - 1];
    }

    public static String fromDateddmmyyyytoyyyymmdd(String dateeeee) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("dd-MM-yyyy").parse(dateeeee);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date1);

    }



    public static String toDate(String dateeeee) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateeeee);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(date1);

    }

    public static String toTime(String timeeee) {

        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeeee);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(date1);
    }

    public static String toDateTime(String timeeee) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(timeeee);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date1);
    }

    public static String server_format(String dddd) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Date.parse(dddd));
        return date;
    }

    public static String app_format(String dddd) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dddd);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(date1);
        return date;
    }

    public static String app_formatYear(String dddd) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy").parse(dddd);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("yy");
        String date = df.format(date1);
        return date;
    }

    public static String checktime (String startf, String endd){
        Date start = null;
        Date end = null;
        String fact = "";
        try {
            if (startf!=null && !startf.isEmpty()){
                start = new SimpleDateFormat("HH:mm").parse(startf);
            }

            if (endd!=null && !endd.isEmpty()){
                end = new SimpleDateFormat("HH:mm").parse(endd);
            }

            if(start.after(end)){
                return fact = "after";
            }

            if(start.before(end)){
                return fact = "before";
            }

            if(start.equals(end)){
                return fact = "equal";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fact;
    }

    public static Date stringtoTime(String time){
        Date date = null;
        Date end = null;
        String fact = "";
        try {
            date = new SimpleDateFormat("HH:mm").parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringdatetodate(String datef){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(datef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String toDateTimeTformat(String timeeee) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeeee);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return df.format(date1);
    }



    public static String comparedates(String startdate, String enddate){
        String type = "";
        Date start = null;
        Date end = null;
        // start is a present date and end is tomorrow date
        //  0 comes when two date are same,
        //  1 comes when date1 is higher then date2
        // -1 comes when date1 is lower then date2

        try {
            start = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            end = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            Log.e(TAG, "comparedates: >>> "+start.compareTo(end));
            switch (start.compareTo(end)){
                case 0:
                    type = DATEEQUAL;
                    break;
                case 1:
                    type = DATEAFTER;
                    break;
                case -1:
                    type = DATEBEFORE;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  type;
    }

    public static String dateaszone(String pattern, String timezone){
        String datye = "";
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(pattern);
        df.setTimeZone(TimeZone.getTimeZone(timezone));
        Log.e(TAG, "Date and time in Madrid: " + df.format(date));
        return  datye = df.format(date);
    }

    public static void showloader(View layout){
        layout.setVisibility(View.VISIBLE);
    }

    public static void hideloader(View layout){
        layout.setVisibility(View.GONE);
    }


    public static boolean checkGPSStatus(Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    };

    public static void strikethrough(TextView textView){
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static void underline(TextView textView){
        textView.setPaintFlags(textView.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    }

    public static String removelastcomma(String s){
        if (s.endsWith(",")) {
            s = s.substring(0, s.length()-1);
        }
        return s;
    }

    public static String hourminBuilder(int hour, int minute){
        String hourplusmin  = "";
        StringBuilder strhour = new StringBuilder();
        StringBuilder strminutee = new StringBuilder();
        if (hour < 10) {
            strhour.append("0" + hour);
        } else {
            strhour.append(hour);
        }
        if (minute < 10) {
            strminutee.append("0" + minute);
        } else if (minute == 0) {
            strminutee.append("00");
        } else {
            strminutee.append(minute);
        }
        String ttttt = strhour + ":" + strminutee;;
        return  hourplusmin = ttttt;
    }


    public static void getAddressFromLocation(final String locationAddress, final Context context, final Handler handler) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                Double latitude = 0.0, longitude = 0.0;
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = (Address) addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        sb.append(address.getLatitude()).append("\n");
                        sb.append(address.getLongitude()).append("\n");
                        latitude = address.getLatitude();
                        longitude = address.getLongitude();
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n\nLatitude and Longitude :\n" + result;
                        bundle.putString("address", result);

                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n Unable to get Latitude and Longitude for this address location.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

    public static void getAddress(final double latitude, final double longitude, final Handler handler, final Context context) {
        final StringBuilder result = new StringBuilder();
        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0) {
                        Log.e(TAG, "getAddress: addres size  >" + addresses.size());

                        Address address = addresses.get(0);

                        for (int i = 0; i <= addresses.get(0).getMaxAddressLineIndex(); i++) {
                            if (i == addresses.get(0).getMaxAddressLineIndex()) {
                                result.append(addresses.get(0).getAddressLine(i));
                            } else {
                                result.append(addresses.get(0).getAddressLine(i) + ",");


                            }
                        }
                        Log.e(TAG, "getAddress:address " + address);
                        Log.e(TAG, "getAddress:(result-- " + result.toString());

                    }
                } catch (Exception e) {
                }finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("dress", result.toString());
                        message.setData(bundle);
                    } /*else {
                        message.what = 2;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n Unable to get Latitude and Longitude for this address location.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }*/
                    message.sendToTarget();

                }

            }
        };
        thread.start();
    }

    public static String getaddress(final double latitude, final double longitude, Context context) {
        final StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {

                Address address = addresses.get(0);

                for (int i = 0; i <= addresses.get(0).getMaxAddressLineIndex(); i++) {
                    if (i == addresses.get(0).getMaxAddressLineIndex()) {
                        result.append(addresses.get(0).getAddressLine(i));
                    } else {
                        result.append(addresses.get(0).getAddressLine(i) + ",");


                    }
                }


             /*   addresssss = result.toString();
                Log.e(TAG, "getaddress: "+ addresssss);
                // binding.etLocationHome.setText(addresssss);
                sessionFilter.setMplacename(addresssss);*/
            }
        } catch (Exception e) {

        }

        return String.valueOf(result.toString());
    }

    /*******************status bar tranperent color******************/

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static String getLastSeenDatenew(long timeStamp, Context context) {

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeStamp * 1000L);
        String date = android.text.format.DateFormat.format("dd/MM/yyyy", cal).toString();
        String lastSeenTime = new SimpleDateFormat("HH:mm a").format(cal.getTime());

        long currentTimeStamp = System.currentTimeMillis();

        long diffTimeStamp = (currentTimeStamp - timeStamp)/1000;

        if (diffTimeStamp < 24 * 60 * 60) {
            return  "Last seen today at " + lastSeenTime;
        } else if (diffTimeStamp < 48 * 60 * 60) {
            return "Last seen yesterday at " + lastSeenTime;
        } else {
            return "Last seen at " + date + " on " + lastSeenTime;
        }

    }


 /**************************************************************************************************
   ---------------------Broad cast reciever work -------------------------------------------==

 ------------------------------Intialization proccess
  private BroadcastReceiver broadcastReceiver;

    broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, new IntentFilter("checkout_update"));
    }
------------------------  senng process
    Intent itent = new Intent("checkout_update");
  itent.putExtra("abc", "update");
  LocalBroadcastManager.getInstance(mContext.getApplicationContext()).sendBroadcast(itent);
  ---------------------------------------------------------------------------------------------------------------------------
  ------- picaso ----------------------------------------
    while loading image in imageview do not set image view scale type swt it from icasoo
    Picasso.get()
    .load(url)
    .fit()
    .placeholder(R.drawable.no_image_available1)
    .centerCrop()
    .into(holder.binding.ivTop);

  ------------------------------------ gson list ---------------------------------------------------
    pointHistoryModelList = gson.fromJson(jsonObject.getJSONArray("data").toString(),
    new TypeToken<ArrayList<PointHistoryModel>>() {
    }.getType());


  ------------------------------------spinner work-------------------------------------------------
    using to stop spinner selection from selecting ******************************************
    private Boolean mIsSpinnerFirstCall = true;

    binding.toolbarimagebtn.spOrderStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    if(!mIsSpinnerFirstCall) {

    }else {

    mIsSpinnerFirstCall = false;

    }


  *************************************************************************************************************************/

 public static void clearAllActivity(Context context, Class<?> cls) {
     Intent intent = new Intent(context, cls);
     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
     context.startActivity(intent);
 }



}
