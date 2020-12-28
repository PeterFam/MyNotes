package com.peterfam.mynotes.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.peterfam.mynotes.R
import com.peterfam.mynotes.db.model.Note
import com.peterfam.mynotes.utils.MyNotesAdapter
import com.peterfam.mynotes.utils.TextUndoRedo
import com.peterfam.mynotes.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_note.*
import java.util.*

class MainActivity : AppCompatActivity(), MyNotesAdapter.NoteAdapterListener,
    TextUndoRedo.TextChangeInfo {
    private lateinit var noteViewModel: NoteViewModel
    private var TUR: TextUndoRedo? = null
    private var adapter: MyNotesAdapter? = null
    private lateinit var addNoteBottomBehavior: BottomSheetBehavior<ConstraintLayout>
    private var isUpdate: Boolean = false
    private var note: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        notesObserver()

    }

    private fun init() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        adapter = MyNotesAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        addNoteBottomBehavior = BottomSheetBehavior.from(bottom_sheet_layout)
        addNoteBottomBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        fab.setOnClickListener {
            addNoteBottomBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            titleTextView.setText("")
            contentTextView.setText("")
            isUpdate = false
        }
        addNewNote()
        TUR = TextUndoRedo(titleTextView, this)
        textAction()
        undoBtn.setOnClickListener {
            TUR!!.exeUndo()
        }
    }

    private fun notesObserver() {
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.notesList.observe(
            this, Observer { notes ->
                notes.let {
                    adapter!!.setNotes(notes)
                }
            }
        )
    }

    private fun addNewNote() {
        saveBtn.setOnClickListener {
            if (!(titleTextView.text.trim().isEmpty() && contentTextView.text.trim().isEmpty())) {
                if (isUpdate) {
                    noteViewModel.updateNote(
                        Note(
                            noteTitle = titleTextView.text.toString(),
                            noteContent = contentTextView.text.toString(),
                            noteDate = Date().time,
                            id = note!!.id
                        )
                    )
                } else{
                    noteViewModel.addNote(
                        Note(
                            noteTitle = titleTextView.text.toString(),
                            noteContent = contentTextView.text.toString(),
                            noteDate = Date().time
                        )
                    )
                }
                Toast.makeText(this, "Note Saved!", Toast.LENGTH_LONG).show()
                addNoteBottomBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                titleTextView.setText("")
                contentTextView.setText("")
                hideKeyboard(parentView)
            } else {
                Toast.makeText(this, "Please fill title & note content", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDeleteNote(noteID: Int) {
        noteViewModel.deleteNote(noteID)
        Toast.makeText(this, "Note Deleted!", Toast.LENGTH_LONG).show()
    }

    override fun onItemClicked(note: Note) {
        addNoteBottomBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        titleTextView.setText(note.noteTitle)
        contentTextView.setText(note.noteContent)
        isUpdate = true
        this.note = note
    }

    override fun textAction() {
        undoBtn.isEnabled = TUR!!.canUndo()
    }
}