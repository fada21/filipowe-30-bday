package com.fada21.android.filipa30bday.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fada21.android.filipa30bday.R;
import com.fada21.android.filipa30bday.model.FilipCover;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FilipCoverFragment extends Fragment {

    private static final String ARG_FILIP_COVER = "FILIP_COVER";

    private FilipCover filipCover;

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

        FilipCover filipCover = getFilipCover(savedInstanceState);

        Picasso.with(getActivity()).load(filipCover.getUrl()).noPlaceholder().error(R.drawable.ic_no_img).into(img);

        tvDitty.setText(filipCover.getDitty());
        tvDitty.setOnClickListener(v -> Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show());

        return rootView;
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

}
