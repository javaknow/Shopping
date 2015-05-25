package com.shopping.swb.shopping.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.constant.AppConstants;
import com.shopping.swb.shopping.util.UMUtil;
import com.shopping.swb.shopping.util.Utility;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

public class SettingsActivity extends BaseActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private View mFeedback,mCheckUpdate,mClearCache,mAbout;
    private FeedbackAgent mFeedbackAgent;
    private PushAgent mPushAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        com.umeng.fb.util.Log.LOG = true;
        mFeedbackAgent = UMUtil.feedback(this);
        mPushAgent = UMUtil.getPushAgent(this);
        mPushAgent.enable();
        String device_token = UmengRegistrar.getRegistrationId(this);
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
        mCheckUpdate = findViewById(R.id.check_update);
        mCheckUpdate.setOnClickListener(this);
        mClearCache = findViewById(R.id.clear_cache);
        mClearCache.setOnClickListener(this);
        mAbout = findViewById(R.id.about);
        mAbout.setOnClickListener(this);
        mFeedback = findViewById(R.id.feedback);
        mFeedback.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
        int id = v.getId();
        switch (id){
            case R.id.check_update:
                updateApp();
                break;
            case R.id.clear_cache:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.clear_cache_dialog_title)
                        .setMessage(R.string.clear_cache_dialog_message)
                        .setPositiveButton(R.string.btn_ok,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this)
                                        .edit().putString(AppConstants.HISTORIES_LIST,"").apply();
                                dialog.cancel();
                                Toast.makeText(SettingsActivity.this,R.string.clear_cache_success_hint,Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.btn_cancel,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.about:
                Intent aboutIntent = new Intent(this,AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.feedback:
                //    mFeedbackAgent.startFeedbackActivity();
                Intent intent = new Intent(this,FeedbackActivity.class);
                String feedbackId = mFeedbackAgent.getDefaultConversation().getId();
                intent.putExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID,feedbackId);
                startActivity(intent);
                break;
        }
    }

    private void updateApp() {
        UmengUpdateAgent.setUpdateCheckConfig(false);
        //      UmengUpdateAgent.forceUpdate(this);
//        UmengUpdateAgent.setDownloadListener(new UmengDownloadListener(){
//
//            @Override
//            public void OnDownloadStart() {
//                Toast.makeText(SettingsActivity.this, R.string.download_start , Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void OnDownloadUpdate(int progress) {
//                Toast.makeText(SettingsActivity.this, R.string.download_progress + progress + "%" , Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void OnDownloadEnd(int result, String file) {
//                //Toast.makeText(mContext, "download result : " + result , Toast.LENGTH_SHORT).show();
//                Toast.makeText(SettingsActivity.this, R.string.download_file_path + file , Toast.LENGTH_SHORT).show();
//            }
//        });
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        UmengUpdateAgent.showUpdateDialog(SettingsActivity.this, updateInfo);
                        break;
                    case UpdateStatus.No: // has no update
                        Toast.makeText(SettingsActivity.this, R.string.check_update_hint, Toast.LENGTH_SHORT).show();
                        break;
//                    case UpdateStatus.NoneWifi: // none wifi
//                        Toast.makeText(SettingsActivity.this, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
//                        break;
                    case UpdateStatus.Timeout: // time out
                        Toast.makeText(SettingsActivity.this, R.string.connect_time_out, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        //     UmengUpdateAgent.update(this);
        UmengUpdateAgent.forceUpdate(this);
    }
}
