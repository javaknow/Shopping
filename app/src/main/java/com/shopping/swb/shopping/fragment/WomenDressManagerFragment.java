package com.shopping.swb.shopping.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.adapter.FramentManagerAdapter;
import com.shopping.swb.shopping.constant.DataUrl;
import com.shopping.swb.shopping.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class WomenDressManagerFragment extends BaseFragment {
    private ViewPager mViewPager;
    private FramentManagerAdapter mAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private List<Fragment> mFragments;
    private String[] mTypes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createFragments();
        mTypes = getResources().getStringArray(R.array.women_dress);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mAdapter = new FramentManagerAdapter(getChildFragmentManager(),mFragments,mTypes);
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.indicator_tab);
        mSlidingTabLayout.setCustomTabView(R.layout.sliding_tab_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(android.R.color.white));
        mSlidingTabLayout.setViewPager(mViewPager);
        return view;
    }

    private void createFragments(){
        mFragments = new ArrayList<>();
        mFragments.add(GoodsFragment.newInstance(DataUrl.LIAN_YI_QUN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.BAN_SHENG_QUN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.T_XU));
        mFragments.add(GoodsFragment.newInstance(DataUrl.ZHEN_ZHI_SHAN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.CHEN_SHAN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.XUE_FANG_SHAN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.NIU_ZAI_KU));
        mFragments.add(GoodsFragment.newInstance(DataUrl.DA_DI_KU));
        mFragments.add(GoodsFragment.newInstance(DataUrl.NV_SHI_NEI_KU));
        mFragments.add(GoodsFragment.newInstance(DataUrl.WEN_GIONG));
        mFragments.add(GoodsFragment.newInstance(DataUrl.TAO_ZHUANG));
        mFragments.add(GoodsFragment.newInstance(DataUrl.ZHONG_LAO_NIAN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.DA_MA_NV_ZHUANG));
    }
}
