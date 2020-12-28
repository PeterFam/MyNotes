package com.peterfam.mynotes.repository

import androidx.lifecycle.asLiveData
import com.peterfam.mynotes.db.model.Note
import com.peterfam.mynotes.db.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
class MyNoteRepo(private val noteDao: NoteDao) {

    val searchQuery = MutableStateFlow("")

    private val taskFlow = searchQuery.flatMapLatest {
        noteDao.getAllNotes(it)
    }
    val allNotes  = taskFlow.asLiveData()

    suspend fun addNote(note: Note) = noteDao.addNote(note)

    suspend fun deleteNote(noteID: Int) = noteDao.deleteNote(noteID)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)
}