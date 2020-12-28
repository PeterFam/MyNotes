package com.peterfam.mynotes.db

import androidx.room.*
import com.peterfam.mynotes.db.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE noteTitle LIKE '%' || :searchQuery || '%' ")
    fun getAllNotes(searchQuery: String): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("DELETE FROM note WHERE id = :noteID")
    suspend fun deleteNote(noteID: Int)

    @Update
    suspend fun updateNote(note: Note)
}
