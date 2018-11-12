package com.mamacgroup.jaayz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Chinni on 03-05-2016.
 */
public class Session {
    public static final String SERVERURL = "http://clients.mamacgroup.com/jaay/api/";
    public static final String WEBURL = "http://clients.mamacgroup.com/jaay/api/";
    public static final String PAYMENT_URL   = "http://clients.mamacgroup.com/jaay/api/Tap.php?";
    static Context context;
    static SharedPreferences sharedPreferences;
    static String lan_key = "minwain_lan";
    static String words_key = "minwain_words";
    static String is_first = "is_first";
    static String fav_news = "fav_news";
    public static final String USERID = "minwain_id";
    public static final String NAME = "minwain_name";


    public static void setUserid(Context context, String member_id, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERID, member_id);
        editor.putString(NAME, name);
        editor.apply();

    }
    public static String getUserid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USERID, "-1");

    }
    public static void setAreaid(Context context, String member_id, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("area_id", member_id);
        editor.putString("area_name", name);
        editor.apply();

    }
    public static String getAreaid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("area_id", "-1");

    }
    public static String getAreaName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("area_name", "");

    }
    public static void setWish(Context context, String c_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(fav_news, c_id);
        editor.apply();
    }

    public static String getWish(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(fav_news, "-1");
    }
    public static void set_user_language(Context context, String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(lan_key,user_id);
        editor.commit();
    }
    public static String get_user_language(Context context){
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getString(lan_key, "en");
        }catch (Exception ex){
            return "en";
        }
    }
    public static void set_user_language_words(Context context, String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(words_key,user_id);
        editor.commit();
    }

    public static String get_lan(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(sharedPreferences.getString(lan_key,"en").equals("ar"))
        {
            return "_ar";
        }
        return "";
    }
    public static JSONObject get_user_language_words(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(sharedPreferences.getString(words_key,"-1"));
            jsonObject = jsonObject.getJSONObject(get_user_language(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static String getword(Context context, String word)
    {

        JSONObject words = get_user_language_words(context);

        try {
            return words.getString(word);
        } catch (JSONException e) {
            e.printStackTrace();
            return word;
        }
    }

    public static   void forceRTLIfSupported(Activity activity) {
        context = activity.getApplicationContext();
        SharedPreferences sharedPref;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        Log.e("lan", sharedPref.getString(lan_key, "-1"));

        if (sharedPref.getString(lan_key, "-1").equals("en")) {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("en".toLowerCase());
            res.updateConfiguration(conf, dm);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

        else if(sharedPref.getString(lan_key, "-1").equals("ar")){
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("ar".toLowerCase());
            res.updateConfiguration(conf, dm);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

        else {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("en".toLowerCase());
            res.updateConfiguration(conf, dm);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

    }
    public static String is_first_get(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(is_first,"-1");
    }
    public static  void is_first_set(Context context, String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(is_first,user_id);
        editor.commit();
    }
    public static void set_minimizetime(Context context){
        Date date = new Date(System.currentTimeMillis());
        long millis = date.getTime();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("timer_key", date.getTime()).apply();
        editor.commit();
    }
    public static void get_minimizetime(Context context) {

        Date resume = new Date(System.currentTimeMillis());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Date pause = new Date(sharedPreferences.getLong("timer_key", 0));
        Log.e("time_diff", "test");
        if(sharedPreferences.getLong("timer_key",0)!=0) {

            long resume_time = System.currentTimeMillis();
            long pause_time = sharedPreferences.getLong("timer_key", 0);
            long diff = resume_time-pause_time;
            Log.e("time_diff", String.valueOf(diff));

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("timer_key", 0).apply();

            long twentyfiveminutes = 1*1000*60*20;

            Log.e("time_25", String.valueOf(twentyfiveminutes));

            if(diff>twentyfiveminutes){

                //    deleteCache(context);

                Intent i = context.getPackageManager()
                        .getLaunchIntentForPackage( context.getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);


            }

        }

    }
    public static void setSettings(Context context, String settings) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("settings", settings);
        editor.commit();
    }

    public static String getSettings(Context context, String word) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject;
        String temp;
        try {
            jsonObject=new JSONObject( sharedPreferences.getString("settings","-1"));
            temp=jsonObject.getString(word);
        } catch (JSONException e) {
            temp=word;
            e.printStackTrace();
        }

        return temp;
    }
    public static void setMemberDetails(Context context, String settings) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("member", settings);
        editor.commit();
    }

    public static String getMemberDetails(Context context, String word) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject;
        String temp;
        try {
            jsonObject=new JSONObject( sharedPreferences.getString("member","-1"));
            temp=jsonObject.getString(word);
        } catch (JSONException e) {
            temp="";
            e.printStackTrace();
        }

        return temp;
    }

}
