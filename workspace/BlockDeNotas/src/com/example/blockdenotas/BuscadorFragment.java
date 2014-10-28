package com.example.blockdenotas;

import android.app.ListFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;

public class BuscadorFragment extends ListFragment implements OnQueryTextListener { // , LoaderManager.LoaderCallbacks<Cursor> {

    // This is the Adapter being used to display the list's data.
    SimpleCursorAdapter mAdapter;

    // If non-null, this is the current filter the user has provided.
    String mCurFilter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data. In a real
        // application this would come from a resource.

        // We have a menu item to show in action bar.
        setHasOptionsMenu(true);

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.activity_list_item, null,
                        new String[] { "titulo", "subtitulo" },
                        new int[] { android.R.id.text1, android.R.id.text2 },
                        0);
        setListAdapter(mAdapter);

        // Prepare the loader. Either re-connect with an existing one,
        // or start a new one.
        // getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Place an action bar item for searching.
        MenuItem item = menu.add("Search");
        item.setIcon(android.R.drawable.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView sv = new SearchView(getActivity());
        sv.setOnQueryTextListener(this);
        item.setActionView(sv);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Called when the action bar search text has changed. Update
        // the search filter, and restart the loader to do a new query
        // with this filter.
        mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;
        // getLoaderManager().restartLoader(0, null, this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Don't care about this.
        return true;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behavior here.
        Log.i("FragmentComplexList", "Item clicked: " + id);
    }

    // These are the Contacts rows that we will retrieve.
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] { "", "", };

    /*
     * @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) { // This is called when a new Loader needs to be created. This // sample only has one Loader, so we don't care about the ID.
     * if (mCurFilter != null) { } else { } // Now create and return a CursorLoader that will take care of // creating a Cursor for the data being displayed. String select = ""; return new
     * CursorLoader(getActivity(), null, CONTACTS_SUMMARY_PROJECTION, select, null, "titulo COLLATE LOCALIZED ASC"); }
     * @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) { // Swap the new cursor in. (The framework will take care of closing the // old cursor once we return.)
     * mAdapter.swapCursor(data); }
     * @Override public void onLoaderReset(Loader<Cursor> loader) { // This is called when the last Cursor provided to onLoadFinished() // above is about to be closed. We need to make sure we are no
     * // longer using it. mAdapter.swapCursor(null); }
     */
}
