package com.shopping.swb.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shopping.swb.shopping.R;

public class UserCenterActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    private View mOrder,mCart,mLogistics,mCollect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
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
        mOrder = findViewById(R.id.order);
        mCart = findViewById(R.id.cart);
        mLogistics = findViewById(R.id.logistic);
        mCollect = findViewById(R.id.collect);
        mOrder.setOnClickListener(this);
        mCart.setOnClickListener(this);
        mLogistics.setOnClickListener(this);
        mCollect.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_center, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order:
                Intent orderIntent = new Intent(this,OrderActivity.class);
                startActivity(orderIntent);
                break;
            case R.id.cart:
                Intent cartIntent = new Intent(this,CartActivity.class);
                startActivity(cartIntent);
                break;
            case R.id.logistic:
                Intent logisticsIntent = new Intent(this,LogisticsActivity.class);
                startActivity(logisticsIntent);
                break;
            case R.id.collect:
                Intent collectIntent = new Intent(this,CollectActivity.class);
                startActivity(collectIntent);
                break;
        }
    }
}
