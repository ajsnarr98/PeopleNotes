package com.ajsnarr.peoplenotes.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajsnarr.peoplenotes.BaseActivity
import com.ajsnarr.peoplenotes.R
import com.ajsnarr.peoplenotes.data.Note
import com.ajsnarr.peoplenotes.data.Tag
import com.ajsnarr.peoplenotes.util.hideKeyboardFrom
import kotlinx.android.synthetic.main.activity_search.*


private const val TAG = "SearchActivity"

val SEARCH_FILTERS = listOf<String>("Limit results", "TAG", "NAME")

class SearchActivity : BaseActivity() {

    private val SEARCH_ICON = android.R.drawable.ic_menu_search
    private val X_ICON = android.R.drawable.ic_menu_close_clear_cancel

    private var isSearchBarActive = false

    private lateinit var viewModel: SearchViewModel

    private lateinit var searchBar: EditText
    private lateinit var recyclerAdapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel

        // setup search bar view to allow handling of back buttn presses within
        // keyboard
        search_bar_view.searchActivity = this

        // setup recycler view
        val recycler = recycler_view
        val recyclerManager = LinearLayoutManager(this)
        recyclerAdapter = ResultAdapter(this, RecyclerActionLister())
        recycler.apply {
            layoutManager = recyclerManager
            adapter = recyclerAdapter
        }

        // setup search bar
        searchBar = search_bar
        searchBar.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                setSearchBarActive(true)
            } else {
                setSearchBarActive(false)
            }
        }
        searchBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                setSearchBarActive(false)
                true
            } else {
                false
            }
        }

        // setup filter dropdown
        val searchFiltersDropdown = search_filters_dropdown
        searchFiltersDropdown.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SEARCH_FILTERS)
        searchFiltersDropdown.onItemSelectedListener = OnItemSelectedListener()

        // load notes
        loadDefaultNotes()
    }

    override fun onBackPressed() {
        // try to set search bar inactive
        Log.d(TAG, "onBackPressed")
        setSearchBarActive(false)
    }

    private fun loadDefaultNotes() {
        val notes = mutableListOf<Note>(
            Note(id="123", name="Devin James", tags=mutableListOf<Tag>(Tag("friend"),
                Tag("charlotesville"), Tag("JMU"), Tag("1"), Tag("1"),
                Tag("1"), Tag("1"))),
            Note(id="1233", name="John Smith"),
            Note(id="1234", name="Openheimer Shmitt")
        )
        recyclerAdapter.updateResults(notes)
    }

    private fun setSearchBarActive(isActive: Boolean) {
        // return if state of bar is not changed
        if (isActive == this.isSearchBarActive) return

        this.isSearchBarActive = isActive

        if (!isActive) {
            hideKeyboardFrom(searchBar.context, search_bar)
            searchBar.clearFocus() // un-click from search bar
        }
    }

    // for spinner (dropdown)
    private class OnItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Log.d(TAG, "onItemSelected | position: $view")
        }

    }

    // for recycler view items
    private class RecyclerActionLister() : ResultAdapter.ActionListener {

    }
}
