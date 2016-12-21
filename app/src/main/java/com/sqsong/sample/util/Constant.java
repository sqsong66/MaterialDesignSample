package com.sqsong.sample.util;

import android.content.Context;

import com.sqsong.sample.R;
import com.sqsong.sample.model.StoreData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 青松 on 2016/12/14.
 */

public class Constant {

    public static final String API_KEY = "8ad83ccdafef418cae17472e75b18ea2";

    public static final int ANIMATION_DUARATION = 300;

    public static List<StoreData> getStoreData(Context context) {
        String[] images = context.getResources().getStringArray(R.array.shop_bg);
        String[] titles = context.getResources().getStringArray(R.array.shop_title);
        String[] authors = context.getResources().getStringArray(R.array.shop_author);

        List<StoreData> storeDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            StoreData data = new StoreData();
            data.setImage(images[i % images.length]);
            data.setAuthor(authors[i % authors.length]);
            data.setTitle(titles[i % titles.length]);
            storeDataList.add(data);
        }
        return storeDataList;
    }
}
