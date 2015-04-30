package com.shopping.swb.shopping.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-04-29
 * Time: 00:34
 */
public class FramentManagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> mFragments;
    private String[] mTypes;
    public FramentManagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] types) {
        super(fm);
        mFragments = fragments;
        mTypes = types;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments!=null&&!mFragments.isEmpty()?mFragments.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTypes[position];
    }

}
