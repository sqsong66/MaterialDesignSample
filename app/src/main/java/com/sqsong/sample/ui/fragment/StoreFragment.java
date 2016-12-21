package com.sqsong.sample.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sqsong.sample.R;
import com.sqsong.sample.adapter.StoreRecyclerAdapter;
import com.sqsong.sample.model.StoreData;
import com.sqsong.sample.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 青松 on 2016/12/14.
 */

public class StoreFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;

    private List<StoreData> mDataList = new ArrayList<>();
    private StoreRecyclerAdapter mStoreAdapter;

    @Override
    protected int getResourceId() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initEvent() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mStoreAdapter = new StoreRecyclerAdapter(getContext(), mDataList);
        mRecycler.setAdapter(mStoreAdapter);
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mListener != null) {
                    mListener.onViewScrolled(dy, null, 0);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        List<StoreData> storeData = Constant.getStoreData(getContext());
        mDataList.clear();
        mDataList.addAll(storeData);
        mStoreAdapter.notifyDataSetChanged();
    }
}
