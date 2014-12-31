package com.fada21.android.filipa30bday.io.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(suppressConstructorProperties = true)
public class DittyHelper {
    @NonNull
    private final Context context;
    @NonNull
    private final Drawable dittyDrawable;

    public boolean doShowDitties() {
        return DittyStaticHelper.doShowDitties(context);
    }

    public void setShowDitties(boolean doShowDitties) {
        DittyStaticHelper.setShowDitties(context, doShowDitties);
    }

    public boolean toggleShowDitty() {
        return DittyStaticHelper.toggleShowDitty(context);
    }

    public void setShowDittyIconLevel() {
        DittyStaticHelper.setShowDittyIconLevel(dittyDrawable, doShowDitties());
    }


}
