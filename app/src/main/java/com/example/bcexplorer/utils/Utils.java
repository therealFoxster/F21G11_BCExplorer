package com.example.bcexplorer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Utils {

    //putBoolean
    public static void store(Context context, String key, boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(key, value).apply();
    }

    //getBoolean
    public static boolean getBoolean(Context context, String key, boolean defaultvalue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, defaultvalue);
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }
}


