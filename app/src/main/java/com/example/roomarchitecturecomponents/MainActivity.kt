package com.example.roomarchitecturecomponents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this, Observer {
            Toast.makeText(this, "onChanged", Toast.LENGTH_LONG).show()
        })
    }
}
