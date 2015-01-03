package com.fada21.android.filipkowe30bday;

import android.app.Application;

import com.squareup.picasso.Picasso;

import lombok.Getter;

public class FilipApp extends Application {

    @Getter
    private static FilipApp instance;

    @Getter
    private Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initPicasso();

    }

    private void initPicasso() {
        picasso = (new Picasso.Builder(getApplicationContext())).loggingEnabled(BuildConfig.DEBUG).build();

    }

}
