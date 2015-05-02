package com.shopping.swb.shopping.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.TextView;

import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.fragment.AllManagerFragment;
import com.shopping.swb.shopping.fragment.DigitalManagerFragment;
import com.shopping.swb.shopping.fragment.FoodManagerFragment;
import com.shopping.swb.shopping.fragment.FurnitureManagerFragment;
import com.shopping.swb.shopping.fragment.MenClothingManagerFragment;
import com.shopping.swb.shopping.fragment.OrnamentManagerFragment;
import com.shopping.swb.shopping.fragment.OthersManagerFragment;
import com.shopping.swb.shopping.fragment.ShoesManagerFragment;
import com.shopping.swb.shopping.fragment.WomenDressFragment;
import com.shopping.swb.shopping.fragment.WomenDressManagerFragment;
import com.shopping.swb.shopping.util.Utility;
import com.shopping.swb.shopping.view.NavDrawerLayout;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainActivity extends ActionBarActivity implements DrawerLayout.DrawerListener
            ,View.OnClickListener,NavDrawerLayout.NavDrawerClickListener{
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private View mCustomTitle;
    private NavDrawerLayout mNavDrawerLayout;
    private Fragment mFragmentShown;
    private UMSocialService mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavDrawer();
        setCustomTitle();
        //  setOverflowShowingAlways();
        mNavDrawerLayout = (NavDrawerLayout) findViewById(R.id.nav_drawer);
        mNavDrawerLayout.setNavDrawerClickListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content,new AllManagerFragment()).commit();
    }

    private void setNavDrawer(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if(!mDrawerLayout.isDrawerOpen(Gravity.START)){
            mDrawerLayout.openDrawer(Gravity.START);
        }
    }
    private void setCustomTitle(){
        mCustomTitle = LayoutInflater.from(this).inflate(R.layout.actionbar_custom_title_layout,null);
        ((TextView)mCustomTitle).setText(R.string.search_all);
        mCustomTitle.setOnClickListener(this);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(Gravity.CENTER);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(mCustomTitle, layoutParams);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_search:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
               // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.action_share:
                mController = Utility.share(this,getResources().getString(R.string.share_content));
                break;
            case R.id.action_settings:
                Intent settingIntent = new Intent(this,SettingsActivity.class);
                startActivity(settingIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onDrawerSlide(View view, float v) {

    }

    @Override
    public void onDrawerOpened(View view) {

    }

    @Override
    public void onDrawerClosed(View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void gotoUserCenter() {
        Intent intent = new Intent(this,UserCenterActivity.class);
        startActivity(intent);
    }

    @Override
    public void setTitle(int position,String title) {
        if(!((TextView)mCustomTitle).getText().equals(title)) {
            ((TextView) mCustomTitle).setText(title);
            handleFragment(position);
        }
        mDrawerLayout.closeDrawer(Gravity.START);
    }

    private void handleFragment(int position){
        switch (position){
            case 0:
                mFragmentShown = new AllManagerFragment();
                break;
            case 1:
                mFragmentShown = new WomenDressManagerFragment();
                break;
            case 2:
                mFragmentShown = new MenClothingManagerFragment();
                break;
            case 3:
                mFragmentShown = new FurnitureManagerFragment();
                break;
            case 4:
                mFragmentShown = new ShoesManagerFragment();
                break;
            case 5:
                mFragmentShown = new OrnamentManagerFragment();
                break;
            case 6:
                mFragmentShown = new DigitalManagerFragment();
                break;
            case 7:
                mFragmentShown = new FoodManagerFragment();
                break;
            case 8:
                mFragmentShown = new OthersManagerFragment();
                break;
        }
        if(mFragmentShown != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mFragmentShown).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
