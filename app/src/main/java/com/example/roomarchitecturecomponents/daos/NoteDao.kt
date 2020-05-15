package com.example.roomarchitecturecomponents.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomarchitecturecomponents.entities.Note

@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE from note_table")
    fun deleteAllNotes()

    @Query("SELECT * from note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<MutableList<Note>>

}