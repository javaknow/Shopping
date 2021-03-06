package com.shopping.swb.shopping.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.constant.DataUrl;
import com.shopping.swb.shopping.db.CollectContract;
import com.shopping.swb.shopping.db.CollectDBHelper;
import com.shopping.swb.shopping.db.DBUtil;
import com.shopping.swb.shopping.util.Utility;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class GoodsDetailActivity extends ActionBarActivity {
    private Toolbar mToolbar;
    private String mGoodsId;
    private String mTitle;
    private double mNowPrice;
    private double mOriginPrice;
    private double mDiscount;
    private String mSold;
    private String mPicUrl;
    private WebView mWebView;
    private CircularProgressBar mLoding;
    private MenuItem mCollect;
    private DBUtil mDBUtil;
    private CollectDBHelper mCollectDBHelper;
    private Cursor mCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        mGoodsId = getIntent().getStringExtra("id");
        mTitle = getIntent().getStringExtra("title");
        mNowPrice = getIntent().getDoubleExtra("now_price", -1);
        mOriginPrice = getIntent().getDoubleExtra("origin_price", -1);
        mDiscount = getIntent().getDoubleExtra("discount", -1);
        mSold = getIntent().getStringExtra("sold");
        mPicUrl = getIntent().getStringExtra("pic_url");
        initView();
    }
    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLoding = (CircularProgressBar) findViewById(R.id.loading);
        mWebView = (WebView) findViewById(R.id.goods_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(DataUrl.PREFIX_GOODS_DETAIL_URL+mGoodsId);
        // 在WebView中打开链接（默认行为是使用浏览器，设置此项后都用WebView打开）
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mLoding.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        mCollectDBHelper = new CollectDBHelper(this);
        mDBUtil = DBUtil.getInstance(mCollectDBHelper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_goods_detail, menu);
        mCollect = menu.findItem(R.id.action_collect);
        mCursor = query();
        if (mCursor.getCount() != 0) {
            mCollect.setIcon(R.drawable.icon_collected);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_collect) {
            if (Utility.isNetworkAvailable(this)) {
                mCursor = query();
                ContentValues values = new ContentValues();
                values.put(CollectContract.CollectEntry.COLUMNS_NUM_ID, mGoodsId);
                values.put(CollectContract.CollectEntry.COLUMNS_TITLE, mTitle);
                values.put(CollectContract.CollectEntry.COLUMNS_NOW_PRICE, mNowPrice);
                values.put(CollectContract.CollectEntry.COLUMNS_ORIGIN_PRICE, mOriginPrice);
                values.put(CollectContract.CollectEntry.COLUMNS_DISCOUNT, mDiscount);
                values.put(CollectContract.CollectEntry.COLUMNS_SOLD, mSold);
                values.put(CollectContract.CollectEntry.COLUMNS_PIC_URL, mPicUrl);
                if (mCursor.getCount() == 0) {
                    if (mDBUtil.insert(CollectContract.CollectEntry.DATABASE_TABLE_COLLECT, null, values)
                            > 0) {
                        Toast.makeText(this, R.string.collect_success, Toast.LENGTH_SHORT).show();
                        mCollect.setIcon(R.drawable.icon_collected);
                    } else {
                        Toast.makeText(this, R.string.collect_fail, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.collected, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Cursor query() {
        Cursor cursor = null;
        try {
            cursor = mDBUtil.query("select * from "
                    + CollectContract.CollectEntry.DATABASE_TABLE_COLLECT
                    + " where "
                    + CollectContract.CollectEntry.COLUMNS_NUM_ID
                    + "=?", new String[]{mGoodsId});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }
    /**
     * 按键响应，在WebView中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个WebView返回退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
        {
            // 返回键退回
            mWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up
        // to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
