package com.fada21.android.filipa30bday.ui.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fada21.android.filipa30bday.R;
import com.fada21.android.filipa30bday.adapters.FilipPicsPagerAdapter;
import com.fada21.android.filipa30bday.io.IFilipCoverProvider;
import com.fada21.android.filipa30bday.io.LocalFilipCoverProvider;
import com.fada21.android.filipa30bday.io.helpers.DittyHelper;
import com.fada21.android.filipa30bday.io.helpers.DittyStaticHelper;
import com.fada21.android.filipa30bday.ui.view.ZoomOutPageTransformer;


public class FilipActivity extends ActionBarActivity {

    private FilipPicsPagerAdapter pagerAdapter;

    private ViewPager viewPager;
    private Drawable dittyIconDrawable;
    private DittyHelper dittyHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        pagerAdapter = new FilipPicsPagerAdapter(getSupportFragmentManager());
        IFilipCoverProvider provider = LocalFilipCoverProvider.createProvider(this);
        pagerAdapter.setData(provider.getFilipCoverList());

        viewPager = (ViewPager) findViewById(R.id.pager);
        //final float pagerPageMargin = getResources().getDimension(R.dimen.pager_page_margin);
        //viewPager.setPageMargin((int) (pagerPageMargin * -1f));
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filip, menu);
        MenuItem showDitty = menu.findItem(R.id.action_toggle_ditty);
        dittyIconDrawable = showDitty.getIcon();
        dittyHelper = new DittyHelper(this, dittyIconDrawable);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        dittyHelper.setShowDittyIconLevel();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                return true;
            case R.id.action_full_screen:
                return true;
            case R.id.action_toggle_ditty:
                dittyHelper.toggleShowDitty();
                dittyHelper.setShowDittyIconLevel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
