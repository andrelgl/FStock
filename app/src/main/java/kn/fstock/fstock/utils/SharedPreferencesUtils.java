package kn.fstock.fstock.utils;

import android.content.Context;
import android.content.SharedPreferences;

import kn.fstock.fstock.R;

public class SharedPreferencesUtils {
    private static String user_pref_id = "user_pref_id";
    private static String user_pref_name = "user_pref_name";
    private static String user_pref_email = "user_pref_email";

    public static int getUserId(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getInt(user_pref_id, -1);
    }

    public static String getUserName(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(user_pref_name, "");
    }

    public static String getUserEmail(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(user_pref_email, "");
    }

    public static void putUserId(Context context, int id){
      context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
              .edit()
              .putInt(user_pref_id, id)
              .apply();
    }
    public static void putUserName(Context context, String name){
        context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                .edit()
                .putString(user_pref_name, name)
                .apply();
    }
    public static void putUserEmail(Context context, String email){
        context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                .edit()
                .putString(user_pref_email, email)
                .apply();
    }
}
