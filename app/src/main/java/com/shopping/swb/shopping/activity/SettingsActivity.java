package com.shopping.swb.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.util.Utility;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

public class SettingsActivity extends BaseActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private View mFeedback;
    private FeedbackAgent mFeedbackAgent;
    private PushAgent mPushAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        com.umeng.fb.util.Log.LOG = true;
        mFeedbackAgent = Utility.feedback(this);
        mPushAgent = Utility.getPushAgent(this);
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
            case R.id.feedback:
            //    mFeedbackAgent.startFeedbackActivity();
                Intent intent = new Intent(this,FeedbackActivity.class);
                String feedbackId = mFeedbackAgent.getDefaultConversation().getId();
                intent.putExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID,feedbackId);
                startActivity(intent);
                break;
        }
    }
}
