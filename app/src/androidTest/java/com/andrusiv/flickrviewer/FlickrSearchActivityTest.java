package com.andrusiv.flickrviewer;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.widget.EditText;

import com.andrusiv.flickrviewer.ui.search.FlickrSearchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FlickrSearchActivityTest {

    @Rule
    public ActivityTestRule<FlickrSearchActivity> searchActivityRule = new ActivityTestRule(FlickrSearchActivity.class);

    @Test
    public void imageIsOpenedInFlickrImageActivity() {
        onView(withId(R.id.action_search)).perform(click());
        onView(withClassName(endsWith(SearchView.class.getSimpleName()))).check(matches(isDisplayed()));

        onView(isAssignableFrom(EditText.class))
                .perform(typeText("cars"))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER))
                .perform(pressBack())
                .perform(pressBack());

        onView(withId(R.id.action_search))
                .perform(click());
        onView(isAssignableFrom(EditText.class))
                .perform(typeText("typing this in in order to wait while images are loading..."))
                .perform(pressBack())
                .perform(pressBack());

        onView(withText("Flickr: cars"))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.image))
                .check(matches(isDisplayed()));
    }

    @Test
    public void activityTitleIsCorrectAfterSearch() {
        onView(withId(R.id.action_search)).perform(click());
        onView(withClassName(endsWith(SearchView.class.getSimpleName()))).check(matches(isDisplayed()));

        onView(isAssignableFrom(EditText.class))
                .perform(typeText("cars"))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER))
                .perform(pressBack())
                .perform(pressBack());


        onView(withText("Flickr: cars")).check(matches(isDisplayed()));
    }

}