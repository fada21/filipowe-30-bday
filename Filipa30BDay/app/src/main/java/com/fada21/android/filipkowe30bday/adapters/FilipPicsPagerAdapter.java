package com.fada21.android.filipkowe30bday.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fada21.android.filipkowe30bday.FilipApp;
import com.fada21.android.filipkowe30bday.R;
import com.fada21.android.filipkowe30bday.fragments.FilipCoverFragment;
import com.fada21.android.filipkowe30bday.model.FilipCover;
import com.fada21.android.filipkowe30bday.utils.CommonUtils;

import java.util.List;

public class FilipPicsPagerAdapter extends FragmentStatePagerAdapter {

    private List<FilipCover> data;

    public FilipPicsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<FilipCover> filipCoverList) {
        data = filipCoverList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return FilipCoverFragment.newInstance(data.get(position));
    }

    @Override
    public int getCount() {
        if (CommonUtils.isCollectionEmpty(data)) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (CommonUtils.isCollectionEmpty(data)) {
            return FilipApp.getInstance().getString(R.string.error_no_data);
        } else {
            return getDataItem(position).getTitle();
        }
    }

    public FilipCover getDataItem(int position) {
        if (CommonUtils.isCollectionEmpty(data)) {
            return null;
        } else {
            return data.get(position);
        }
    }

}
