package com.fada21.android.filipa30bday.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fada21.android.filipa30bday.fragments.FilipCoverFragment;
import com.fada21.android.filipa30bday.model.FilipCover;
import com.fada21.android.filipa30bday.utils.CommonUtils;

import java.util.List;

public class FilipPicsPagerAdapter extends FragmentPagerAdapter {

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
            return "No data!"; // TODO to res
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
