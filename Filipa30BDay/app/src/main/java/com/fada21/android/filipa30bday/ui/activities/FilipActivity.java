package com.fada21.android.filipa30bday.ui.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.fada21.android.filipa30bday.R;
import com.fada21.android.filipa30bday.adapters.FilipPicsPagerAdapter;
import com.fada21.android.filipa30bday.io.SimpleFilipCoverProvider;


public class FilipActivity extends ActionBarActivity {

    private FilipPicsPagerAdapter pagerAdapter;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        pagerAdapter = new FilipPicsPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setData(SimpleFilipCoverProvider.createProvider().getFilipPicsList());

        final float pagerPageMargin = getResources().getDimension(R.dimen.pager_page_margin);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setPageMargin((int) (pagerPageMargin * -1f));
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(2);
        //viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
