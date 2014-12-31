package com.fada21.android.filipa30bday.fragments;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.Toast;

import com.fada21.android.filipa30bday.FilipApp;
import com.fada21.android.filipa30bday.R;
import com.fada21.android.filipa30bday.io.helpers.DittyStaticHelper;
import com.fada21.android.filipa30bday.model.FilipCover;
import com.squareup.picasso.Callback;

import org.parceler.Parcels;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FilipCoverFragment extends Fragment {

    private static final String ARG_FILIP_COVER = "FILIP_COVER";
    private static final int ALPHA_FILTER = 0xAAFFFFFF;

    private FilipCover filipCover;

    private TextView tvDitty;
    private ImageView img;
    private boolean detached;

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

        FilipCover filipCover = getFilipCover(savedInstanceState);

        String ditty = filipCover.getDitty();
        if (DittyStaticHelper.doShowDitties(getActivity()) && !TextUtils.isEmpty(ditty)) {
            tvDitty.setVisibility(View.VISIBLE);
            tvDitty.setText(Html.fromHtml(ditty));
        } else {
            tvDitty.setVisibility(View.GONE);
        }

        tvDitty.setOnClickListener(v -> Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show());

        return rootView;
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
                        Integer colorFromBg = getResources().getColor(R.color.colorPrimaryDarkAlpha);
                        Integer colorToBg = palette.getDarkMutedColor(R.color.colorPrimaryDark);
                        colorToBg &= ALPHA_FILTER;
                        Integer colorFromText = getResources().getColor(android.R.color.white);
                        Integer colorToText = palette.getLightVibrantColor(android.R.color.white);
                        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFromBg, colorToBg);
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                tvDitty.getBackground().setColorFilter(new PorterDuffColorFilter((Integer) animator.getAnimatedValue(), PorterDuff.Mode.DST_OVER));
                            }
                        });
                        ValueAnimator colorTextAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFromText, colorToText);
                        colorTextAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                tvDitty.setTextColor((Integer) animator.getAnimatedValue());
                            }
                        });
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(colorTextAnimation, colorAnimation);
                        set.start();
                    });
                }
            }
        };
        return emptyCallback;
    }

    private FilipCover getFilipCover(Bundle savedInstanceState) {
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        detached = false;
    }

    @Override
    public void onDetach() {
        detached = true;
        super.onDetach();
    }
}
