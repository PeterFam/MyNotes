package com.peterfam.mynotes.repository

import androidx.lifecycle.LiveData
import com.peterfam.mynotes.db.model.Note
import com.peterfam.mynotes.db.NoteDao

class MyNoteRepo(private val noteDao: NoteDao) {


    val allNotes : LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun addNote(note: Note) = noteDao.addNote(note)

    suspend fun deleteNote(noteID: Int) = noteDao.deleteNote(noteID)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)
}