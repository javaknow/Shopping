package com.shopping.swb.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.util.UMUtil;
import com.shopping.swb.shopping.util.Utility;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.SyncListener;
import com.umeng.fb.fragment.FeedbackFragment;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener
        , SyncListener, AdapterView.OnItemSelectedListener {
    private Toolbar mToolbar;
    private FeedbackFragment mFeedbackFragment;
    private EditText mContent, mContact;
    private Button mCommit;
    private FeedbackAgent mFeedbackAgent;
    private Conversation mConversation;
    private Spinner mContactType;
    private String[] mTypes;
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
//        String conversation_id = getIntent().getStringExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID);
//        mFeedbackFragment = FeedbackFragment.newInstance(conversation_id);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.container, mFeedbackFragment)
//                .commit();
        com.umeng.fb.util.Log.LOG = true;
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
        mContent = (EditText) findViewById(R.id.content);
        mContact = (EditText) findViewById(R.id.contact);
        mCommit = (Button) findViewById(R.id.commit);
        mCommit.setOnClickListener(this);
        mFeedbackAgent = UMUtil.feedback(this);
        mContactType = (Spinner) findViewById(R.id.contact_type);
        mTypes = getResources().getStringArray(R.array.contact_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, mTypes);
        mContactType.setAdapter(adapter);
        mType = mTypes[0];
        mContactType.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //  mFeedbackFragment.refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                if (Utility.isNetworkAvailable(this)) {
                    setUserInfo();
                } else {
                    Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setUserInfo() {
        String content = mContent.getText().toString().trim();
        String contact = mContact.getText().toString().trim();
        if ("".equals(content)) {
            Toast.makeText(this, R.string.feedback_content_not_null, Toast.LENGTH_SHORT).show();
        } else {
            UserInfo userInfo = mFeedbackAgent.getUserInfo();
            if (userInfo == null) {
                userInfo = new UserInfo();
            }
            Map<String, String> map = userInfo.getContact();
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(mType, contact);
            userInfo.setContact(map);
            mFeedbackAgent.setUserInfo(userInfo);//保存联系方式
            mConversation = mFeedbackAgent.getDefaultConversation();
            mConversation.addUserReply(content);//用户反馈意见
            mConversation.sync(this);
        }
    }

    @Override
    public void onReceiveDevReply(List<Reply> replies) {

    }

    @Override
    public void onSendUserReply(List<Reply> replies) {
        mContent.setText("");
        mContact.setText("");
        Toast.makeText(this, R.string.commit_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mType = mTypes[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
