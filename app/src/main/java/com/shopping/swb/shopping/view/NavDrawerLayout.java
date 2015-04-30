package com.shopping.swb.shopping.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shopping.swb.shopping.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-04-28
 * Time: 13:59
 */
public class NavDrawerLayout extends LinearLayout implements View.OnClickListener{
    private View mUserCenter;
    private View mAll;
    private View mWomen;
    private View mMen;
    private View mFurniture;
    private View mShoes;
    private View mOrnament;
    private View mDigital;
    private View mFood;
    private View mMore;
    private TextView mTextAll;
    private TextView mTextWomen;
    private TextView mTextMen;
    private TextView mTextFurniture;
    private TextView mTextShoes;
    private TextView mTextOrnament;
    private TextView mTextDigital;
    private TextView mTextFood;
    private TextView mTextMore;
    private Context mContext;
    private String mTitle;
    private NavDrawerClickListener mNavDrawerClickListener;
    private List<TextView> mTextViewList;

    public interface NavDrawerClickListener{
        public void gotoUserCenter();
        public void setTitle(int position,String title);
    }
    public NavDrawerLayout(Context context) {
        super(context);
        mContext = context;
    }

    public NavDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public NavDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mUserCenter = findViewById(R.id.user_center);
        mAll = findViewById(R.id.all);
        mWomen = findViewById(R.id.women_dress);
        mMen = findViewById(R.id.men_clothing);
        mFurniture = findViewById(R.id.furniture);
        mShoes = findViewById(R.id.shoes);
        mOrnament = findViewById(R.id.ornament);
        mDigital = findViewById(R.id.digital);
        mFood = findViewById(R.id.food);
        mMore = findViewById(R.id.more);

        mTextAll = (TextView) findViewById(R.id.text_all);
        mTextWomen = (TextView) findViewById(R.id.text_women_dress);
        mTextMen = (TextView) findViewById(R.id.text_men_clothing);
        mTextFurniture = (TextView) findViewById(R.id.text_furniture);
        mTextShoes = (TextView) findViewById(R.id.text_shoes);
        mTextOrnament = (TextView) findViewById(R.id.text_ornament);
        mTextDigital = (TextView) findViewById(R.id.text_digital);
        mTextFood = (TextView) findViewById(R.id.text_food);
        mTextMore = (TextView) findViewById(R.id.text_others);

        mTextViewList = new ArrayList<>();
        mTextViewList.add(mTextAll);
        mTextViewList.add(mTextWomen);
        mTextViewList.add(mTextMen);
        mTextViewList.add(mTextFurniture);
        mTextViewList.add(mTextShoes);
        mTextViewList.add(mTextOrnament);
        mTextViewList.add(mTextDigital);
        mTextViewList.add(mTextFood);
        mTextViewList.add(mTextMore);

        mUserCenter.setOnClickListener(this);
        mAll.setOnClickListener(this);
        mWomen.setOnClickListener(this);
        mMen.setOnClickListener(this);
        mFurniture.setOnClickListener(this);
        mShoes.setOnClickListener(this);
        mOrnament.setOnClickListener(this);
        mDigital.setOnClickListener(this);
        mFood.setOnClickListener(this);
        mMore.setOnClickListener(this);

        mTextAll.setTextColor(getResources().getColor(R.color.primary_color));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int positon = 0;
        switch (id){
            case R.id.user_center:
                if(mNavDrawerClickListener != null){
                    mNavDrawerClickListener.gotoUserCenter();
                }
                break;
            case R.id.all:
                mTitle = getResources().getString(R.string.search_all);
                positon = 0;
                break;
            case R.id.women_dress:
                mTitle = getResources().getString(R.string.search_skirt);
                positon = 1;
                break;
            case R.id.men_clothing:
                mTitle = getResources().getString(R.string.search_palus);
                positon = 2;
                break;
            case R.id.furniture:
                mTitle = getResources().getString(R.string.search_furniture);
                positon = 3;
                break;
            case R.id.shoes:
                mTitle = getResources().getString(R.string.search_shoes);
                positon = 4;
                break;
            case R.id.ornament:
                mTitle = getResources().getString(R.string.search_ornament);
                positon = 5;
                break;
            case R.id.digital:
                mTitle = getResources().getString(R.string.search_digital);
                positon = 6;
                break;
            case R.id.food:
                mTitle = getResources().getString(R.string.search_food);
                positon = 7;
                break;
            case R.id.more:
                mTitle = getResources().getString(R.string.search_more);
                positon = 8;
                break;
        }
        if(mNavDrawerClickListener != null){
            mNavDrawerClickListener.setTitle(positon,mTitle);
        }

        for(int i=0;i<mTextViewList.size();i++){
            if(i == positon){
                mTextViewList.get(i).setTextColor(getResources().getColor(R.color.primary_color));
            }else{
                mTextViewList.get(i).setTextColor(getResources().getColor(R.color.category_text_color));
            }
        }
    }
     public void setNavDrawerClickListener(NavDrawerClickListener navDrawerListener){
         mNavDrawerClickListener = navDrawerListener;
     }
}
