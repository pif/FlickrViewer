package com.andrusiv.flickrviewer.ui.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrusiv.flickrviewer.R;
import com.andrusiv.flickrviewer.api.FlickrImage;
import com.andrusiv.flickrviewer.api.FlickrProvider;

import java.util.ArrayList;
import java.util.List;

public class FlickrViewAdapter extends RecyclerView.Adapter<FlickrViewHolder> {

    private List<FlickrImage> currentItems;
    private int currentPage;
    private String currentQuery;
    private final FlickrProvider imageProvider;

    public FlickrViewAdapter(FlickrProvider imageProvider) {
        if (imageProvider == null) {
            throw new IllegalArgumentException("imageProvider should not be null");
        }

        this.imageProvider = imageProvider;
        this.currentItems = new ArrayList<>();
        this.currentPage = 0;
        this.currentQuery = "";
    }

    @Override
    public FlickrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        FlickrViewHolder fvh = new FlickrViewHolder(layoutView);
        return fvh;
    }

    @Override
    public void onBindViewHolder(FlickrViewHolder holder, int position) {
        FlickrImage image = currentItems.get(position);
        holder.updateImage(image);
    }

    @Override
    public int getItemCount() {
        return currentItems.size();
    }

    public void newSearch(String query) {
        if (query == null || "".equals(query)) {
            throw new IllegalArgumentException("query should not be empty");
        }

        currentItems.clear();
        currentPage = 0;
        currentQuery = query;

        imageProvider.getAll(currentQuery, new FlickrProvider.ProviderCallback() {
            @Override
            public void callback(int page, List<FlickrImage> images) {
                currentItems.addAll(images);
                currentPage = page;
            }
        });
        notifyDataSetChanged();

        if (currentItems.isEmpty()) {
            nextPage();
        }
    }

    public void nextPage() {
        currentPage++;
        imageProvider.findImages(currentQuery, currentPage, new FlickrProvider.ProviderCallback() {
            @Override
            public void callback(int page, List<FlickrImage> images) {
                currentItems.addAll(images);
                notifyItemRangeInserted(getItemCount(), currentItems.size() - 1);
            }
        });
    }
}