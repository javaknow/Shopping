package com.shopping.swb.shopping.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.constant.DataUrl;
import com.shopping.swb.shopping.fragment.GoodsFragment;
import com.shopping.swb.shopping.fragment.GoodsSearchFragment;

public class SearchDetailActivity extends BaseActivity {
    private Toolbar mToolbar;
    private String mSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearch = getIntent().getStringExtra("search");
        setContentView(R.layout.activity_search_detail);
        initView();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, GoodsSearchFragment.newInstance(DataUrl.PREFIX_SEARCH + mSearch))
                .commit();
    }
    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mSearch);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_detail, menu);
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
}
