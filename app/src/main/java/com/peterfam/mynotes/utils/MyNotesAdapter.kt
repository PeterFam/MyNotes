package com.peterfam.mynotes.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.peterfam.mynotes.db.model.Note
import com.peterfam.mynotes.R

class MyNotesAdapter internal constructor(
    context: Context,
    private val noteAdapterListener: NoteAdapterListener
) : RecyclerView.Adapter<MyNotesAdapter.MyNotesViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notesList = emptyList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNotesViewHolder {
        val itemView = inflater.inflate(R.layout.item_row, parent, false)
        return MyNotesViewHolder(itemView)
    }

    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: MyNotesViewHolder, position: Int) {
        val note = notesList[position]
        holder.noteTitleTextView.text = note.noteTitle
        holder.noteDescTextView.text = note.noteContent
        holder.itemView.setOnLongClickListener {
            noteAdapterListener.onDeleteNote(note.id)
            false
        }
    }

    internal fun setNotes(notesList: List<Note>) {
        this.notesList = notesList
        notifyDataSetChanged()
    }

    inner class MyNotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitleTextView: TextView = itemView.findViewById(R.id.title_textView)
        val noteDescTextView: TextView = itemView.findViewById(R.id.desc_textView)
    }

    interface NoteAdapterListener {
        fun onDeleteNote(noteID: Int)
    }
}