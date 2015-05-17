package com.shopping.swb.shopping.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.melnykov.fab.FloatingActionButton;
import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.activity.GoodsDetailActivity;
import com.shopping.swb.shopping.adapter.ShouYeGoodsAdapter;
import com.shopping.swb.shopping.constant.DataUrl;
import com.shopping.swb.shopping.entity.ShouYeGoods;
import com.shopping.swb.shopping.entity.ShouYeGoodsList;
import com.shopping.swb.shopping.util.Utility;
import com.shopping.swb.shopping.view.pulltorefresh.PullToRefreshBase;
import com.shopping.swb.shopping.view.pulltorefresh.PullToRefreshGridView;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class AllManagerFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2
               ,AdapterView.OnItemClickListener,View.OnClickListener,AbsListView.OnScrollListener{
    private static final int MSG_REQUEST_INFO = 0;
    private static final int MSG_SUCCESS = 1;
    private static final int MSG_FAILURE = 2;
    private PullToRefreshGridView mGridView;
    private AsyncHttpClient mAsyncHttpClient;
    private List<ShouYeGoods> mGoodsList = new ArrayList<>();
    private ShouYeGoodsAdapter mGoodsAdapter;
    private CircularProgressBar mProgressBar;
    private ImageView mImageView;
    private FloatingActionButton mActionButton;
    private int mFirstVisibleItem = 0;
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
        mGridView = (PullToRefreshGridView) view.findViewById(R.id.gridview);
        mGridView.setOnRefreshListener(this);
        mGridView.setOnItemClickListener(this);
        mProgressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar);
        mImageView = (ImageView) view.findViewById(R.id.have_no_data);
        mActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mActionButton.hide();
        mActionButton.setOnClickListener(this);
        mGridView.getRefreshableView().setOnScrollListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoodsAdapter = new ShouYeGoodsAdapter(mActivity,mGoodsList);
        mGridView.setAdapter(mGoodsAdapter);
        mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_REQUEST_INFO:
                    getInfoFromServer();
                    break;
                case MSG_SUCCESS:
                    mProgressBar.setVisibility(View.GONE);
                    mImageView.setVisibility(View.GONE);
                    initGoods(msg.obj.toString());
                    break;
                case MSG_FAILURE:
                    mProgressBar.setVisibility(View.GONE);
                    if(mGoodsAdapter.getCount() == 0){
                        mImageView.setVisibility(View.VISIBLE);
                    }
                    mGridView.onRefreshComplete();
                    break;
            }
        }
    };
    private void getInfoFromServer(){
        if(mAsyncHttpClient == null){
            mAsyncHttpClient = new AsyncHttpClient();
        }
        mAsyncHttpClient.setTimeout(8000);
        mAsyncHttpClient.get(DataUrl.SHOUYE_URL,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String json = new String(bytes,0,bytes.length);
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_SUCCESS,json));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    mHandler.sendEmptyMessage(MSG_FAILURE);
            }
        });
    }
    private void initGoods(String json){
        try {
            ShouYeGoodsList goodsList = Utility.getGoods(json, ShouYeGoodsList.class);
            mGoodsList.clear();
            mGoodsList.addAll(goodsList.getList());
            mGoodsAdapter.notifyDataSetChanged();
            mGridView.onRefreshComplete();
        }catch (Exception e){
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_FAILURE);
        }
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
        if(mGoodsList!=null){
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
        intent.putExtra("id",mGoodsList.get(position).getNum_iid());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(mGoodsAdapter!=null){
            mGridView.setAdapter(mGoodsAdapter);
            mActionButton.hide();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState){
            case SCROLL_STATE_FLING:
                mActionButton.hide();
                break;
            case SCROLL_STATE_IDLE:
                if(mFirstVisibleItem == 0){
                    mActionButton.hide();
                }else {
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
}
