package com.sqsong.sample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by 青松 on 2016/12/14.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInflateView();
        setContentView(getResourceId());

        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    protected void beforeInflateView() {}

    protected abstract int getResourceId();

    protected abstract void initView();

    protected abstract void initEvent();
}
