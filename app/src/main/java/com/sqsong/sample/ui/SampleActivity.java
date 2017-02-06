package com.sqsong.sample.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sqsong.sample.R;
import com.sqsong.sample.ui.fragment.VectorActivity;

import butterknife.BindView;

public class SampleActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.download_tv)
    TextView mDownloadTv;

    @BindView(R.id.jump_market_tv)
    TextView mMarketTv;

    @BindView(R.id.change_icon_btn)
    Button mChangeIconBtn;

    @BindView(R.id.notification_btn)
    Button mNotificationBtn;

    @BindView(R.id.vector_btn)
    Button mVectorBtn;

    @Override
    protected int getResourceId() {
        return R.layout.activity_sample;
    }

    @Override
    protected void initEvent() {
        setupToolbar();

        mDownloadTv.setOnClickListener(this);
        mMarketTv.setOnClickListener(this);
        mChangeIconBtn.setOnClickListener(this);
        mNotificationBtn.setOnClickListener(this);
        mVectorBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_tv:
                startActivity(new Intent(this, DownloadFileActivity.class));
                break;
            case R.id.jump_market_tv:
                jumpToMarket();
                break;
            case R.id.change_icon_btn:
                changeApplicationIcon();
                break;
            case R.id.notification_btn:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.vector_btn:
                startActivity(new Intent(this, VectorActivity.class));
                break;
        }
    }

    private void jumpToMarket() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.tencent.mm"));
        startActivity(intent);
    }

    private void changeApplicationIcon() {
        PackageManager pm = getPackageManager();
        ActivityManager am = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
        pm.setComponentEnabledSetting(
                new ComponentName(getApplicationContext(), "com.sqsong.sample.MainActivity-Default"),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(
                new ComponentName(getApplicationContext(), "com.sqsong.sample.MainActivity-Icon"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        exit();
    }
}
