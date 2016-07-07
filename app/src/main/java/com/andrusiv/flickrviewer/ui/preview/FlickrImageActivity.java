package com.andrusiv.flickrviewer.ui.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.andrusiv.flickrviewer.R;
import com.andrusiv.flickrviewer.api.FlickrImage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class FlickrImageActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE = "extra_image_uri";

    private ImageView mImageView;
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_image);

        mImageView = (ImageView) findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);

        FlickrImage image = (FlickrImage) getIntent().getSerializableExtra(EXTRA_IMAGE);
        if (image != null) {
            Picasso.with(this).load(image.getURL()).into(mImageView, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    mAttacher.update();
                }
            });
            setTitle(image.getTitle());
        }
    }

    /**
     * Convenience method for calling this activity.
     * @param image
     * @param from
     */
    public static void openImage(FlickrImage image, Context from) {
        Intent i = new Intent(from, FlickrImageActivity.class);
        i.putExtra(EXTRA_IMAGE, image);
        from.startActivity(i);
    }
}
