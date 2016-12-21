package com.sqsong.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sqsong.sample.R;
import com.sqsong.sample.model.ArticleData;
import com.sqsong.sample.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 青松 on 2016/12/21.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    private Context mContext;
    private List<ArticleData> mArticleLists;
    private LayoutInflater mInflater;

    public NewsRecyclerAdapter(Context context, List<ArticleData> articleLists) {
        this.mContext = context;
        this.mArticleLists = articleLists;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_news_view, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        ArticleData articleData = mArticleLists.get(position);
        holder.title_tv.setText(articleData.getTitle());
        holder.description_tv.setText(articleData.getDescription());
        String publishTime = Util.getNormalFormatTime(articleData.getPublishedAt());
        holder.time_tv.setText(publishTime);

        Glide.with(mContext).load(articleData.getUrlToImage()).placeholder(R.drawable.img_default).crossFade().into(holder.image_iv);
    }

    @Override
    public int getItemCount() {
        return mArticleLists.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_tv)
        TextView title_tv;

        @BindView(R.id.description_tv)
        TextView description_tv;

        @BindView(R.id.time_tv)
        TextView time_tv;

        @BindView(R.id.image_iv)
        ImageView image_iv;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
