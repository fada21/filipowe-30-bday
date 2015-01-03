package com.fada21.android.filipka30bday.io.helpers;

import android.content.Context;
import android.view.MenuItem;

import com.fada21.android.filipka30bday.R;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class DittyHelper {

    public static final int SHOW_DITTY_LEVEL_ON = 0;
    public static final int SHOW_DITTY_LEVEL_OFF = 1;

    @NonNull
    private final Context context;
    @NonNull
    private final MenuItem menuItem;

    @Getter
    @Setter
    private boolean doShowDitty;

    public DittyHelper(Context context, MenuItem menuItem) {
        this.context = context;
        this.menuItem = menuItem;
        doShowDitty = DittyStaticHelper.doShowDitties(context);
    }

    public boolean doShowDitties() {
        return doShowDitty;
    }

    public void setShowDitties(boolean doShowDitties) {
        DittyStaticHelper.setShowDitties(context, doShowDitties);
        setDoShowDitty(doShowDitties);
    }

    public boolean toggleShowDitty() {
        setDoShowDitty(DittyStaticHelper.toggleShowDitty(context));
        return doShowDitties();
    }

    /**
     * Do on UI thread.
     */
    public void setupActionBar() {
        boolean doShowDitties = doShowDitties();
        if (doShowDitties) {
            menuItem.setTitle(context.getString(R.string.action_show_ditty_off));
            menuItem.getIcon().setLevel(SHOW_DITTY_LEVEL_OFF);
        } else {
            menuItem.setTitle(context.getString(R.string.action_show_ditty_on));
            menuItem.getIcon().setLevel(SHOW_DITTY_LEVEL_ON);
        }
    }

}
