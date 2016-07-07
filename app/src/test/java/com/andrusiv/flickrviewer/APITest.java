package com.andrusiv.flickrviewer;

import com.andrusiv.flickrviewer.api.FlickrImage;
import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class APITest {

    /**
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
     * @throws Exception
     */
    @Test
    public void checkGeneratedURL_isCorrect() throws Exception {
        final int farm = 8;
        final String server = "7382";
        final String id = "28144863405";
        final String secret = "47cf380f58";
        final String title = "Véritable Fauve";
        final String JSON = "{\n" +
                "    \"farm\": " + farm + ",\n" +
                "    \"id\": \"" + id + "\",\n" +
                "    \"isfamily\": 0,\n" +
                "    \"isfriend\": 0,\n" +
                "    \"ispublic\": 1,\n" +
                "    \"owner\": \"108946687@N03\",\n" +
                "    \"secret\": \"" + secret + "\",\n" +
                "    \"server\": \"" + server + "\",\n" +
                "    \"title\": \"" + title + "\"\n" +
                "}";

        final String CORRECT_URL = "http://farm" + farm + ".static.flickr.com/" + server + "/" + id + "_" + secret + ".jpg";

        Gson gson = new Gson();
        FlickrImage flickrImage = gson.fromJson(JSON, FlickrImage.class);

        assertEquals(CORRECT_URL, flickrImage.getURL());
    }
}