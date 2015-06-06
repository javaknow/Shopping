package com.shopping.swb.shopping.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.melnykov.fab.FloatingActionButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.activity.AdvertisementActivity;
import com.shopping.swb.shopping.activity.GoodsDetailActivity;
import com.shopping.swb.shopping.activity.JKJActivity;
import com.shopping.swb.shopping.activity.RecommendActivity;
import com.shopping.swb.shopping.adapter.AdvertisementAdapter;
import com.shopping.swb.shopping.adapter.ShouYeGoodsAdapter;
import com.shopping.swb.shopping.constant.DataUrl;
import com.shopping.swb.shopping.entity.Advertisement;
import com.shopping.swb.shopping.entity.AdvertisementData;
import com.shopping.swb.shopping.entity.ShouYeGoods;
import com.shopping.swb.shopping.entity.ShouYeGoodsList;
import com.shopping.swb.shopping.util.Utility;
import com.shopping.swb.shopping.view.AutoScrollViewPager;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

public class AllManagerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
        , AdapterView.OnItemClickListener, View.OnClickListener, AbsListView.OnScrollListener
        , ViewPager.OnPageChangeListener {
    private static final int MSG_REQUEST_INFO = 0;
    private static final int MSG_SUCCESS = 1;
    private static final int MSG_FAILURE = 2;
    private static final int LOAD_GOODS = 3;
    private static final int LOAD_ADVERTISEMENT = 4;
    private GridViewWithHeaderAndFooter mGridView;
    private AsyncHttpClient mAsyncHttpClient;
    private List<ShouYeGoods> mGoodsList = new ArrayList<>();
    private ShouYeGoodsAdapter mGoodsAdapter;
    private CircularProgressBar mProgressBar;
    private ImageView mImageView;
    private FloatingActionButton mActionButton;
    private int mFirstVisibleItem = 0;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AutoScrollViewPager mViewPager;
    private List<ImageView> mImageViews = new ArrayList<>();
    private List<ImageView> mDots = new ArrayList<>();
    private AdvertisementAdapter mAdvertisementAdapter;
    private List<AdvertisementData> mAdvertisementDatas = new ArrayList<>();
    private View mHeaderView;
    private LinearLayout mIndicator;
    private View mJkj,mTwenty,mRecommend,mForenotice;

    public AllManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_manager, container, false);
        initView(view);
        setListener();
        return view;
    }
    private void initView(View view){
        mGridView = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gridview);
        mHeaderView = LayoutInflater.from(mActivity).inflate(R.layout.header_layout, null);
        mGridView.addHeaderView(mHeaderView);
        mHeaderView.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        mViewPager = (AutoScrollViewPager) mHeaderView.findViewById(R.id.viewpager);
        mIndicator = (LinearLayout) mHeaderView.findViewById(R.id.indicator);
        mProgressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar);
        mImageView = (ImageView) view.findViewById(R.id.have_no_data);
        mActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mActionButton.hide();
        mJkj = mHeaderView.findViewById(R.id.jkj_bao_you);
        mTwenty = mHeaderView.findViewById(R.id._20fengding);
        mRecommend = mHeaderView.findViewById(R.id.recommend);
        mForenotice = mHeaderView.findViewById(R.id.forenotice);

    }
    private void setListener(){
        mGridView.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mActionButton.setOnClickListener(this);
        mGridView.setOnScrollListener(this);
        mJkj.setOnClickListener(this);
        mTwenty.setOnClickListener(this);
        mRecommend.setOnClickListener(this);
        mForenotice.setOnClickListener(this);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoodsAdapter = new ShouYeGoodsAdapter(mActivity, mGoodsList);
        mGridView.setAdapter(mGoodsAdapter);
        mAdvertisementAdapter = new AdvertisementAdapter(mActivity, mImageViews);
        mViewPager.setAdapter(mAdvertisementAdapter);
        mViewPager.setOnPageChangeListener(this);
        mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REQUEST_INFO:
                    getInfoFromServer(DataUrl.SHOUYE_URL, LOAD_GOODS);
                    getInfoFromServer(DataUrl.PREFIX_ADVERTISEMENT, LOAD_ADVERTISEMENT);
                    break;
                case MSG_SUCCESS:
                    mProgressBar.setVisibility(View.GONE);
                    mImageView.setVisibility(View.GONE);
                    mHeaderView.setVisibility(View.VISIBLE);
                    if (msg.arg1 == LOAD_GOODS) {
                        initGoods(msg.obj.toString());
                    } else {
                        initAdvertisement(msg.obj.toString());
                    }
                    break;
                case MSG_FAILURE:
                    mProgressBar.setVisibility(View.GONE);
                    if (mGoodsAdapter.getCount() == 0) {
                        mImageView.setVisibility(View.VISIBLE);
                        mHeaderView.setVisibility(View.GONE);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private void getInfoFromServer(String url, final int loadType) {
        if (mAsyncHttpClient == null) {
            mAsyncHttpClient = new AsyncHttpClient();
        }
        mAsyncHttpClient.setTimeout(8000);
        mAsyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes, 0, bytes.length);
                mHandler.sendMessage(mHandler.obtainMessage(MSG_SUCCESS, loadType, -1, json));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendEmptyMessage(MSG_FAILURE);
            }
        });
    }

    private void initGoods(String json) {
        try {
            ShouYeGoodsList goodsList = Utility.getData(json, ShouYeGoodsList.class);
            mGoodsList.clear();
            mGoodsList.addAll(goodsList.getList());
            mGoodsAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_FAILURE);
        }
    }

    private void initAdvertisement(String json) {
        try {
            Advertisement advertisement = Utility.getData(json, Advertisement.class);
            mAdvertisementDatas.clear();
            mAdvertisementDatas.addAll(advertisement.getData());
            initAdvertisementImg();
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_FAILURE);
        }
    }

    private void initAdvertisementImg() {
        mImageViews.clear();
        if(mAdvertisementDatas.size() > 3){
            mAdvertisementDatas.remove(0);
        }
        for (int i = 0; i < mAdvertisementDatas.size(); i++) {
            ImageView imageView = new ImageView(mActivity);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setImageResource(R.drawable.default_image);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(layoutParams);
            ImageLoader.getInstance().displayImage(mAdvertisementDatas.get(i).getIphoneimg(), imageView);
            mImageViews.add(imageView);
        }
        mAdvertisementAdapter.notifyDataSetChanged();
        createIndicator();
        mViewPager.setInterval(2000);
        mViewPager.startAutoScroll();
    }

    private void createIndicator() {
        mDots.clear();
        mIndicator.removeAllViews();
        for (int i = 0; i < mImageViews.size(); i++) {
            ImageView imageView = new ImageView(mActivity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
            layoutParams.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(layoutParams);
            if (i == 0) {
               imageView.setImageResource(R.drawable.dot_blue);
            } else {
               imageView.setImageResource(R.drawable.dot_white);
            }
            mDots.add(imageView);
            mIndicator.addView(imageView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewPager.stopAutoScroll();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoodsList != null) {
            mGoodsList.clear();
        }
        if(mImageViews != null){
            mImageViews.clear();
        }
        if(mDots != null){
            mDots.clear();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mActivity, GoodsDetailActivity.class);
        intent.putExtra("id", mGoodsList.get(position).getNum_iid());
        intent.putExtra("title", mGoodsList.get(position).getTitle());
        intent.putExtra("origin_price", mGoodsList.get(position).getOrigin_price());
        intent.putExtra("now_price", mGoodsList.get(position).getNow_price());
        intent.putExtra("discount", mGoodsList.get(position).getDiscount());
        intent.putExtra("sold", mGoodsList.get(position).getDeal_num());
        intent.putExtra("pic_url", mGoodsList.get(position).getPic_url());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (mGoodsAdapter != null) {
                    mGridView.setAdapter(mGoodsAdapter);
                    mActionButton.hide();
                }
                break;
            case R.id.jkj_bao_you:
                Intent jkjIntent = new Intent(mActivity, JKJActivity.class);
                jkjIntent.putExtra("title",getResources().getString(R.string.jkj_bao_you));
                jkjIntent.putExtra("url",DataUrl.PREFIX_JKJBAOYOU);
                startActivity(jkjIntent);
                break;
            case R.id._20fengding:
                Intent twentyIntent = new Intent(mActivity, JKJActivity.class);
                twentyIntent.putExtra("title",getResources().getString(R.string._20_feng_ding));
                twentyIntent.putExtra("url",DataUrl.PREFIX_20FENGDING);
                startActivity(twentyIntent);
                break;
            case R.id.recommend:
                Intent recommendIntent = new Intent(mActivity, RecommendActivity.class);
                recommendIntent.putExtra("title",getResources().getString(R.string.everyday_recommend));
                recommendIntent.putExtra("url",DataUrl.EVERYDAY_RECOMMEND);
                startActivity(recommendIntent);
                break;
            case R.id.forenotice:
                Intent forenoticeIntent = new Intent(mActivity, RecommendActivity.class);
                forenoticeIntent.putExtra("title",getResources().getString(R.string.tomorrow_forenotice));
                forenoticeIntent.putExtra("url",DataUrl.FORENOTICE);
                startActivity(forenoticeIntent);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_FLING:
                mActionButton.hide();
                break;
            case SCROLL_STATE_IDLE:
                if (mFirstVisibleItem == 0) {
                    mActionButton.hide();
                } else {
                    mActionButton.show();
                }
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                mActionButton.hide();
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(final int position) {
        for (int i = 0; i < mDots.size(); i++) {
            if (i == position) {
                mDots.get(i).setImageResource(R.drawable.dot_blue);
            } else {
                mDots.get(i).setImageResource(R.drawable.dot_white);
            }
        }
        mImageViews.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,AdvertisementActivity.class);
                intent.putExtra("title",mAdvertisementDatas.get(position).getTitle());
                intent.putExtra("link",mAdvertisementDatas.get(position).getLink());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
