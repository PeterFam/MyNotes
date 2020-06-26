package com.peterfam.mynotes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterfam.mynotes.db.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("DELETE FROM note WHERE id = :noteID")
    suspend fun deleteNote(noteID: Int)
}
