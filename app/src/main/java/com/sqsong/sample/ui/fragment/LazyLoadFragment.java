package com.sqsong.sample.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by sqsong on 16-11-27.
 *
 * Lazy load fragment, when the fragment visible to user, then load datas;
 */
public abstract class LazyLoadFragment extends Fragment {

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            this.isVisible = true;
            onVisible();
        } else {
            this.isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();
    protected void onInvisible(){}

}
