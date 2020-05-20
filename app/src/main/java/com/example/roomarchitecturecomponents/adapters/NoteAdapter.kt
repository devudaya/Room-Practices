package com.example.roomarchitecturecomponents.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomarchitecturecomponents.R
import com.example.roomarchitecturecomponents.entities.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter : ListAdapter<Note, NoteAdapter.ViewHolder>(diffCallBacks) {

    companion object {

        var diffCallBacks = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title == newItem.title
                        && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }
        }
    }

    //    private var noteList = mutableListOf<Note>()
    private lateinit var onItemClick: (note: Note) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

//    override fun getItemCount(): Int {
//        return noteList.size
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

//    fun setNotes(noteList: MutableList<Note>) {
//        this.noteList = noteList
//        notifyDataSetChanged()
//    }

    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    fun setItemClickListener(itemClick: (note: Note) -> Unit) {
        this.onItemClick = itemClick
    }

    inner class ViewHolder internal constructor(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        private val textViewTitle = itemView.text_view_title!!
        private val textViewDescription = itemView.text_view_description!!
        private val textViewPriority = itemView.text_view_priority!!

        init {
            itemView.setOnClickListener { onItemClick.invoke(getItem(adapterPosition)) }
        }

        fun bindData(note: Note) {

            textViewTitle.text = note.title
            textViewDescription.text = note.description
            textViewPriority.text = note.priority.toString()
        }

    }
}