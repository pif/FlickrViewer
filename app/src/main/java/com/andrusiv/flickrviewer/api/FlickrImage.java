package com.andrusiv.flickrviewer.api;

import java.io.Serializable;

/**
 * This class describes single image from Flickr API.
 *
 * Serializable is a shortcut here.
 * Parcelable should be preferred.
 *
 * This class corresponds to the following JSON from FlickrAPI.
 *
 * <pre>
 * {
 *     "farm": 1,
 *     "id": "19971374670",
 *     "isfamily": 0,
 *     "isfriend": 0,
 *     "ispublic": 1,
 *     "owner": "76648979@N06",
 *     "secret": "afa97b3da7",
 *     "server": "547",
 *     "title": "Leonora"
 * }</pre>
 *
 */
public class FlickrImage implements Serializable {

    int farm;
    String id;
    String secret;
    String server;
    protected String title;

    public FlickrImage() {
    }

    public String getURL() {
        return "http://farm" + farm + ".static.flickr.com/" + server + "/" + id + "_" + secret + ".jpg";
    }

    public String getTitle() {
        return title;
    }
}