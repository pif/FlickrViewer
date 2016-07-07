package com.andrusiv.flickrviewer;

import com.andrusiv.flickrviewer.api.FlickrImage;
import com.andrusiv.flickrviewer.api.FlickrProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestImageProvider implements FlickrProvider {

    final List<FlickrImage> storage;

    public TestImageProvider() {
        List<FlickrImage> images = new ArrayList<>();
        images.add(new ResourceImage("kitten1"));
        images.add(new ResourceImage("kitten2"));
        images.add(new ResourceImage("kitten3"));
        images.add(new ResourceImage("kitten4"));
        images.add(new ResourceImage("kitten5"));
        images.add(new ResourceImage("kitten6"));

        storage = images;
    }

    @Override
    public void getAll(String query, ProviderCallback callback) {
        callback.callback(1, Collections.unmodifiableList(storage));
    }

    @Override
    public void findImages(String query, int page, ProviderCallback callback) {
        callback.callback(page, Collections.unmodifiableList(storage));
    }
}
