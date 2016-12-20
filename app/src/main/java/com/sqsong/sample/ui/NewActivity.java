package com.sqsong.sample.ui;

import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.widget.Button;

import com.sqsong.sample.R;
import com.sqsong.sample.view.RevealColorView;

import butterknife.BindView;

public class NewActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.reveal)
    RevealColorView mRevealView;

    @BindView(R.id.btn_1)
    Button button1;

    @BindView(R.id.btn_2)
    Button button2;

    @BindView(R.id.btn_3)
    Button button3;

    @BindView(R.id.btn_4)
    Button button4;

    private View selectedView;
    private int backgroundColor;

    @Override
    protected int getResourceId() {
        return R.layout.activity_new;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {
        backgroundColor = Color.parseColor("#212121");

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int color = getColor(view);
        final Point p = getLocationInView(mRevealView, view);

        if (selectedView == view) {
            mRevealView.hide(p.x, p.y, backgroundColor, 0, 300, null);
            selectedView = null;
        } else {
            mRevealView.reveal(p.x, p.y, color, view.getHeight() / 2, 340, null);
            selectedView = view;
        }

        /*switch (view.getId()) {
            case R.id.btn_1:

                break;
            case R.id.btn_2:

                break;
            case R.id.btn_3:

                break;
            case R.id.btn_4:

                break;
        }*/
    }

    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] /*- l0[0]*/ + target.getWidth() / 2;
//        l1[1] = l1[1] /*- l0[1]*/ + target.getHeight() / 2;

        Point point = new Point(l1[0], l1[1]);
        return point;
    }

    private int getColor(View view) {
        return Color.parseColor((String) view.getTag());
    }
}
