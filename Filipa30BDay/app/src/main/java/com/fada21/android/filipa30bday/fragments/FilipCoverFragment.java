package com.fada21.android.filipa30bday.fragments;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fada21.android.filipa30bday.FilipApp;
import com.fada21.android.filipa30bday.R;
import com.fada21.android.filipa30bday.events.EventShowDittyOnToggled;
import com.fada21.android.filipa30bday.events.EventShowDittyToggle;
import com.fada21.android.filipa30bday.io.helpers.DittyStaticHelper;
import com.fada21.android.filipa30bday.model.FilipCover;
import com.squareup.picasso.Callback;

import org.parceler.Parcels;

import de.greenrobot.event.EventBus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FilipCoverFragment extends Fragment {

    private static final String ARG_FILIP_COVER = "FILIP_COVER";
    private static final int ALPHA_FILTER = 0xAAFFFFFF;

    private FilipCover filipCover;

    private volatile boolean detached;
    private Context ctx;
    private TextView tvDitty;
    private ImageView img;

    public static FilipCoverFragment newInstance(FilipCover filipCover) {
        FilipCoverFragment fragment = new FilipCoverFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FILIP_COVER, Parcels.wrap(filipCover));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_FILIP_COVER, Parcels.wrap(filipCover));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_filip, container, false);
        tvDitty = (TextView) rootView.findViewById(R.id.text_filip_cover_ditty);
        img = (ImageView) rootView.findViewById(R.id.img_filip_cover);

        initFilipCover(savedInstanceState);

        setupDitty(DittyStaticHelper.doShowDitties(ctx));
        String ditty = filipCover.getDitty();
        if (DittyStaticHelper.doShowDitties(getActivity()) && !TextUtils.isEmpty(ditty)) {
            tvDitty.setVisibility(View.VISIBLE);
            tvDitty.setText(Html.fromHtml(ditty));
        } else {
            tvDitty.setVisibility(View.GONE);
        }


        return rootView;
    }

    private FilipCover initFilipCover(Bundle savedInstanceState) {
        if (filipCover == null) {
            if (savedInstanceState != null) {
                Parcelable fpp = savedInstanceState.getParcelable(ARG_FILIP_COVER);
                if (fpp != null) {
                    filipCover = Parcels.unwrap(fpp);
                }
            }
            if (filipCover == null) {
                Bundle arguments = getArguments();
                if (arguments != null) {
                    Parcelable parcelable = arguments.getParcelable(ARG_FILIP_COVER);
                    filipCover = Parcels.unwrap(parcelable);
                }
            }
        }
        return filipCover;
    }

    private void setupDitty(boolean dittyToBeShown) {
        if (!detached) {
            String ditty = filipCover.getDitty();
            if (!TextUtils.isEmpty(ditty)) {
                tvDitty.setText(Html.fromHtml(ditty));
                if (dittyToBeShown) {
                    tvDitty.setVisibility(View.VISIBLE);
                    tvDitty.setOnClickListener(v -> {
                        if (!detached) EventBus.getDefault().post(new EventShowDittyToggle());
                    });
                } else {
                    tvDitty.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FilipApp.getInstance().getPicasso().load(filipCover.getUrl()).noPlaceholder().error(R.drawable.ic_no_img).fit().centerInside().into(img, getFilipCoverCallback());
    }

    private Callback getFilipCoverCallback() {
        Callback.EmptyCallback emptyCallback = new Callback.EmptyCallback() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onSuccess() {
                if (!detached && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    Palette.generateAsync(bitmap, palette -> {

                        ValueAnimator colorAnimationForDittyText = getValueAnimatorForDittyText(palette);
                        ValueAnimator colorAnimationForDittyApla = getValueAnimatorForDittyApla(palette);

                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(colorAnimationForDittyText, colorAnimationForDittyApla);
                        set.start();
                    });
                }
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            private ValueAnimator getValueAnimatorForDittyText(Palette palette) {
                Integer colorFromText = getResources().getColor(android.R.color.white);
                Integer colorToText = palette.getLightVibrantColor(android.R.color.white);
                ValueAnimator colorTextAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFromText, colorToText);
                colorTextAnimation.addUpdateListener(animator -> tvDitty.setTextColor((Integer) animator.getAnimatedValue()));
                return colorTextAnimation;
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            private ValueAnimator getValueAnimatorForDittyApla(Palette palette) {
                Integer colorFromBg = getResources().getColor(R.color.colorPrimaryDarkAlpha);
                Integer colorToBg = palette.getDarkMutedColor(R.color.colorPrimaryDark);
                colorToBg &= ALPHA_FILTER;
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFromBg, colorToBg);
                colorAnimation.addUpdateListener(animator -> tvDitty.getBackground().setColorFilter(new PorterDuffColorFilter((Integer) animator.getAnimatedValue(), PorterDuff.Mode.DST_OVER)));
                return colorAnimation;
            }
        };
        return emptyCallback;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        detached = false;
        ctx = activity;
    }

    @Override
    public void onDetach() {
        synchronized (this) {
            detached = true;
            ctx = null;
        }
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEventMainThread(EventShowDittyOnToggled ev) {
        setupDitty(ev.isDittyToBeShown());
    }
}
