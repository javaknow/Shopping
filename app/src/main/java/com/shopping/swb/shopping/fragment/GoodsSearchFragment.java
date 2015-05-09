package com.shopping.swb.shopping.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.melnykov.fab.FloatingActionButton;
import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.activity.GoodsDetailActivity;
import com.shopping.swb.shopping.adapter.GoodsAdapter;
import com.shopping.swb.shopping.entity.Goods;
import com.shopping.swb.shopping.entity.GoodsList;
import com.shopping.swb.shopping.util.SortGoodsByPrice;
import com.shopping.swb.shopping.util.SortGoodsBySold;
import com.shopping.swb.shopping.util.Utility;
import com.shopping.swb.shopping.view.pulltorefresh.PullToRefreshBase;
import com.shopping.swb.shopping.view.pulltorefresh.PullToRefreshGridView;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;


public class GoodsSearchFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2
        , AdapterView.OnItemClickListener, View.OnClickListener,AbsListView.OnScrollListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    // private static final String ARG_PARAM2 = "param2";

    private String mUrl;
    private String mTag;
    private static final int MSG_REQUEST_INFO = 0;
    private static final int MSG_SUCCESS = 1;
    private static final int MSG_FAILURE = 2;
    private PullToRefreshGridView mGridView;
    private AsyncHttpClient mAsyncHttpClient;
    private List<Goods> mGoodsList = new ArrayList<>();
    private GoodsAdapter mGoodsAdapter;
    private CircularProgressBar mProgressBar;
    private ImageView mImageView, mArrow;
    private TextView mRenQi, mSold, mPriceText;
    private View mPriceLayout;
    private boolean mIsDown = false;
    private int mFlag = 0;
    private FloatingActionButton mActionButton;

    public static GoodsSearchFragment newInstance(String url) {
        GoodsSearchFragment fragment = new GoodsSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        //   args.putString(ARG_PARAM2, tag);
        fragment.setArguments(args);
        return fragment;
    }

    public GoodsSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM1);
            //      mTag = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods_search, container, false);
        mGridView = (PullToRefreshGridView) view.findViewById(R.id.pulltorefresh_gridview);
        mGridView.setOnRefreshListener(this);
        mGridView.setOnItemClickListener(this);
        mProgressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar);
        mImageView = (ImageView) view.findViewById(R.id.have_no_data);
        mRenQi = (TextView) view.findViewById(R.id.ren_qi);
        mSold = (TextView) view.findViewById(R.id.xiao_liang);
        mPriceLayout = view.findViewById(R.id.price_layout);
        mPriceText = (TextView) view.findViewById(R.id.price_text);
        mArrow = (ImageView) view.findViewById(R.id.arrow);
        mActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mActionButton.hide();
        mActionButton.setOnClickListener(this);
        mGridView.getRefreshableView().setOnScrollListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoodsAdapter = new GoodsAdapter(mActivity, mGoodsList);
        mGridView.setAdapter(mGoodsAdapter);
        mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
        mRenQi.setOnClickListener(this);
        mSold.setOnClickListener(this);
        mPriceLayout.setOnClickListener(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REQUEST_INFO:
                    //  mProgressBar.setVisibility(View.VISIBLE);
                    getInfoFromServer();
                    break;
                case MSG_SUCCESS:
                    mProgressBar.setVisibility(View.GONE);
                    mImageView.setVisibility(View.GONE);
                    initGoods(msg.obj.toString());
                    break;
                case MSG_FAILURE:
                    mProgressBar.setVisibility(View.GONE);
                    if (mGoodsAdapter.getCount() == 0) {
                        mImageView.setVisibility(View.VISIBLE);
                    }
                    mGridView.onRefreshComplete();
                    break;
            }
        }
    };

    private void getInfoFromServer() {
        if (mAsyncHttpClient == null) {
            mAsyncHttpClient = new AsyncHttpClient();
        }
        mAsyncHttpClient.setTimeout(8000);
        mAsyncHttpClient.get(mUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes, 0, bytes.length);
                mHandler.sendMessage(mHandler.obtainMessage(MSG_SUCCESS, json));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendEmptyMessage(MSG_FAILURE);
            }
        });
    }

    private void initGoods(String json) {
        try {
            GoodsList goodsList = Utility.getGoods(json, GoodsList.class);
            mGoodsList.clear();
            mGoodsList.addAll(goodsList.getList());
            mGoodsAdapter.notifyDataSetChanged();
            mGridView.onRefreshComplete();
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_FAILURE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(mGoodsAdapter.getCount()!=0){
//            mProgressBar.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_ABBREV_ALL);
        // 设置刷新时候的字体
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mActivity, GoodsDetailActivity.class);
        intent.putExtra("id", mGoodsList.get(position).getItem_id());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ren_qi:
                mRenQi.setTextColor(getResources().getColor(R.color.primary_color));
                mSold.setTextColor(getResources().getColor(R.color.category_text_color));
                mPriceText.setTextColor(getResources().getColor(R.color.category_text_color));
                mGoodsAdapter = new GoodsAdapter(mActivity,mGoodsList);
                mGridView.setAdapter(mGoodsAdapter);
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(mActivity, GoodsDetailActivity.class);
                        intent.putExtra("id", mGoodsList.get(position).getItem_id());
                        startActivity(intent);
                    }
                });
                if(mFlag == 0){
                    mIsDown = false;
                    mArrow.setImageResource(R.drawable.ic_arrowup);
                }else{
                    mIsDown = true;
                    mArrow.setImageResource(R.drawable.ic_arrowdown);
                }
                break;
            case R.id.xiao_liang:
                mRenQi.setTextColor(getResources().getColor(R.color.category_text_color));
                mSold.setTextColor(getResources().getColor(R.color.primary_color));
                mPriceText.setTextColor(getResources().getColor(R.color.category_text_color));
                if (mGoodsList != null && !mGoodsList.isEmpty()) {
                   final List<Goods> list = new ArrayList<>();
                    list.addAll(mGoodsList);
                    Collections.sort(list, new SortGoodsBySold());
                    mGoodsAdapter = new GoodsAdapter(mActivity,list);
                    mGridView.setAdapter(mGoodsAdapter);
                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           Intent intent = new Intent(mActivity, GoodsDetailActivity.class);
                           intent.putExtra("id", list.get(position).getItem_id());
                           startActivity(intent);
                       }
                   });
                }
                if(mFlag == 0){
                    mIsDown = false;
                    mArrow.setImageResource(R.drawable.ic_arrowup);
                }else{
                    mIsDown = true;
                    mArrow.setImageResource(R.drawable.ic_arrowdown);
                }
                break;
            case R.id.price_layout:
                mRenQi.setTextColor(getResources().getColor(R.color.category_text_color));
                mSold.setTextColor(getResources().getColor(R.color.category_text_color));
                mPriceText.setTextColor(getResources().getColor(R.color.primary_color));
                if (!mIsDown) {
                    mArrow.setImageResource(R.drawable.ic_arrowup_blue);
                    mIsDown = true;
                    mFlag = 0;
                } else {
                    mArrow.setImageResource(R.drawable.ic_arrowdown_blue);
                    mIsDown = false;
                    mFlag = 1;
                }
                if (mGoodsList != null && !mGoodsList.isEmpty()) {
                    final List<Goods> list = new ArrayList<>();
                    list.addAll(mGoodsList);
                    Collections.sort(list, new SortGoodsByPrice(mFlag));
                    mGoodsAdapter = new GoodsAdapter(mActivity,list);
                    mGridView.setAdapter(mGoodsAdapter);
                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(mActivity, GoodsDetailActivity.class);
                            intent.putExtra("id", list.get(position).getItem_id());
                            startActivity(intent);
                        }
                    });
                }
                break;
            case R.id.fab:
                if(mGoodsAdapter!=null){
                    mGridView.setAdapter(mGoodsAdapter);
                    mActionButton.hide();
                }
                break;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState){
            case SCROLL_STATE_FLING:
                mActionButton.hide();
                break;
            case SCROLL_STATE_IDLE:
                mActionButton.show();
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                mActionButton.hide();
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser&&mGoodsList.isEmpty()){
//            mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
//        }
//    }
}
