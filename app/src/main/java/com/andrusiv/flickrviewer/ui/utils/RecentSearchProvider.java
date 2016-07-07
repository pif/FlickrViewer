package com.andrusiv.flickrviewer.ui.utils;

import android.content.SearchRecentSuggestionsProvider;

public class RecentSearchProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.andrusiv.flickrviewer.ui.utils.RecentSearchProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public RecentSearchProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}