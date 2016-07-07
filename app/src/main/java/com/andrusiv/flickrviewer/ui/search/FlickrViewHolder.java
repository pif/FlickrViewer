package com.andrusiv.flickrviewer.ui.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.andrusiv.flickrviewer.R;
import com.andrusiv.flickrviewer.api.FlickrImage;
import com.andrusiv.flickrviewer.ui.preview.FlickrImageActivity;
import com.squareup.picasso.Picasso;

public class FlickrViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView;
    FlickrImage image;

    public FlickrViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        imageView = (ImageView) itemView.findViewById(R.id.photo);
    }

    @Override
    public void onClick(View v) {
        if (image != null) {
            FlickrImageActivity.openImage(image, v.getContext());
        }
    }

    public void updateImage(FlickrImage image) {
        this.image = image;
        Picasso.with(imageView.getContext())
                .load(image.getURL())
                .into(imageView);
    }
}