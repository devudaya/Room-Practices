package com.example.roomarchitecturecomponents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) { noteRepository.deleteNote(note) }
    }

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {noteRepository.deleteAllNotes()}
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) { noteRepository.updateNote(note) }
    }

}