package com.ajsnarr.linknotes

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.ajsnarr.linknotes.data.NoteCollection
import com.ajsnarr.linknotes.data.TagCollection

open class BaseViewModel : ViewModel() {
    val notesCollection: NoteCollection = NoteCollection.instance
    val tagCollection: TagCollection = TagCollection.instance

    /**
     * All lifecycle observers known by this ViewModel.
     */
    open val lifecycleObservers: MutableCollection<LifecycleObserver>
        get() = arrayListOf(notesCollection, tagCollection)
}