package com.example.roomarchitecturecomponents

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.roomarchitecturecomponents.entities.Note
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        number_picker_priority.minValue = 0
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        if (intent.hasExtra("edit_note")) {
            val note = intent.getParcelableExtra<Note>("edit_note")!!
            title = note.title
            edit_text_title.setText(note.title)
            edit_text_description.setText(note.description)
            number_picker_priority.value = note.priority
        } else title = "Add Note"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {

        val title = edit_text_title.text.trim().toString()
        val description = edit_text_description.text.trim().toString()
        val priority = number_picker_priority.value

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please add description and title",
                    Toast.LENGTH_LONG).show()
            return
        }

        val newIntent = Intent()
        if (intent.hasExtra("id"))
            newIntent.putExtra("id", intent.getIntExtra("id",
                -1))
        newIntent.putExtra("newNote", Note(title, description, priority))
        setResult(Activity.RESULT_OK, newIntent)
        finish()

    }
}
