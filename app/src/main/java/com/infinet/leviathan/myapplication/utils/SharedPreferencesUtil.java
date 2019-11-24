package com.infinet.leviathan.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static SharedPreferences sp;
    private static Context context;

    public static void saveString(Context context, String key, String values) {
        context.getSharedPreferences("config", Context.MODE_PRIVATE).edit().putString(key, values).commit();
    }

    public static String getString(Context context, String key) {
        String config = context.getSharedPreferences("config", Context.MODE_PRIVATE).getString(key, "");
        return config;
    }

    public static void saveBoolean(Context context, String key, boolean b) {
        context.getSharedPreferences("config", Context.MODE_PRIVATE).edit().putBoolean(key, b).commit();
    }

    public static boolean getBoolean(Context context, String key) {
        boolean config = context.getSharedPreferences("config", Context.MODE_PRIVATE).getBoolean(key, false);
        return config;
    }

}
