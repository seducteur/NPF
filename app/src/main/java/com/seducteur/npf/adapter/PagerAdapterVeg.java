package com.seducteur.npf.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by x-note on 2016-03-04.
 */
public class PagerAdapterVeg extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public PagerAdapterVeg(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return this.mFragments.get(i);
    }

    @Override
    public int getCount() {
        return this.mFragments.size();
    }
}
