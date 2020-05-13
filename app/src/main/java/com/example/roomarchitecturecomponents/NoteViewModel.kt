package com.example.roomarchitecturecomponents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.roomarchitecturecomponents.entities.Note

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var noteRepository: Repository = Repository(application)

    private val allNotes by lazy {
        MutableLiveData<MutableList<Note>>()
    }

    fun insertNote(note: Note) {
        noteRepository.insterNote(note)
    }

    fun updateNote(note: Note) {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note) {
        noteRepository.deleteNote(note)
    }

    fun deleteAllNotes() {
        noteRepository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<MutableList<Note>> {
        allNotes.value = noteRepository.getAllNotes().value
        return allNotes
    }
}