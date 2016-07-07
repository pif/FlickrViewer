package com.andrusiv.flickrviewer.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;

import com.andrusiv.flickrviewer.R;
import com.andrusiv.flickrviewer.api.FlickrAPIProvider;
import com.andrusiv.flickrviewer.ui.utils.EndlessScroll;
import com.andrusiv.flickrviewer.ui.utils.RecentSearchProvider;

public class FlickrSearchActivity extends AppCompatActivity {

    private static final String KEY_LAST_SEARCH = "key_current_search";
    private static final String DEFAULT_SEARCH = "kittens";
    private static final String KEY_LAYOUT_STATE = "key_layout_state";

    private RecyclerView mRecyclerView;
    private SearchView mSearchView;

    private GridLayoutManager mLayoutManager;
    private FlickrViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_search);

        setupRecyclerView();

        handleIntent(getIntent());
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(FlickrSearchActivity.this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FlickrViewAdapter(FlickrAPIProvider.getInstance());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new EndlessScroll(mLayoutManager) {
            @Override
            public void loadMore() {
                mAdapter.nextPage();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        setupSearchView(mSearchView);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable layoutManagerState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(KEY_LAYOUT_STATE, layoutManagerState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable layoutManagerState = savedInstanceState.getParcelable(KEY_LAYOUT_STATE);
        mLayoutManager.onRestoreInstanceState(layoutManagerState);
    }

    private void setupSearchView(SearchView searchView) {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String query = null;

        if (Intent.ACTION_MAIN.equals(intent.getAction())) {
            query = getPreferences(MODE_PRIVATE).getString(KEY_LAST_SEARCH, DEFAULT_SEARCH);
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);

            // store query in history
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    RecentSearchProvider.AUTHORITY, RecentSearchProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            // persist last query between launches for convenience
            getPreferences(MODE_PRIVATE).edit().putString(KEY_LAST_SEARCH, query).commit();
        }

        if (query != null) {
            final String finalQuery = query;
            setTitle(getString(R.string.main_label, finalQuery));
            mAdapter.newSearch(finalQuery);

            if (mSearchView != null && mSearchView.isShown() && TextUtils.isEmpty(mSearchView.getQuery())) {
                mSearchView.setQuery(getIntent().getStringExtra(SearchManager.QUERY), false);
            }
        }
    }
}
