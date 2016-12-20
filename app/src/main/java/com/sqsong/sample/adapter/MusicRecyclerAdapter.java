package com.sqsong.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sqsong.sample.R;
import com.sqsong.sample.model.MusicData;
import com.sqsong.sample.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 青松 on 2016/12/20.
 */

public class MusicRecyclerAdapter extends RecyclerView.Adapter<MusicRecyclerAdapter.MusicViewHolder> {

    public static final int TYPE_NEW = 1;
    public static final int TYPE_RECOMMEND = 2;

    private Context mContext;
    private List<MusicData> mDataList;
    private LayoutInflater mInflater;
    private int mImageWidth;
    private int mType;

    public MusicRecyclerAdapter(Context context, List<MusicData> dataList, int type) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mInflater = LayoutInflater.from(context);
        this.mImageWidth = type == TYPE_NEW ? (Util.getScreenWidth() - Util.dip2px(10) * 3) / 2 : (Util.getScreenWidth() - Util.dip2px(10) * 4) / 3;
        this.mType = type;
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_music_view, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.musicBg.getLayoutParams();
        params.height = mImageWidth;

        MusicData musicData = mDataList.get(position);
        Glide.with(mContext).load(musicData.getMusicImage()).placeholder(R.drawable.default_bg)
                .crossFade().into(holder.musicBg);
        holder.musicTitleTv.setText(musicData.getMusicTitle());
        holder.musicAuthorTv.setText(musicData.getMusicAuthor());

        if (mType == TYPE_RECOMMEND) {
            int padding = Util.dip2px(10);
            holder.content_ll.setPadding(padding, padding, padding, padding);
            holder.musicTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.musicAuthorTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.music_bg)
        ImageView musicBg;

        @BindView(R.id.content_ll)
        LinearLayout content_ll;

        @BindView(R.id.music_title_tv)
        TextView musicTitleTv;

        @BindView(R.id.music_author_tv)
        TextView musicAuthorTv;

        public MusicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
