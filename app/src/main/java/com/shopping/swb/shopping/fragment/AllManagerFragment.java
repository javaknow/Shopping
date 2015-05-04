package com.shopping.swb.shopping.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.adapter.ShouYeGoodsAdapter;
import com.shopping.swb.shopping.constant.DataUrl;
import com.shopping.swb.shopping.entity.ShouYeGoods;
import com.shopping.swb.shopping.entity.ShouYeGoodsList;
import com.shopping.swb.shopping.util.Utility;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class AllManagerFragment extends BaseFragment {
    private static final int MSG_REQUEST_INFO = 0;
    private static final int MSG_SUCCESS = 1;
    private static final int MSG_FAILURE = 2;
    private GridView mGridView;
    private AsyncHttpClient mAsyncHttpClient;
    private List<ShouYeGoods> mGoodsList = new ArrayList<>();
    private ShouYeGoodsAdapter mGoodsAdapter;
    private CircularProgressBar mProgressBar;
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
        mGridView = (GridView) view.findViewById(R.id.gridview);
        mProgressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar);
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
                    initGoods(msg.obj.toString());
                    break;
                case MSG_FAILURE:
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(mActivity,R.string.network_unavailable,Toast.LENGTH_SHORT).show();
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
        ShouYeGoodsList goodsList = Utility.getGoods(json, ShouYeGoodsList.class);
        mGoodsList.addAll(goodsList.getList());
        mGoodsAdapter.notifyDataSetChanged();
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
}
