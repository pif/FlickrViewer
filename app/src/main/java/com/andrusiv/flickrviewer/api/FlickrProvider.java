package com.andrusiv.flickrviewer.api;

import java.util.List;

/**
 * Should be used in the future to decouple code between different modules.
 */
public interface FlickrProvider {

    /**
     * Calls callback for each consecutive cached page.
     *
     * @param query
     * @param callback
     */
    void getAll(String query, final ProviderCallback callback);

    /**
     * Returns images for specific page and query.
     *
     * @param query
     * @param page
     * @param callback
     */
    void findImages(String query, int page, final ProviderCallback callback);

    interface ProviderCallback {
        void callback(int page, List<FlickrImage> images);
    }
}
