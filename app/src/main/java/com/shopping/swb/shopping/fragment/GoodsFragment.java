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
import com.shopping.swb.shopping.adapter.GoodsAdapter;
import com.shopping.swb.shopping.entity.Goods;
import com.shopping.swb.shopping.entity.GoodsList;
import com.shopping.swb.shopping.util.Utility;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;


public class GoodsFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
   // private static final String ARG_PARAM2 = "param2";

    private String mUrl;
    private String mTag;
    private static final int MSG_REQUEST_INFO = 0;
    private static final int MSG_SUCCESS = 1;
    private static final int MSG_FAILURE = 2;
    private GridView mGridView;
    private AsyncHttpClient mAsyncHttpClient;
    private List<Goods> mGoodsList = new ArrayList<>();
    private GoodsAdapter mGoodsAdapter;
    private CircularProgressBar mProgressBar;

    public static GoodsFragment newInstance(String url) {
        GoodsFragment fragment = new GoodsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
     //   args.putString(ARG_PARAM2, tag);
        fragment.setArguments(args);
        return fragment;
    }

    public GoodsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_goods, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridview);
        mProgressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoodsAdapter = new GoodsAdapter(mActivity,mGoodsList);
        mGridView.setAdapter(mGoodsAdapter);
        if(mGoodsAdapter.getCount()!=0){
            mProgressBar.setVisibility(View.GONE);
        }else {
            mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_REQUEST_INFO:
                  //  mProgressBar.setVisibility(View.VISIBLE);
                    getInfoFromServer();
                    break;
                case MSG_SUCCESS:
                    mProgressBar.setVisibility(View.GONE);
                    initGoods(msg.obj.toString());
                    break;
                case MSG_FAILURE:
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(mActivity, R.string.network_unavailable, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void getInfoFromServer(){
        if(mAsyncHttpClient == null){
            mAsyncHttpClient = new AsyncHttpClient();
        }
        mAsyncHttpClient.setTimeout(8000);
        mAsyncHttpClient.get(mUrl,new AsyncHttpResponseHandler() {
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
            GoodsList goodsList = Utility.getGoods(json, GoodsList.class);
            mGoodsList.clear();
            mGoodsList.addAll(goodsList.getList());
            mGoodsAdapter.notifyDataSetChanged();
        }catch (Exception e){
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
        if(mGoodsList!=null){
            mGoodsList.clear();
        }
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser&&mGoodsList.isEmpty()){
//            mHandler.sendEmptyMessage(MSG_REQUEST_INFO);
//        }
//    }
}
