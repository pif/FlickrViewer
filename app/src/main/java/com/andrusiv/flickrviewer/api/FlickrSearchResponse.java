package com.andrusiv.flickrviewer.api;

/**
 * This class describes single response from Flickr Search API.
 *
 * This class corresponds to the following JSON from FlickrAPI.
 *
 * <pre>
 * {
 *     "photos": {
 *         "page": "2000",
 *         "pages": "1984",
 *         "perpage": 100,
 *         "photo": [
 *             {
 *                 "farm": 1,
 *                 "id": "19971374670",
 *                 "isfamily": 0,
 *                 "isfriend": 0,
 *                 "ispublic": 1,
 *                 "owner": "76648979@N06",
 *                 "secret": "afa97b3da7",
 *                 "server": "547",
 *                 "title": "Leonora"
 *             },
 *             {
 *                 "farm": 8,
 *                 "id": "28018420815",
 *                 "isfamily": 0,
 *                 "isfriend": 0,
 *                 "ispublic": 1,
 *                 "owner": "143428541@N05",
 *                 "secret": "9fd93ca0da",
 *                 "server": "7136",
 *                 "title": "Great view 8445"
 *             }
 *             ],
 *         "total": "198385"
 *         },
 *     "stat": "ok"
 * }
 * </pre>
 *
 * */
public class FlickrSearchResponse {

    public static final String STATUS_OK = "ok";
    public static final String STATUS_FAIL = "fail";


    public PhotosPage photos;
    public String stat;

    static class PhotosPage {
        public FlickrImage[] photo;

        PhotosPage() {
        }
    }
}
