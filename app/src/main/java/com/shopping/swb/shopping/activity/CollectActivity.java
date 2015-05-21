package com.shopping.swb.shopping.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.adapter.GoodsAdapter;
import com.shopping.swb.shopping.db.CollectContract;
import com.shopping.swb.shopping.db.CollectDBHelper;
import com.shopping.swb.shopping.db.DBUtil;
import com.shopping.swb.shopping.entity.Goods;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends BaseActivity implements AdapterView.OnItemClickListener
        , View.OnClickListener {
    public static final int QUERY_ALL_DATA = 0;
    public static final int QUERY_SUCCESS = 1;
    public static final int QUEREY_FAILURE = 2;
    private Toolbar mToolbar;
    private GridView mGridView;
    private GoodsAdapter mGoodsAdapter;
    private CollectDBHelper mCollectDBHelper;
    private DBUtil mDBUtil;
    private Cursor mCursor;
    private List<Goods> mGoodsList = new ArrayList<>();
    private boolean mIsEdit, mIsSelect, mIsAllSelect;
    private View mSelectAll, mDeleteSelected, mCancelEdit;
    private ImageView mSelectAllImage;
    private TextView mSelectText;
    private View mEditLayout;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case QUERY_ALL_DATA:
                    mCursor = getInfoFromDB();
                    break;
                case QUERY_SUCCESS:
                    initDatas();
                    break;
                case QUEREY_FAILURE:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initView();

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setOnItemClickListener(this);
        mCollectDBHelper = new CollectDBHelper(this);
        mDBUtil = DBUtil.getInstance(mCollectDBHelper);
        mGoodsAdapter = new GoodsAdapter(this, mGoodsList);
        mGridView.setAdapter(mGoodsAdapter);
        mHandler.sendEmptyMessage(QUERY_ALL_DATA);
        mSelectAll = findViewById(R.id.select_all);
        mDeleteSelected = findViewById(R.id.delete_selected);
        mCancelEdit = findViewById(R.id.cancel_edit);
        mSelectAll.setOnClickListener(this);
        mDeleteSelected.setOnClickListener(this);
        mCancelEdit.setOnClickListener(this);
        mSelectAllImage = (ImageView) findViewById(R.id.select);
        mSelectText = (TextView) findViewById(R.id.select_text);
        mEditLayout = findViewById(R.id.edit_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            if (mGoodsAdapter.getCount() != 0) {
                if (!mIsEdit) {
                    mIsEdit = true;
                    mGoodsAdapter.setShow(mIsEdit);
                    mGoodsAdapter.notifyDataSetChanged();
                    showEditLayout(mIsEdit);
                }
            } else {
                Toast.makeText(this, R.string.no_collect, Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditLayout(boolean isShow) {
        if (isShow) {
            mEditLayout.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.umeng_socialize_slide_in_from_bottom);
            mEditLayout.startAnimation(animation);
        } else {
            mEditLayout.setVisibility(View.GONE);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.umeng_socialize_slide_out_from_bottom);
            mEditLayout.startAnimation(animation);
        }
    }

    private Cursor getInfoFromDB() {
        Cursor cursor = null;
        try {
            cursor = mDBUtil.query("select * from "
                    + CollectContract.CollectEntry.DATABASE_TABLE_COLLECT, null);
            mHandler.sendEmptyMessage(QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    private void initDatas() {
        for (int i = 0; i < mCursor.getCount(); i++) {
            mCursor.moveToPosition(i);
            Goods goods = new Goods();
            goods.setItem_id(mCursor.getString(mCursor.getColumnIndex(CollectContract.CollectEntry.COLUMNS_NUM_ID)));
            goods.setTitle(mCursor.getString(mCursor.getColumnIndex(CollectContract.CollectEntry.COLUMNS_TITLE)));
            goods.setPrice(mCursor.getDouble(mCursor.getColumnIndex(CollectContract.CollectEntry.COLUMNS_ORIGIN_PRICE)));
            goods.setPrice_with_rate(mCursor.getDouble(mCursor.getColumnIndex(CollectContract.CollectEntry.COLUMNS_NOW_PRICE)));
            goods.setDiscount(mCursor.getDouble(mCursor.getColumnIndex(CollectContract.CollectEntry.COLUMNS_DISCOUNT)));
            goods.setSold(mCursor.getString(mCursor.getColumnIndex(CollectContract.CollectEntry.COLUMNS_SOLD)));
            goods.setPic_path(mCursor.getString(mCursor.getColumnIndex(CollectContract.CollectEntry.COLUMNS_PIC_URL)));
            mGoodsList.add(goods);
        }
        mGoodsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoodsList.clear();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!mIsEdit) {
            Intent intent = new Intent(this, GoodsDetailActivity.class);
            intent.putExtra("id", mGoodsList.get(position).getItem_id());
            startActivity(intent);
        } else {
            mIsSelect = !mIsSelect;
            mGoodsList.get(position).setSelect(mIsSelect);
            mGoodsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                mIsAllSelect = !mIsAllSelect;
                setSelectAll(mIsAllSelect);
                for (int i = 0; i < mGoodsList.size(); i++) {
                    mGoodsList.get(i).setSelect(mIsAllSelect);
                }
                mGoodsAdapter.notifyDataSetChanged();
                break;
            case R.id.delete_selected:
                List<Goods> selects = new ArrayList<>();
                for (int i = 0; i < mGoodsList.size(); i++) {
                    if (mGoodsList.get(i).isSelect()) {
                        selects.add(mGoodsList.get(i));
                    }
                }
                mGoodsList.removeAll(selects);
                mGoodsAdapter.notifyDataSetChanged();
                for (int i = 0; i < selects.size(); i++) {
                    mDBUtil.delete(CollectContract.CollectEntry.DATABASE_TABLE_COLLECT,
                            CollectContract.CollectEntry.COLUMNS_NUM_ID +
                                    "=?",
                            new String[]{selects.get(i).getItem_id()});
                }
                if (mGoodsAdapter.getCount() == 0) {
                    showEditLayout(false);
                }
                break;
            case R.id.cancel_edit:
                mIsEdit = false;
                mGoodsAdapter.setShow(mIsEdit);
                mGoodsAdapter.notifyDataSetChanged();
                showEditLayout(mIsEdit);
                setSelectAll(false);
                break;
        }
    }

    private void setSelectAll(boolean isSelectAll) {
        if (isSelectAll) {
            mSelectAllImage.setImageResource(R.drawable.icon_selected);
            mSelectText.setText(R.string.cancel_select_all);
        } else {
            mSelectAllImage.setImageResource(R.drawable.icon_unselect);
            mSelectText.setText(R.string.select_all);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && View.VISIBLE == mEditLayout.getVisibility()) {
            showEditLayout(false);
            mGoodsAdapter.setShow(false);
            mGoodsAdapter.notifyDataSetChanged();
            setSelectAll(false);
            mIsEdit = false;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
