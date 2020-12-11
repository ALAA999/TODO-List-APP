package com.alaa.todolistapp.utils;

import android.app.Application;

public class AppController extends Application {

    private static AppController appController;
    private AppPreferences appPreferences;

    public static synchronized AppController getInstance() {
        return appController;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appController = this;
        appPreferences = new AppPreferences(this);
    }

    public AppPreferences getAppPreferences() {
        return appPreferences;
    }
}