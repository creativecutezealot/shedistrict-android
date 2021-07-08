package com.appentus.shedistrict.Utils;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PrefManager {
    private static final String PrefDB = "partner";


    public static void putString(String KEY, String Value) {
        SharedPreferences sp = SheDistrict.getInstance().getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY, Value);
        editor.apply();
    }

    public static String getString(String KEY) {
        SharedPreferences sp = SheDistrict.getInstance().getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getString(KEY, "");
    }



    public static void putBoolean(String KEY, Boolean Value) {
        SharedPreferences sp = SheDistrict.getInstance().getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, Value);
        editor.apply();
    }

    public static Boolean getBoolean(String KEY) {
        SharedPreferences sp = SheDistrict.getInstance().getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getBoolean(KEY, false);
    }

    public static void clear() {
        SharedPreferences sp = SheDistrict.getInstance().getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

}
