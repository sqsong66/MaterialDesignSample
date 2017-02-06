package com.sqsong.sample.ui.fragment;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.sqsong.sample.R;
import com.sqsong.sample.ui.BaseActivity;

import butterknife.BindView;

public class VectorActivity extends BaseActivity {

    @BindView(R.id.image_iv)
    ImageView mImage;

    @Override
    protected int getResourceId() {
        return R.layout.activity_vector;
    }

    @Override
    protected void initEvent() {
        setupToolbar();

        Drawable drawable = mImage.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable)drawable).start();
        }
    }
}
