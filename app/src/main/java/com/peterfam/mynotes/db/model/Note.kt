package com.peterfam.mynotes.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
data class Note (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "noteTitle") val noteTitle: String,
    @ColumnInfo(name = "noteContent") val noteContent: String,
    @ColumnInfo(name = "noteDate") val noteDate: Long
)