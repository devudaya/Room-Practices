package com.example.roomarchitecturecomponents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomarchitecturecomponents.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(
        application: Application)
    : AndroidViewModel(application) {

    private val noteRepository: Repository = Repository(application, viewModelScope)
    val allNotes = noteRepository.getAllNotes()

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) { noteRepository.insertNote(note) }
    }

}