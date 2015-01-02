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
import android.widget.ScrollView;
import android.widget.TextView;

import com.fada21.android.filipa30bday.FilipApp;
import com.fada21.android.filipa30bday.R;
import com.fada21.android.filipa30bday.events.EventShowDittyOnToggled;
import com.fada21.android.filipa30bday.events.EventShowDittyToggle;
import com.fada21.android.filipa30bday.io.helpers.DittyStaticHelper;
import com.fada21.android.filipa30bday.model.FilipCover;
import com.squareup.picasso.Callback;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FilipCoverFragment extends Fragment {

    private static final String ARG_FILIP_COVER = "FILIP_COVER";
    private static final int ALPHA_FILTER = 0xE0FFFFFF;

    private volatile boolean detached;
    private Context ctx;

    private FilipCover filipCover;

    private volatile boolean filipCoverAuthorReady;

    @InjectView(R.id.text_filip_cover_ditty)
    TextView tvDitty;
    @InjectView(R.id.img_filip_cover)
    ImageView img;
    @InjectView(R.id.text_filip_cover_author)
    TextView tvFilipCoverAuthor;
    @InjectView(R.id.scroll_filip_cover_ditty)
    ScrollView scrollDitty;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_filip, container, false);
        ButterKnife.inject(this, rootView);

        initFilipCover(savedInstanceState);

        boolean doShowDitties = DittyStaticHelper.doShowDitties(ctx);
        setupDitty(doShowDitties);
        setFilipCoverAuthorVisibility(doShowDitties);

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
                    scrollDitty.setVisibility(View.VISIBLE);
                    tvDitty.setOnClickListener(v -> {
                        if (!detached) EventBus.getDefault().post(new EventShowDittyToggle());
                    });
                } else {
                    scrollDitty.setVisibility(View.GONE);
                }
            } else {
                scrollDitty.setVisibility(View.GONE);
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

            Context context;

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onSuccess() {
                synchronized (FilipCoverFragment.this) {
                    if (!detached) {
                        context = ctx;
                    }
                }
                if (!detached) {
                    Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    Palette.generateAsync(bitmap, palette -> {
                        if (!detached) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                ValueAnimator colorAnimationForText = getValueAnimatorForText(palette);
                                ValueAnimator colorAnimationForApla = getValueAnimatorForApla(palette);
                                AnimatorSet set = new AnimatorSet();
                                set.playTogether(colorAnimationForText, colorAnimationForApla);
                                set.start();
                            } else {
                                tvDitty.setTextColor(palette.getLightVibrantColor(R.color.textForCovers));
                            }
                        } else {
                            context = null;
                        }
                    });
                    getActivity().runOnUiThread(() -> setupAuthor());
                } else {
                    context = null;
                }
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            private ValueAnimator getValueAnimatorForText(Palette palette) {
                Integer colorFromText = context.getResources().getColor(R.color.textForCovers);
                Integer colorToText = palette.getLightVibrantColor(R.color.textForCovers);
                ValueAnimator colorTextAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFromText, colorToText);
                colorTextAnimation.addUpdateListener(animator -> {
                    tvDitty.setTextColor((Integer) animator.getAnimatedValue());
                });
                return colorTextAnimation;
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            private ValueAnimator getValueAnimatorForApla(Palette palette) {
                Integer colorFromBg = context.getResources().getColor(R.color.colorPrimaryDark);
                Integer colorToBg = palette.getDarkMutedColor(R.color.colorPrimaryDark);
                colorToBg &= ALPHA_FILTER;
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFromBg, colorToBg);
                colorAnimation.addUpdateListener(animator -> {
                    tvDitty.getBackground().setColorFilter(new PorterDuffColorFilter((Integer) animator.getAnimatedValue(), PorterDuff.Mode.SRC_IN));
                });
                return colorAnimation;
            }
        };
        return emptyCallback;
    }

    private void setupAuthor() {
        String filipCoverAuthor = filipCover.getAuthor();
        if (!TextUtils.isEmpty(filipCoverAuthor)) {
            filipCoverAuthorReady = true;
            tvFilipCoverAuthor.setText(getString(R.string.ditty_author_label, filipCoverAuthor));
            setFilipCoverAuthorVisibility(DittyStaticHelper.doShowDitties(ctx));
        } else {
            tvFilipCoverAuthor.setVisibility(View.GONE);
        }
    }

    private void setFilipCoverAuthorVisibility(boolean isToBeShown) {
        if (filipCoverAuthorReady && isToBeShown) {
            tvFilipCoverAuthor.setVisibility(View.VISIBLE);
        } else {
            tvFilipCoverAuthor.setVisibility(View.GONE);
        }
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
        boolean toBeShown = ev.isDittyToBeShown();
        setupDitty(toBeShown);
        setFilipCoverAuthorVisibility(toBeShown);
    }
}
