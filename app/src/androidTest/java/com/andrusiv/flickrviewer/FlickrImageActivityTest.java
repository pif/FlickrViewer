package com.andrusiv.flickrviewer;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andrusiv.flickrviewer.api.FlickrImage;
import com.andrusiv.flickrviewer.ui.preview.FlickrImageActivity;
import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FlickrImageActivityTest {

    @Rule
    public ActivityTestRule<FlickrImageActivity> searchActivityRule = new ActivityTestRule(FlickrImageActivity.class, true, false);

    @Test
    public void titleCorrespondsToFlickrImageTitle() {
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

        Gson gson = new Gson();
        FlickrImage image = gson.fromJson(JSON, FlickrImage.class);

        Intent intent = new Intent();
        intent.putExtra(FlickrImageActivity.EXTRA_IMAGE, image);

        searchActivityRule.launchActivity(intent);

        onView(withText(startsWith(title.substring(0, 5)))).check(matches(isDisplayed()));
    }
}