package com.appentus.shedistrict.Utils;

import android.app.Activity;
import android.content.Context;

import com.appentus.shedistrict.models.CountriesBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class Country {

    public static CountriesBean getCountries(Activity activity){
        return new Gson().fromJson(loadJSONFromAsset(activity),CountriesBean.class);
    }

    private static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
