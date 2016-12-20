package com.sqsong.sample.ui.fragment;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.Button;

import com.sqsong.sample.R;
import com.sqsong.sample.ui.NewActivity;

import butterknife.BindView;

/**
 * Created by 青松 on 2016/12/14.
 */

public class BookFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.show_sheet_btn)
    Button mShowSheetBtn;

    @BindView(R.id.hide_sheet_btn)
    Button mHideSheetBtn;

    @BindView(R.id.modal_sheet_btn)
    Button mModalSheetBtn;

    @BindView(R.id.new_activity_btn)
    Button mNewActivityBtn;

    @BindView(R.id.bottom_sheet)
    View mBottomView;
    private BottomSheetBehavior mSheetBehavior;

    @Override
    protected int getResourceId() {
        return R.layout.fragment_book;
    }

    @Override
    protected void initEvent() {
        mShowSheetBtn.setOnClickListener(this);
        mHideSheetBtn.setOnClickListener(this);
        mModalSheetBtn.setOnClickListener(this);
        mNewActivityBtn.setOnClickListener(this);

        mSheetBehavior = BottomSheetBehavior.from(mBottomView);
        mSheetBehavior.setHideable(true);
        mSheetBehavior.setPeekHeight(300);
        mSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_sheet_btn:
                if (mSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (mSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (mSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    mSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.hide_sheet_btn:
                mSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.modal_sheet_btn:
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
                break;
            case R.id.new_activity_btn:
                startActivity(new Intent(getContext(), NewActivity.class));
                break;
        }
    }
}
