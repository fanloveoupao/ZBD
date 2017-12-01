package com.tgnet.app.utils.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author fan-gk
 * @date 2017/11/6.
 */
public class DoubleClickToTopUtil {
    private RecyclerViewScroller viewScroller;
    private RecyclerView mRecyclerView;
    private View mActionView;

    public DoubleClickToTopUtil(RecyclerView recycler, View actionView) {
        this.mRecyclerView = recycler;
        this.mActionView = actionView;
        viewScroller = new RecyclerViewScroller(mRecyclerView);
        initListener();
    }

    private void initListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    mActionView.setVisibility(View.GONE);
                } else if (dy < 0) {
                    mActionView.setVisibility(View.VISIBLE);
                } else if (dy > 0) {
                    mActionView.setVisibility(View.GONE);
                } else {
                    mActionView.setVisibility(View.GONE);
                }
            }
        });
        mActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewScroller.scrollTo(0);
            }
        });
    }

}
