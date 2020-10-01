package com.sumitgupta.mynotes.adapter

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sumitgupta.mynotes.R
import com.sumitgupta.mynotes.db.NoteEntity
import com.sumitgupta.mynotes.ui.HomeFragmentDirections
import kotlinx.android.synthetic.main.note_layout.view.*

class NoteAdapter(val notes:List<NoteEntity>):RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {



    class NoteViewHolder(val view:View) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_layout,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.view.tv_title.text=notes[position].title
        holder.view.tv_note.text=notes[position].note

        holder.view.setOnClickListener{
          // we need to pass the note (existing note) that is clicked to the AddNote fragment to update this
            // and to do this we need to set some arguments in the AddNote fragment by going to the nav_graph.xml

          val action= HomeFragmentDirections.actionAddNote()
            action.noteEntity=notes[position] //note added to the argument
            // now, we have to do navigation
            Navigation.findNavController(it).navigate(action)

        }

    }
}