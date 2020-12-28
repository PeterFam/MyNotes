package com.peterfam.mynotes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.peterfam.mynotes.db.AppDatabase
import com.peterfam.mynotes.db.model.Note
import com.peterfam.mynotes.repository.MyNoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    var repo: MyNoteRepo? = null

    val notesList: LiveData<List<Note>>

    init {
        val noteDao = AppDatabase.getDatabase(application, viewModelScope).noteDao()
        repo = MyNoteRepo(noteDao)

        notesList = repo!!.allNotes
    }

    private fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo!!.addNote(note)
    }

    fun deleteNote(noteID: Int) = viewModelScope.launch(Dispatchers.IO) {
        repo!!.deleteNote(noteID)
    }

    private fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo!!.updateNote(note)
    }

    fun addUpdateNote(isUpdate: Boolean, note: Note, id: Int = 0) {
        if (isUpdate) {
            note.id = id
            updateNote(note)
        } else {
            addNote(note)
        }
    }
}