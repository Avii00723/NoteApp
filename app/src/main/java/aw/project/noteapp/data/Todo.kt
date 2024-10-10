package aw.project.noteapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title: String,
    val description: String?,
    val Important: Boolean = false,
    val isDone: Boolean = false,
    @PrimaryKey val id: Int? = null
)
