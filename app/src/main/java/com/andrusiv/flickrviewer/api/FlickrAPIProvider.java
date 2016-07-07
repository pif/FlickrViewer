package com.andrusiv.flickrviewer.api;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Single entry point for API communication.
 * Caches successful responses from Flickr Search API in memory
 *
 * This was done with a purpose to speed up endless scrolling on configuration changes, activity recreation, etc.
 */
public class FlickrAPIProvider implements FlickrProvider {

    private FlickrService flickrAPI;

    private Map<String, Map<Integer, List<FlickrImage>>> memoryStorage = new HashMap<>();

    private FlickrAPIProvider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        flickrAPI = retrofit.create(FlickrService.class);
    }

    private static volatile FlickrAPIProvider INSTANCE = null;

    public static FlickrAPIProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (FlickrAPIProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FlickrAPIProvider();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getAll(String query, ProviderCallback callback) {
        int page = 1;
        while (isCached(query, page)) {
            cachedResult(query, page, callback);
            page++;
        }
    }

    @Override
    public void findImages(String query, int page, final ProviderCallback callback) {
        if (isCached(query, page)) {
            cachedResult(query, page, callback);
        } else {
            photoSearch(query, page, callback);
        }
    }

    private boolean isCached(String query, int page) {
        return memoryStorage.containsKey(query) && memoryStorage.get(query).containsKey(page);
    }

    private void cachedResult(String query, int page, ProviderCallback callback) {
        List<FlickrImage> images = memoryStorage.get(query).get(page);
        Log.d("INFO", "Cached result for: " + query + " " + page + " .size:" + images.size());
        if (images.size() == 0) {
            Log.e("ERR", "zero collection " + query + " " + page);
        }
        callback.callback(page, images);
    }

    private void photoSearch(final String query, final int page, final ProviderCallback callback) {
        flickrAPI.photosSearch(query, page).enqueue(new Callback<FlickrSearchResponse>() {
            @Override
            public void onResponse(Call<FlickrSearchResponse> call, Response<FlickrSearchResponse> response) {
                if (response.body() != null && FlickrSearchResponse.STATUS_OK.equals(response.body().stat)) {
                    List<FlickrImage> flickrImages = Arrays.asList(response.body().photos.photo);
                    Map<Integer, List<FlickrImage>> cache = memoryStorage.get(query);
                    if (cache == null) {
                        cache = new HashMap<>();
                        memoryStorage.put(query, cache);
                    }
                    cache.put(page, flickrImages);
                    cachedResult(query, page, callback);
                } else {
                    Log.e("ERR", String.valueOf(response.raw()));
                }
            }

            @Override
            public void onFailure(Call<FlickrSearchResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("ERR", "error: " + t);
            }
        });
    }
}
