package com.sumitgupta.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note")
data class NoteEntity(

    val title:String,
    val note:String
):Serializable{  // extends Serializable bcoz this data is passing from one fragment to another
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}
