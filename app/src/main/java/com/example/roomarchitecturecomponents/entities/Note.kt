package com.example.roomarchitecturecomponents.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note (
    val title: String,
    val description: String,
    val priority: Int
): Parcelable{
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}