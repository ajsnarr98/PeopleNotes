package com.github.ajsnarr98.linknotes.data.db

import com.github.ajsnarr98.linknotes.Providers
import com.github.ajsnarr98.linknotes.data.UUID
import com.github.ajsnarr98.linknotes.data.db.firestore.DBNote
import com.github.ajsnarr98.linknotes.data.db.firestore.DBTagTree
import com.github.ajsnarr98.linknotes.data.db.firestore.FirestoreNotesDAO
import com.github.ajsnarr98.linknotes.data.db.firestore.FirestoreTagsDAO
import java.lang.IllegalStateException

/**
 * A group of providers for default instances of DAO objects.
 */
object DBProviders {
    val notesDAO: DAO<DBNote>
        get() = notesDAOProvider.instance
    val tagsDAO: DAO<DBTagTree>
        get() = tagsDAOProvider.instance

    var notesDAOProvider: Providers.Provider<DAO<DBNote>>
        = Providers.UserDependantLazyProvider { userId: UUID? ->
            FirestoreNotesDAO(userId ?: throw IllegalStateException("Cannot access notes for null user"))
        }
    var tagsDAOProvider: Providers.Provider<DAO<DBTagTree>>
        = Providers.UserDependantLazyProvider { userId: UUID? ->
            FirestoreTagsDAO(userId  ?: throw IllegalStateException("Cannot access tags for null user"))
        }
}