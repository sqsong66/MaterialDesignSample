package com.sqsong.sample.ui.fragment;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.sqsong.sample.R;
import com.sqsong.sample.adapter.SocialAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 青松 on 2016/12/16.
 */

public class BottomSheetFragment extends BottomSheetDialogFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet, null);
        ButterKnife.bind(this, view);
        setupRecycler();
        dialog.setContentView(view);
    }

    private void setupRecycler() {
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        SocialAdapter mSocialAdapter = new SocialAdapter(getContext());
        mRecycler.setAdapter(mSocialAdapter);
    }

}
