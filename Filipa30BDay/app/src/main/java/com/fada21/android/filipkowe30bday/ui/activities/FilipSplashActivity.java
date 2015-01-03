package com.fada21.android.filipkowe30bday.ui.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.fada21.android.filipkowe30bday.FilipApp;
import com.fada21.android.filipkowe30bday.R;
import com.fada21.android.filipkowe30bday.utils.FilipCoverAppConsts;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FilipSplashActivity extends ActionBarActivity {

    @InjectView(R.id.img_filip_cover)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_splash);
        ButterKnife.inject(this);
        FilipApp.getInstance().getPicasso().load("file:///android_asset/novakovski.png").noPlaceholder().error(R.drawable.ic_no_img).fit().centerInside().into(img);
        img.setOnClickListener(v -> {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(FilipCoverAppConsts.FIRST_STARTUP, false).commit();
            onBackPressed();
        });
    }

}
