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

public class DigitalManagerFragment extends BaseFragment {
    private ViewPager mViewPager;
    private FramentManagerAdapter mAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private List<Fragment> mFragments;
    private String[] mTypes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createFragments();
        mTypes = getResources().getStringArray(R.array.digital);
    }
    private void createFragments(){
        mFragments = new ArrayList<>();
        mFragments.add(GoodsFragment.newInstance(DataUrl.SHENGHUO_DIANQI));
        mFragments.add(GoodsFragment.newInstance(DataUrl.CHUFANG_DIANQI));
        mFragments.add(GoodsFragment.newInstance(DataUrl.YINGYIN_SHITING));
        mFragments.add(GoodsFragment.newInstance(DataUrl.GEREN_HULI));
        mFragments.add(GoodsFragment.newInstance(DataUrl.YIDONG_DIANYUAN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.ERJI_ERMAI));
        mFragments.add(GoodsFragment.newInstance(DataUrl.SHUMA_PEIJIAN));
    }

    public DigitalManagerFragment() {
        // Required empty public constructor
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


}
