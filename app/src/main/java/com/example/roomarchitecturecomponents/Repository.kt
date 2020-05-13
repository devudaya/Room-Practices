package com.example.roomarchitecturecomponents

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.roomarchitecturecomponents.daos.NoteDao
import com.example.roomarchitecturecomponents.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Repository(context: Context) {

    private var noteDao: NoteDao
    private var allNotes: LiveData<MutableList<Note>>

    init {

        val databaseInstance = NoteDatabase.getInstance(context)
        noteDao = databaseInstance.getDao()
        allNotes = noteDao.getAllNotes()
        Log.d("AllNotes", allNotes.toString() )
    }

    fun insterNote(note: Note) {

        runBlocking {
            launch(Dispatchers.IO) { noteDao.insertNote(note) }
        }
    }

    fun updateNote(note: Note) {

        runBlocking {
            launch(Dispatchers.IO) { noteDao.updateNote(note) }
        }
    }

    fun deleteNote(note: Note) {

        runBlocking {
            launch(Dispatchers.IO) { noteDao.deleteNote(note) }
        }
    }

    fun deleteAllNotes() {

        runBlocking {
            launch(Dispatchers.IO) { noteDao.deleteAllNotes() }
        }
    }

    fun getAllNotes(): LiveData<MutableList<Note>> {
        return allNotes
    }

}