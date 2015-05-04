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


public class MenClothingManagerFragment extends BaseFragment {
    private ViewPager mViewPager;
    private FramentManagerAdapter mAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private List<Fragment> mFragments;
    private String[] mTypes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createFragments();
        mTypes = getResources().getStringArray(R.array.men_clothing);
    }
    private void createFragments(){
        mFragments = new ArrayList<>();
        mFragments.add(GoodsFragment.newInstance(DataUrl.MEN_T_XU));
        mFragments.add(GoodsFragment.newInstance(DataUrl.MEN_CHEN_SHAN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.MEN_POLO_SHAN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.MEN_BEI_XIN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.MEN_NIU_ZAI_KU));
        mFragments.add(GoodsFragment.newInstance(DataUrl.MEN_XIU_XIAN_KU));
        mFragments.add(GoodsFragment.newInstance(DataUrl.MEN_GONG_ZHUANG));
        mFragments.add(GoodsFragment.newInstance(DataUrl.MEN_JIA_KE));
        mFragments.add(GoodsFragment.newInstance(DataUrl.MEN_ZHONG_LAO_NIAN));
        mFragments.add(GoodsFragment.newInstance(DataUrl.MEN_NEI_KU));
    }

    public MenClothingManagerFragment() {
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
