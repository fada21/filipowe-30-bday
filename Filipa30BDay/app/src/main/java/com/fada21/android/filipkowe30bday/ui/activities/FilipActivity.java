package com.fada21.android.filipkowe30bday.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.fada21.android.filipkowe30bday.R;
import com.fada21.android.filipkowe30bday.adapters.FilipPicsPagerAdapter;
import com.fada21.android.filipkowe30bday.events.EventShowDittyOnToggled;
import com.fada21.android.filipkowe30bday.events.EventShowDittyToggle;
import com.fada21.android.filipkowe30bday.io.IFilipCoverProvider;
import com.fada21.android.filipkowe30bday.io.LocalFilipCoverProvider;
import com.fada21.android.filipkowe30bday.io.helpers.DittyHelper;
import com.fada21.android.filipkowe30bday.model.FilipCover;
import com.fada21.android.filipkowe30bday.ui.view.ZoomOutPageTransformer;
import com.fada21.android.filipkowe30bday.utils.FilipCoverAppConsts;

import org.apache.http.protocol.HTTP;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;


public class FilipActivity extends AppCompatActivity {

    private FilipPicsPagerAdapter pagerAdapter;
    @InjectView(R.id.pager)
    ViewPager viewPager;
    private DittyHelper dittyHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.inject(this);

        checkIfFirstStartup();

        pagerAdapter = new FilipPicsPagerAdapter(getSupportFragmentManager());
        IFilipCoverProvider provider = LocalFilipCoverProvider.createProvider(this);
        pagerAdapter.setData(provider.getFilipCoverList());

        //final float pagerPageMargin = getResources().getDimension(R.dimen.pager_page_margin);
        //viewPager.setPageMargin((int) (pagerPageMargin * -1f));
        //viewPager.setClipChildren(false);
        //viewPager.setOffscreenPageLimit(2);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setAdapter(pagerAdapter);
    }

    private void checkIfFirstStartup() {
        boolean isFirst = false && PreferenceManager.getDefaultSharedPreferences(this).getBoolean(FilipCoverAppConsts.FIRST_STARTUP, false); // TODO uncomment when ready, not slash at the moment
        if (isFirst) {
            startActivity(new Intent(FilipActivity.this, FilipSplashActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filip, menu);

        MenuItem showDitty = menu.findItem(R.id.action_toggle_ditty);
        dittyHelper = new DittyHelper(this, showDitty);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        dittyHelper.setupActionBar();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                startActivity(getShareIntent());
                return true;
            case R.id.action_full_screen:
                return true;
            case R.id.action_toggle_ditty:
                EventBus.getDefault().post(new EventShowDittyToggle());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Intent getShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        FilipCover filipCover = pagerAdapter.getDataItem(viewPager.getCurrentItem());
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_wishes, filipCover.getTitle()));
        intent.putExtra(Intent.EXTRA_TEXT, filipCover2ShareText(filipCover));
        return intent;
    }

    private String filipCover2ShareText(FilipCover filipCover) {
        StringBuilder sb = new StringBuilder();
        sb.append(filipCover.getTitle()).append(" - ").append(filipCover.getUrl());
        String ditty = filipCover.getDitty();
        if (!TextUtils.isEmpty(ditty)) {
            sb.append("\n\n").append(Html.fromHtml(ditty).toString());
        }
        return sb.toString();
    }

    public void onEventMainThread(EventShowDittyToggle ev) {
        boolean doShowDitty = dittyHelper.toggleShowDitty();
        dittyHelper.setupActionBar();
        EventBus.getDefault().post(new EventShowDittyOnToggled(doShowDitty));
    }
}
