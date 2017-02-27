package com.example.younkyu.soundplayer.fragments.util.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Younkyu on 2017-02-27.
 */

public class PagerAdapters extends FragmentStatePagerAdapter {


    List<Fragment> fragments;

    public PagerAdapters(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void add(Fragment fragment) {
        fragments.add(fragment);
    }
}