package com.example.android.politicalpreparedness;

import android.app.Application;

import timber.log.Timber;

public class PoliticalPreparednessApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }
}