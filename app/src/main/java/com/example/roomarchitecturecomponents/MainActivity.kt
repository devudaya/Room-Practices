package com.example.roomarchitecturecomponents

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomarchitecturecomponents.adapters.NoteAdapter
import com.example.roomarchitecturecomponents.entities.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // -- Vars
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter
    private val ADD_NOTE_REQUEST = 1
    private val EDIT_NOTE_REQUEST = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_add_note.setOnClickListener {
            startActivityForResult(Intent(this, AddNoteActivity::class.java)
                    , ADD_NOTE_REQUEST)
        }

        // -- Set Recycler View
        recyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
        adapter = NoteAdapter()
        adapter.setItemClickListener {

            val editIntent = Intent(this, AddNoteActivity::class.java)
            editIntent.putExtra("id", it.id)
            editIntent.putExtra("edit_note", it)
            startActivityForResult(editIntent, EDIT_NOTE_REQUEST)
        }
        recyclerView.adapter = adapter

        // -- Swipe Delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT)
                        .show()
            }
        }).attachToRecyclerView(recyclerView)

        // -- Attach View Model
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer {
            adapter.setNotes(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_NOTE_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val note = data!!.getParcelableExtra<Note>("newNote")
                    noteViewModel.insertNote(note!!)
                    Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
                }
            }
            EDIT_NOTE_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val id = data!!.getIntExtra("id", -1)
                    if (id == -1) {
                        Toast.makeText(this, "Note can't be updated",
                                Toast.LENGTH_SHORT).show()
                        return
                    }
                    val note = data.getParcelableExtra<Note>("newNote")!!
                    note.id = id
                    noteViewModel.updateNote(note)
                }
            }
        }
    }
}
