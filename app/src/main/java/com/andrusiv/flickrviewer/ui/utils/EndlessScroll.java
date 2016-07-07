package com.andrusiv.flickrviewer.ui.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScroll extends RecyclerView.OnScrollListener {

    private int lastItemCount = 0;
    private int loadWhenLeft = 4;
    private boolean loading = true;

    GridLayoutManager layoutManager;

    public EndlessScroll(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        loadWhenLeft = loadWhenLeft * layoutManager.getSpanCount();
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int itemCount = layoutManager.getItemCount();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        // dataset reset
        if (itemCount < lastItemCount) {
            this.lastItemCount = itemCount;
            if (itemCount == 0) {
                this.loading = true;
            }
        }

        // finally loaded & itemCount has more items
        if (loading && (itemCount > lastItemCount)) {
            loading = false;
            lastItemCount = itemCount;
        }

        // reached visible item, after which we start loading more
        if (!loading && (lastVisible + loadWhenLeft) > itemCount) {
            loadMore();
            loading = true;
        }
    }

    public abstract void loadMore();

}