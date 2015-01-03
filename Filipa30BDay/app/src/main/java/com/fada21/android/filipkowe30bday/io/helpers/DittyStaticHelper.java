package com.fada21.android.filipkowe30bday.io.helpers;

import android.content.Context;
import android.preference.PreferenceManager;

import com.fada21.android.filipkowe30bday.utils.FilipCoverAppConsts;

public class DittyStaticHelper {

    public static boolean doShowDitties(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(FilipCoverAppConsts.SHOW_DITTY, true);
    }

    public static void setShowDitties(Context context, boolean doShowDitties) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(FilipCoverAppConsts.SHOW_DITTY, doShowDitties).commit();
    }

    public static boolean toggleShowDitty(Context context) {
        boolean newShowDitties = !doShowDitties(context);
        setShowDitties(context, newShowDitties);
        return newShowDitties;
    }

}
