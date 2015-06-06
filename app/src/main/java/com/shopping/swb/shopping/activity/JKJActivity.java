package com.shopping.swb.shopping.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.melnykov.fab.FloatingActionButton;
import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.adapter.JKJAdapter;
import com.shopping.swb.shopping.entity.JKJData;
import com.shopping.swb.shopping.entity.JKJList;
import com.shopping.swb.shopping.util.Utility;
import com.shopping.swb.shopping.view.paginggridview.PagingGridView;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class JKJActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
        , AdapterView.OnItemClickListener, PagingGridView.Pagingable, View.OnClickListener {
    private static final int MSG_REQUEST_INFO = 0;
    private static final int MSG_SUCCESS = 1;
    private static final int MSG_FAILURE = 2;
    private AsyncHttpClient mAsyncHttpClient;
    private ImageView mImageView;
    private FloatingActionButton mActionButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PagingGridView mPagingGridView;
    private CircularProgressBar mProgressBar;
    private Toolbar mToolbar;
    private String mTitle, mUrl;
    private int mPage = 0;
    private int mTotalPage = 1;
    private List<JKJData> mGoodsList = new ArrayList<>();
    private JKJAdapter mGoodsAdapter;
    private boolean mIsPullDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jkj);
        mTitle = getIntent().getStringExtra("title");
        mUrl = getIntent().getStringExtra("url");
        initView();
        setListener();
        mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        mPagingGridView = (PagingGridView) findViewById(R.id.paging_gridview);
        mProgressBar = (CircularProgressBar) findViewById(R.id.progress_bar);
        mImageView = (ImageView) findViewById(R.id.have_no_data);
        mActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mActionButton.hide();
        mGoodsAdapter = new JKJAdapter(this, mGoodsList);
        mPagingGridView.setHasMoreItems(true);
    }

    private void setListener() {
        mPagingGridView.setPagingableListener(this);
        mPagingGridView.setOnItemClickListener(this);
        mActionButton.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jkj, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REQUEST_INFO:
                    getInfoFromServer(mUrl);
                    break;
                case MSG_SUCCESS:
                    mProgressBar.setVisibility(View.GONE);
                    mImageView.setVisibility(View.GONE);
                    initGoods(msg.obj.toString());
                    break;
                case MSG_FAILURE:
                    mProgressBar.setVisibility(View.GONE);
                    if (mPage == 0 && !mIsPullDown) {
                        mImageView.setVisibility(View.VISIBLE);
                    }
                    setLoadingMore(false,getResources().getString(R.string.network_unavailable)
                            + getResources().getString(R.string.click_to_reroading));
                    mPagingGridView.getLoadingView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setLoadingMore(true,getResources().getString(R.string.loading_text));
                            mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
                        }
                    });
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private void getInfoFromServer(String url) {
        if (mAsyncHttpClient == null) {
            mAsyncHttpClient = new AsyncHttpClient();
        }
        mAsyncHttpClient.setTimeout(8000);
        RequestParams params = new RequestParams();
        params.put("page", mPage);
        mAsyncHttpClient.get(url, params, new AsyncHttpResponseHandler() {
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
            JKJList goodsList = Utility.getData(json, JKJList.class);
            mTotalPage = goodsList.getTotal_page();
            if (mPage == 0) {
                mGoodsList.clear();
            }
            mGoodsList.addAll(goodsList.getList());
            if (mPagingGridView.getAdapter() == null) {
                mPagingGridView.setAdapter(mGoodsAdapter);
            }
            mGoodsAdapter.notifyDataSetChanged();
            mPagingGridView.setHasMoreItems(true);
            mSwipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_FAILURE);
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPage = 0;
        mIsPullDown = true;
        mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position < mGoodsList.size()) {
            Intent intent = new Intent(this, GoodsDetailActivity.class);
            intent.putExtra("id", mGoodsList.get(position).getId());
            intent.putExtra("title", mGoodsList.get(position).getTitle());
            intent.putExtra("origin_price", mGoodsList.get(position).getOri_price());
            intent.putExtra("now_price", mGoodsList.get(position).getNow_price());
            intent.putExtra("discount", mGoodsList.get(position).getDiscount());
            intent.putExtra("sold", mGoodsList.get(position).getSold_volu());
            intent.putExtra("pic_url", mGoodsList.get(position).getPic_url());
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoodsList != null) {
            mGoodsList.clear();
        }
    }

    @Override
    public void onLoadMoreItems() {
        mPage++;
        if (mPage < mTotalPage) {
            setLoadingMore(true, getResources().getString(R.string.loading_text));
            if (Utility.isNetworkAvailable(this)) {
                mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
            } else {
                mHandler.sendEmptyMessage(MSG_FAILURE);
            }
        } else {
            setLoadingMore(false, getResources().getString(R.string.no_data));
        }
    }

    private void setLoadingMore(boolean showProgressBar, String text) {
        mPagingGridView.getLoadingView().setShowProgress(showProgressBar);
        mPagingGridView.getLoadingView().setLoadingText(text);
    }

    @Override
    public void onScrollStateChanged(int scrollState, int firstVisibleItem) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                mActionButton.hide();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                if (firstVisibleItem == 0) {
                    mActionButton.hide();
                } else {
                    mActionButton.show();
                }
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                mActionButton.hide();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (mGoodsAdapter != null) {
            mPagingGridView.setAdapter(mGoodsAdapter);
            mActionButton.hide();
        }
    }
}
