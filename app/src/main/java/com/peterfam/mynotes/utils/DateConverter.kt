package com.peterfam.mynotes.utils

import androidx.room.TypeConverter
import java.util.*


@TypeConverter
fun toDate(dateLong: Long): Date = Date(dateLong)


@TypeConverter
fun fromDate(date: Date): Long = date.time