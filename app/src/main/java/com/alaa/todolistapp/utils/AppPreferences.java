package com.alaa.todolistapp.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

public class AppPreferences {

    public final static String USER = "user";

    private static final String PREF_NAME = "AppSettingsPreferences";

    private int PRIVATE_MODE = 0;
    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context context;

    public AppPreferences(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public FirebaseUser getUser() {
        Gson gson = new Gson();
        String json = pref.getString(USER, "");
        return gson.fromJson(json, FirebaseUser.class);
    }

    public void setUser(FirebaseUser user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(USER, json);
        editor.commit();
    }

    public void clean() {
        editor.clear();
        editor.apply();
    }
}