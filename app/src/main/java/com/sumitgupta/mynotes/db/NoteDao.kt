package com.sumitgupta.mynotes.db

import android.provider.ContactsContract
import androidx.room.*

@Dao
interface Note0Dao {

    @Insert
    suspend fun addNote(note:NoteEntity)   // we are declaring these functions as suspend bcoz to call them in kotlin coroutine scope

    @Query("SELECT * FROM note ORDER BY id DESC")  //ORDER BY id DESC , it will display the latest saved note
    suspend fun getAllNotes(): List<NoteEntity>


    @Insert
    suspend fun addMultipleNotes(vararg note:NoteEntity)

    @Update
    suspend fun updateNote(note:NoteEntity)
    // we will use the same fragment i.e. AddNoteFragment to update the node

    @Delete
    suspend fun deleteNote(note:NoteEntity)

}