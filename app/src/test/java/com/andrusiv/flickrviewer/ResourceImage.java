package com.andrusiv.flickrviewer;

import com.andrusiv.flickrviewer.api.FlickrImage;

public class ResourceImage extends FlickrImage {

    public ResourceImage(String title) {
        this.title = title;
    }

    @Override
    public String getURL() {
        return "android.resource://com.andrusiv.flickrviewer/drawable/" + title;
    }
}
