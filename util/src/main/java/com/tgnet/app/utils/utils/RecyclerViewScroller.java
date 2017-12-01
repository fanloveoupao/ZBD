package com.tgnet.app.utils.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by fan-gk on 2017/4/27.
 */

public class RecyclerViewScroller {
    RecyclerView recycler;
    LinearLayoutManager manager;
    private boolean move = false;
    private int mIndex = 0;

    public RecyclerViewScroller(RecyclerView recycler) {
        this.recycler = recycler;
        this.manager = (LinearLayoutManager) this.recycler.getLayoutManager();
        recycler.addOnScrollListener(new RecyclerViewListener());
    }

    public void scrollTo(int position) {
        recycler.scrollToPosition(position);
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        //然后区分情况
        if (position <= firstItem) {
            recycler.scrollToPosition(position);
        } else if (position <= lastItem) {
            int top = recycler.getChildAt(position - firstItem).getTop();
            recycler.scrollBy(0, top);
        } else {
            recycler.scrollToPosition(position);
            mIndex = position;
            move = true;
        }
    }

    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = mIndex - manager.findFirstVisibleItemPosition();
                if (0 <= n && n < recycler.getChildCount()) {
                    int top = recycler.getChildAt(n).getTop();
                    recycler.scrollBy(0, top);
                }
            }
        }
    }
}
