package com.fada21.android.filipa30bday;

import android.app.Application;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

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
        OkHttpClient okHttpClient = new OkHttpClient();
        Cache cache = null;
        try {
            cache = new Cache(getCacheDir(), 50 * 1024 * 1024);
        } catch (IOException e) {
            // TODO log
        }
        okHttpClient.setCache(cache);
        OkHttpDownloader okHttpDownloader = new OkHttpDownloader(okHttpClient);
        picasso = (new Picasso.Builder(getApplicationContext())).downloader(okHttpDownloader).build();

    }

    private File createCacheDir() {
        File cache = new File(getCacheDir(), "filip_covers_cache");
        if (!cache.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cache.mkdirs();
        }
        return cache;
    }
}
