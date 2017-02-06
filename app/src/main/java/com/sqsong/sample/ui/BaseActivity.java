package com.sqsong.sample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sqsong.sample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 青松 on 2016/12/14.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private List<AppCompatActivity> mActivityList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInflateView();
        setContentView(getResourceId());

        mActivityList.add(this);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    protected void setupToolbar() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolBar.setTitle(this.getClass().getSimpleName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void beforeInflateView() {}

    protected abstract int getResourceId();

    protected void initView() {}

    protected abstract void initEvent();

    protected void exit() {
        for (AppCompatActivity activity: mActivityList) {
            if (activity != null) {
                activity.finish();
            }
        }
//        Process.killProcess(Process.myPid());
    }
}
