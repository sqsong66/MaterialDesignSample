package com.sqsong.sample;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.sqsong.sample.ui.BaseActivity;
import com.sqsong.sample.ui.fragment.BaseFragment;
import com.sqsong.sample.ui.fragment.BookFragment;
import com.sqsong.sample.ui.fragment.MusicFragment;
import com.sqsong.sample.ui.fragment.NewsFragment;
import com.sqsong.sample.ui.fragment.StoreFragment;
import com.sqsong.sample.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        BaseFragment.OnFragmentInteractionListener {

    @BindView(R.id.search_bar)
    CardView mSearchBar;

    @BindView(R.id.content)
    View mContent;

    @BindView(R.id.bottom_view)
    BottomNavigationView mBottomNavigationView;

    private boolean isInAnim;
    private boolean isOutAnim;
    private int mCurrentTab = 0;
    private int mSearchBarScrollHeight;
    private int mBottomViewScrollHeight;
    private boolean isSearchBarShown = true;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeInflateView() {

    }

    @Override
    protected void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    protected void initEvent() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        StoreFragment storeFragment = new StoreFragment();
        mFragmentList.add(storeFragment);
        mFragmentList.add(new MusicFragment());
        mFragmentList.add(new BookFragment());
        mFragmentList.add(new NewsFragment());

        getSupportFragmentManager().beginTransaction().replace(R.id.content, storeFragment).commit();

        calculateScrollHeight();
    }

    private void calculateScrollHeight() {
        mSearchBar.post(new Runnable() {
            @Override
            public void run() {
                mSearchBarScrollHeight = mSearchBar.getHeight() + Util.dip2px(10) + Util.getStatusBarHeight(getApplicationContext());
                mBottomViewScrollHeight = mBottomNavigationView.getHeight();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_store:
                showCurrentTabFragment(0);
                break;
            case R.id.menu_music:
                showCurrentTabFragment(1);
                break;
            case R.id.menu_book:
                showCurrentTabFragment(2);
                break;
            case R.id.menu_news:
                showCurrentTabFragment(3);
                break;
        }
        return true;
    }

    private void hideSearchBar() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mSearchBar, "scaleY", 1, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mSearchBar, "alpha", 1, 0);
        mSearchBar.setPivotY(0);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mSearchBar, "scaleX", 1, 0);
        set.playTogether(/*scaleX,*/alpha,  scaleY);
        set.setDuration(100).start();
        isSearchBarShown = false;
    }

    private void showSearchBar() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mSearchBar, "scaleY", 0, 1);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mSearchBar, "alpha", 0, 1);
        mSearchBar.setPivotY(0);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mSearchBar, "scaleX", 0, 1);
        set.playTogether(/*scaleX, */alpha, scaleY);
        set.setDuration(100).start();
        isSearchBarShown = true;
    }

    private void showCurrentTabFragment(int curTab) {
        if (mCurrentTab == curTab) return;

        if (curTab == 3 && isSearchBarShown) {
            hideSearchBar();
        } else {
            if (!isSearchBarShown) {
                showSearchBar();
            }
        }

        mBottomNavigationView.setBackgroundColor(getBottomBackgroundColor(curTab));
        Fragment fragment = mFragmentList.get(curTab);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragmentList.get(mCurrentTab));
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.content, fragment);
        }
        transaction.commit();
        mCurrentTab = curTab;
    }

    private int getBottomBackgroundColor(int curTab) {
        int color = R.color.colorPrimary;
        switch (curTab) {
            case 0:
                color = R.color.colorPrimary;
                break;
            case 1:
                color = R.color.colorPurple;
                break;
            case 2:
                color = R.color.colorBlueGrey;
                break;
            case 3:
                color = R.color.colorBrown;
                break;
        }
        return getResources().getColor(color);
    }

    @Override
    public void onViewScrolled(int dy, View animateView, int animateDistance) {
        Log.i("sqsong", "dy -- > " + dy);
        if (dy > 0 && Math.abs(dy) > 20 && !isOutAnim) {
            ObjectAnimator.ofFloat(mSearchBar, "translationY", 0, -mSearchBarScrollHeight).setDuration(500).start();
            ObjectAnimator.ofFloat(mBottomNavigationView, "translationY", 0, mBottomViewScrollHeight).setDuration(500).start();
            if (animateView != null && animateDistance > 0) {
                ObjectAnimator.ofFloat(animateView, "translationY", 0, -animateDistance).setDuration(500).start();
            }
            isOutAnim = true;
            isInAnim = false;
        } else if (dy < 0 && Math.abs(dy) > 20 && !isInAnim) {
            ObjectAnimator.ofFloat(mSearchBar, "translationY", -mSearchBarScrollHeight, 0).setDuration(500).start();
            ObjectAnimator.ofFloat(mBottomNavigationView, "translationY", mBottomViewScrollHeight, 0).setDuration(500).start();
            if (animateView != null && animateDistance > 0) {
                ObjectAnimator.ofFloat(animateView, "translationY", -animateDistance, 0).setDuration(500).start();
            }
            isInAnim = true;
            isOutAnim = false;
        }
    }
}
