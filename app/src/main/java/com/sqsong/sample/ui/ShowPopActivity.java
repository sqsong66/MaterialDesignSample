package com.sqsong.sample.ui;

import android.animation.ObjectAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;

import com.sqsong.sample.R;

import butterknife.BindView;

public class ShowPopActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.pop_ll)
    LinearLayout mPopLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private boolean isPopShow = true;

    @Override
    protected int getResourceId() {
        return R.layout.activity_show_pop;
    }

    @Override
    protected void initEvent() {
        setupToolbar();

        mToolbar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar:
                animatePop();
                break;
        }
    }

    private void animatePop() {
        int height = mPopLayout.getMeasuredHeight();
        if (isPopShow) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(mPopLayout, "translationY", 0, -height);
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(1500);
            animator.start();

            isPopShow = false;
        } else {
            ObjectAnimator animator = ObjectAnimator.ofFloat(mPopLayout, "translationY", -height, 0);
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(1500);
            animator.start();
            isPopShow = true;
        }
    }
}
