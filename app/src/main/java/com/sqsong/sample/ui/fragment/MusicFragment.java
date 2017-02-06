package com.sqsong.sample.ui.fragment;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sqsong.sample.R;
import com.sqsong.sample.adapter.MusicRecyclerAdapter;
import com.sqsong.sample.model.MusicData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 青松 on 2016/12/14.
 */
public class MusicFragment extends BaseFragment {

    @BindView(R.id.nested_scrollview)
    NestedScrollView mNestScrollView;

    @BindView(R.id.music_new_recycler)
    RecyclerView mNewRecycler;

    @BindView(R.id.music_recommend_recycler)
    RecyclerView mRecommendRecycler;

    private MusicRecyclerAdapter mNewAdapter;
    private List<MusicData> mNewMusicData = new ArrayList<>();

    private MusicRecyclerAdapter mRecommendAdapter;
    private List<MusicData> mRecommendMusicData = new ArrayList<>();

    @Override
    protected int getResourceId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initEvent() {
        mNewRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mNewAdapter = new MusicRecyclerAdapter(getContext(), mNewMusicData, MusicRecyclerAdapter.TYPE_NEW);
        mNewRecycler.setAdapter(mNewAdapter);

        mRecommendRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecommendAdapter = new MusicRecyclerAdapter(getContext(), mRecommendMusicData, MusicRecyclerAdapter.TYPE_RECOMMEND);
        mRecommendRecycler.setAdapter(mRecommendAdapter);

        mNewRecycler.setNestedScrollingEnabled(false);
        mRecommendRecycler.setNestedScrollingEnabled(false);

        mNestScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (mListener != null) {
                    mListener.onViewScrolled(scrollY - oldScrollY, null, 0);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        // new data
        String[] newBgs = getResources().getStringArray(R.array.music_new_bg);
        String[] newTitles = getResources().getStringArray(R.array.music_new_title);
        String[] newAuthors = getResources().getStringArray(R.array.music_new_author);
        for (int i=0; i<newBgs.length; i++) {
            MusicData data = new MusicData(newBgs[i], newTitles[i], newAuthors[i]);
            mNewMusicData.add(data);
        }
        mNewAdapter.notifyDataSetChanged();

        // recommend data
        String[] recommendBgs = getResources().getStringArray(R.array.music_recommend_bg);
        String[] recommendTitles = getResources().getStringArray(R.array.music_recommend_title);
        String[] recommendAuthors = getResources().getStringArray(R.array.music_recommend_author);
        for (int i=0; i<recommendBgs.length; i++) {
            MusicData data = new MusicData(recommendBgs[i], recommendTitles[i], recommendAuthors[i]);
            mRecommendMusicData.add(data);
        }
        mRecommendAdapter.notifyDataSetChanged();
    }

}
