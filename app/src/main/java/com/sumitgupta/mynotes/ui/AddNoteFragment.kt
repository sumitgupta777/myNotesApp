package com.sumitgupta.mynotes.ui

import android.app.AlertDialog
import android.app.LauncherActivity
import android.content.pm.LauncherApps
import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract
import android.renderscript.Script
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation

import com.sumitgupta.mynotes.R
import com.sumitgupta.mynotes.db.NoteDatabase
import com.sumitgupta.mynotes.db.NoteEntity
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch

//class AddNoteFragment : Fragment() // after using coroutines in BaseFragment we will extent BaseFragment in below line instead of Fragment
class AddNoteFragment : BaseFragment() {

    private var notes: NoteEntity?=null // making this variable for receiving the note from HomeFragment adapter in below onActivityCreated function

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // receiving the notes using arguments which is passed in view.setOnClick Listener in HomeAdapter
          arguments?.let {
              notes= AddNoteFragmentArgs.fromBundle(it).noteEntity
              edTitle.setText(notes?.title)
              edNote.setText(notes?.note)
          }

        button_save.setOnClickListener{ view->
            val noteTitle=edTitle.text.toString().trim()
            val noteBody=edNote.text.toString().trim()

            if (noteTitle.isEmpty()){
                edTitle.error="title required"
                edTitle.requestFocus()
                return@setOnClickListener

            }
            if (noteBody.isEmpty()){
                edNote.error="note required"
                edNote.requestFocus()
                return@setOnClickListener

            }


            launch {
                //val note=NoteEntity(noteTitle,noteBody)

                context?.let {


                    val mNote=NoteEntity(noteTitle,noteBody)

                    if (notes==null){

                        // inserting note
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")

                    }else{
                        // not null means that note has some content and we have to update it
                        // to update the content of pre existing note we should use below code
                        mNote.id= notes!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note Updated")


                    }




                    val action = AddNoteFragmentDirections.actionSaveNote() // actionSaveNote is the id of navigation arrow  to navigate from
                    // addNote fragment to HomeFragment in nav_graph.xml
                    Navigation.findNavController(view).navigate(action) // we can not pass 'it' inside findNavController() bcoz we are in coroutine scope
                }
            }



           // val note=NoteEntity(noteTitle,noteBody)

         /*
         when we were using AsyncTask class

            //NoteDatabase(requireActivity()).getNoteDao().addNote(note)
            // We cant write above line here bcoz room does not allows us to perform database operations on main thread
            // , so we have to use it in asynchronous way and to do that we use AsyncTask class
            // NoteDatabase() using parenthesis after the database class automatically calls the invoke function of NoteDatabase class
            //NoteDatabase(requireActivity()).getNoteDao()

          //  saveNote(note)


          */


        }

    }

    private fun deleteNote(){
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure ?")
            setMessage("you cannot undo this operation")
            setPositiveButton("Yes"){_,_->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(notes!!)

                    // to get back to the home fragment after deleting the note
                    val action = AddNoteFragmentDirections.actionSaveNote() // actionSaveNote is the id of navigation arrow  to navigate from
                    // addNote fragment to HomeFragment in nav_graph.xml
                    Navigation.findNavController(requireView()).navigate(action)
                }

            }
            setNegativeButton("No"){_,_->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete-> if(notes!=null) deleteNote() else context?.toast("Cannot delete note")
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu,menu)
    }

    // we are commenting below code bcoz we are using coroutines instead of AsyncTask class , so to do this we are create a  base
    // fragment for kotlin coroutines and use it in this fragment

    /*private fun saveNote(note:NoteEntity){
        class SaveNote :AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {

                // NoteDatabase() using parenthesis after the database class automatically calls the invoke function of NoteDatabase class
                //NoteDatabase(requireActivity()).getNoteDao()
                NoteDatabase(requireActivity()).getNoteDao().addNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                Toast.makeText(activity,"Not Saved",Toast.LENGTH_SHORT).show()
            }

        }
        SaveNote().execute()
    }

     */



}
