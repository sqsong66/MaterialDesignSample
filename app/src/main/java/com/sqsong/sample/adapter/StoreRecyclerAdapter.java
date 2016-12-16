package com.sqsong.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sqsong.sample.R;
import com.sqsong.sample.model.StoreData;
import com.sqsong.sample.util.Util;

import java.util.List;

/**
 * Created by 青松 on 2016/12/14.
 */

public class StoreRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_BLANK = 1;
    public static final int TYPE_STORE_DATA = 2;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<StoreData> mStoreDataList;

    public StoreRecyclerAdapter(Context context, List<StoreData> dataList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mStoreDataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BLANK;
        } else {
            return TYPE_STORE_DATA;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BLANK) {
            return new BlankViewHolder(mInflater.inflate(R.layout.item_blank, parent, false));
        } else {
            return new StoreViewHolder(mInflater.inflate(R.layout.item_store_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (viewType == TYPE_BLANK) {

        } else {
            StoreViewHolder storeViewHolder = (StoreViewHolder) holder;

            StoreData storeData = mStoreDataList.get(position - 1);
            String image = storeData.getImage();
            String title = storeData.getTitle();
            String author = storeData.getAuthor();

            Glide.with(mContext).load(image).crossFade().centerCrop().placeholder(R.drawable.default_bg).into(storeViewHolder.image);
            storeViewHolder.title_tv.setText(title);
            storeViewHolder.author_tv.setText("By " + author);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) storeViewHolder.itemView.getLayoutParams();
            if (position == mStoreDataList.size()) {
                params.bottomMargin = Util.dip2px(10);
            } else {
                params.bottomMargin = 0;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mStoreDataList.size() + 1;
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView image;
        TextView title_tv;
        TextView author_tv;
        Button sample_btn;
        Button review_btn;

        public StoreViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            image = (ImageView) itemView.findViewById(R.id.image);
            title_tv = (TextView) itemView.findViewById(R.id.title_tv);
            author_tv = (TextView) itemView.findViewById(R.id.author_tv);
            sample_btn = (Button) itemView.findViewById(R.id.sample_btn);
            review_btn = (Button) itemView.findViewById(R.id.review_btn);
        }
    }

    public class BlankViewHolder extends RecyclerView.ViewHolder {

        public BlankViewHolder(View itemView) {
            super(itemView);
        }
    }
}
