package com.example.roomarchitecturecomponents

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.roomarchitecturecomponents.daos.NoteDao
import com.example.roomarchitecturecomponents.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Repository(
        context: Context,
        vmScope: CoroutineScope) {

    private var noteDao: NoteDao
    private var allNotes: LiveData<MutableList<Note>>

    init {

        val databaseInstance = NoteDatabase.getInstance(context, vmScope)
        noteDao = databaseInstance.getDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    fun getAllNotes(): LiveData<MutableList<Note>> {
        return allNotes
    }

}