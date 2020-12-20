package com.alaa.todolistapp.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

public class AppPreferences {

    public final static String USER_UID = "user_uid";

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

    public String getUserUId() {
        return pref.getString(USER_UID, "");
    }

    public void setUserUId(String userUId) {
        editor.putString(USER_UID, userUId);
        editor.apply();
    }

    public void clean() {
        editor.clear();
        editor.apply();
    }
}