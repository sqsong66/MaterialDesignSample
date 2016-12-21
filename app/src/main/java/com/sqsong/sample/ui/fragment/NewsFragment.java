package com.sqsong.sample.ui.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.sqsong.sample.R;
import com.sqsong.sample.adapter.NewsRecyclerAdapter;
import com.sqsong.sample.model.ArticleData;
import com.sqsong.sample.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 青松 on 2016/12/14.
 */

public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;

    private String mCurNewsCat = "cnn";
    private NewsRecyclerAdapter mNewsAdapter;
    private List<ArticleData> mArticleLists = new ArrayList<>();

    @Override
    protected int getResourceId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initEvent() {
        setHasOptionsMenu(true);

        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorGreen, R.color.colorOrange);
        mToolbar.setTitle("CNN NEWS");
        AppCompatActivity mCompatActivity = (AppCompatActivity) getActivity();
        mCompatActivity.setSupportActionBar(mToolbar);
        mCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable upArrow = ContextCompat.getDrawable(getContext(), R.mipmap.ic_menu_white_24dp);
        upArrow.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorToolbarIcon), PorterDuff.Mode.SRC_ATOP);
        mCompatActivity.getSupportActionBar().setHomeAsUpIndicator(upArrow);

        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mNewsAdapter = new NewsRecyclerAdapter(getContext(), mArticleLists);
        mRecycler.setAdapter(mNewsAdapter);

        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_toolbar_menu, menu);
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorToolbarIcon), PorterDuff.Mode.SRC_ATOP);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            mSwipeLayout.setRefreshing(true);
            loadData();
        } else if (item.getItemId() == R.id.menu_more) {
            showNewsCategoryMenu();
        } else if (item.getItemId() == android.R.id.home){
            Toast.makeText(getContext(), "Menu", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNewsCategoryMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(), mToolbar, Gravity.END);
        popupMenu.getMenuInflater().inflate(R.menu.news_category_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.news_cnn:
                        mCurNewsCat = "cnn";
                        mToolbar.setTitle("CNN NEWS");
                        break;
                    case R.id.news_abc:
                        mCurNewsCat = "abc-news-au";
                        mToolbar.setTitle("ABC NEWS");
                        break;
                    case R.id.news_bbc:
                        mCurNewsCat = "bbc-news";
                        mToolbar.setTitle("BBC NEWS");
                        break;
                    case R.id.news_cnbc:
                        mCurNewsCat = "cnbc";
                        mToolbar.setTitle("CNBC NEWS");
                        break;
                    case R.id.news_google:
                        mCurNewsCat = "google-news";
                        mToolbar.setTitle("GOOGLE NEWS");
                        break;
                }
                mArticleLists.clear();
                mNewsAdapter.notifyDataSetChanged();
                mSwipeLayout.setRefreshing(true);
                loadData();
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    protected void loadData() {
        RetrofitInstance.getInstance().getArtiacleLists(mCurNewsCat, "top")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<ArticleData>>() {
                @Override
                public void call(List<ArticleData> articleDatas) {
                    mSwipeLayout.setRefreshing(false);
                    mArticleLists.clear();
                    mArticleLists.addAll(articleDatas);
                    mNewsAdapter.notifyDataSetChanged();
                    mRecycler.smoothScrollToPosition(0);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    int code = ((HttpException) throwable).code();
                    Toast.makeText(getContext(), "code: " + code, Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
