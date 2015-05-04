package com.shopping.swb.shopping.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.umeng.message.PushAgent;

public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
    }

}
