package com.example.ebuyzone;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedDataGetSet {

    public static String getMySavedToken(Context context){
        SharedPreferences preferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token_in_func = preferences.getString("token","");
        String res_token = "Token " + token_in_func;
        return res_token;
    }

    public static String getMySavedUsername(Context context){
        SharedPreferences preferences = context.getSharedPreferences("myPrefs1", Context.MODE_PRIVATE);
        String username_in_func = preferences.getString("username","");
        return username_in_func;
    }

}