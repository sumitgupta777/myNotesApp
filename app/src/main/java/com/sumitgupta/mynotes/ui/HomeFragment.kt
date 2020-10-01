package com.sumitgupta.mynotes.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.sumitgupta.mynotes.R
import com.sumitgupta.mynotes.adapter.NoteAdapter
import com.sumitgupta.mynotes.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

//class HomeFragment : Fragment()
class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerViewNotes.setHasFixedSize(true)
        recyclerViewNotes.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL) // 1st parameter is no. of column and 2nd parameter is orientation


        // fetching the notes
        launch {
            context?.let {
                val note=NoteDatabase(it).getNoteDao().getAllNotes()
                recyclerViewNotes.adapter=NoteAdapter(note)
            }

        }

        button_add.setOnClickListener{
            // before using FragmentNameDirections in below line rebuild your project
            // so the navigation architecture automatically generated the HomeFragmentDirection class
            // here actionAddNote() is the id of the navigation arrow connecting home fragment to AddNote fragment in nav_graph.xml file
            val action = HomeFragmentDirections.actionAddNote() //actionAddNote() it is the id in the nav_graph.xml
            Navigation.findNavController(it).navigate(action)
            //above code will navigate from home fragment to add note fragment
        }
    }


}
