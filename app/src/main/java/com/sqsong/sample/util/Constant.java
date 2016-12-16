package com.sqsong.sample.util;

import com.sqsong.sample.model.StoreData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 青松 on 2016/12/14.
 */

public class Constant {

    public static final String[] images = {"http://cdn.wallpapersafari.com/2/46/HtkBcG.jpg",
            "http://mobilehdwalls.com/wp-content/uploads/2016/04/blackberries-pear-plate-food-1080x1920-394x700.jpg",
            "http://www.winwallpapers.net/w1/2015/02/Apple-Tree-iPhone-6-Plus.jpg",
            "http://www.winwallpapers.net/w1/2014/05/parrot-1080x1920.jpg",
            "http://androidwallpaper.org/wp-content/uploads/2015/09/wallpaper-for-android-phones-Flowers-1080x1920-Beautiful-tulips-flowers-wallpaper-pictures.jpg",
            "https://4.bp.blogspot.com/-hILSLVvX4ZU/VmiIdhBwWbI/AAAAAAAB6V0/nbdIqMz36FU/s0/Merry_Christmas2_2015_sm.jpg",
            "http://www.samsung-wallpapers.com/uploads/allimg/130702/1-130F2224021.jpg",
            "http://www.5djpg.com/uploads/allimg/160809/1-160P91Q215.jpg",
            "http://hdwallpaperbackgrounds.net/wp-content/uploads/2015/07/Top-HD-Wallpapers-1080x1920-8.jpg",
            "http://androidwallpaper.org/wp-content/uploads/2015/09/wallpaper-for-android-phones-Flowers-1080x1920-beautiful-purple-colour-flower.jpg",
            "http://www.wowhdbackgrounds.com/wp-content/uploads/2016/11/HD-Wallpapers-1080x1920-1.jpg" };

    public static final String[] titles = {
            "Room of Plates",
            "The Sleek Boot",
            "Night Hunting",
            "Rain and Coffee",
            "Ocean View",
            "Lovers on the Roof",
            "Lessons from Delhi",
            "Mountaineers",
            "Plains in the night",
            "Dear Olivia",
            "Driving Lessons"
    };

    public static final String[] names = {
            "Ali Conners",
            "Sandra Adams",
            "Janet Perkins",
            "Peter Carlsson",
            "Trevor Hansen",
            "Britta Holt",
            "Mary Johnson",
            "Abbey Christensen",
            "David Park",
            "Sylvia Sorensen",
            "Halime Carver"
    };

    public static List<StoreData> getStoreData() {
        List<StoreData> storeDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            StoreData data = new StoreData();
            data.setImage(images[i % images.length]);
            data.setAuthor(names[i % names.length]);
            data.setTitle(titles[i % titles.length]);
            storeDataList.add(data);
        }
        return storeDataList;
    }
}
