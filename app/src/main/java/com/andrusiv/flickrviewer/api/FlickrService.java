package com.andrusiv.flickrviewer.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * Retrofit Flickr Search API definition.
 *
 * <pre>
 * https://api.flickr.com/services/rest/
 *     ?method=flickr.photos.photosSearch
 *     &api_key=3e7cc266ae2b0e0d78e279ce8e361736
 *     &format=json
 *     &nojsoncallback=1
 *     &safe_search=1
 *     &text=kittens
 * </pre>
 */
public interface FlickrService {
    @GET("services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1")
    Call<FlickrSearchResponse> photosSearch(@Query("text") String text, @Query("page") int page);
}
