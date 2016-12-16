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
import com.sqsong.sample.util.Util;

/**
 * Created by 青松 on 2016/12/16.
 */

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.SocialHolder> {

    static int[] mIcons = {R.mipmap.ic_snapchat, R.mipmap.ic_google_plus, R.mipmap.ic_facebook, R.mipmap.ic_twitter, R.mipmap.ic_instagram,
                           R.mipmap.ic_hangout, R.mipmap.ic_whatsapp, R.mipmap.ic_qq, R.mipmap.ic_weibo};
    static String[] mNames = {"Snapchat", "Google+", "Facebook", "Twitter", "Instagram", "Hangout", "WhatsApp", "QQ", "Weibo"};

    private Context mContext;
    private LayoutInflater mInflater;

    public SocialAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public SocialHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_social_share, parent, false);
        return new SocialHolder(view);
    }

    @Override
    public void onBindViewHolder(SocialHolder holder, int position) {
        int size = Util.dip2px(48);
        Glide.with(mContext).load(mIcons[position]).override(size, size).into(holder.icon);
//        holder.icon.setImageResource(mIcons[position]);
        holder.name.setText(mNames[position]);
    }

    @Override
    public int getItemCount() {
        return mIcons.length;
    }

    class SocialHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView name;

        public SocialHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

}