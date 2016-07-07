package com.andrusiv.flickrviewer;

import com.andrusiv.flickrviewer.api.FlickrProvider;
import com.andrusiv.flickrviewer.ui.search.FlickrViewAdapter;

import org.junit.Test;

import static org.junit.Assert.fail;

public class AdapterTest {

    private static final String FAKE_STRING = "HELLO WORLD";

    @Test
    public void checkImageProviderIsNotNull() {
        try {
            FlickrProvider provider = null;
            FlickrViewAdapter adapter = new FlickrViewAdapter(provider);
            fail();
        } catch (IllegalArgumentException e) {
            // that's fine. we expect this exception in that case.
        }
    }

    @Test
    public void checkExceptionOnEmptyOrNullQuery() {
        TestImageProvider provider = new TestImageProvider();
        FlickrViewAdapter adapter = new FlickrViewAdapter(provider);

        try {
            adapter.newSearch(null);
        } catch (IllegalArgumentException e) {
            // that's fine. we expect this exception in that case.
        }

        try {
            adapter.newSearch("");
        } catch (IllegalArgumentException e) {
            // that's fine. we expect this exception in that case.
        }
    }
}