package com.peterfam.mynotes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.peterfam.mynotes.db.AppDatabase
import com.peterfam.mynotes.db.model.Note
import com.peterfam.mynotes.repository.MyNoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private var repo: MyNoteRepo? = null

    val notesList: LiveData<List<Note>>

    init {
        val noteDao = AppDatabase.getDatabase(application, viewModelScope).noteDao()
        repo = MyNoteRepo(noteDao)
        notesList = repo!!.allNotes
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo!!.addNote(note)
    }

    fun deleteNote(noteID: Int) = viewModelScope.launch(Dispatchers.IO) {
        repo!!.deleteNote(noteID)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repo!!.updateNote(note)
    }
}