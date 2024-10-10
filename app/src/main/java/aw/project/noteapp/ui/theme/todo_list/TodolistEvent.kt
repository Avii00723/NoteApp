package aw.project.noteapp.ui.theme.todo_list

import aw.project.noteapp.data.Todo

sealed class TodolistEvent {
    data class OnDeleteTodo(val todo: Todo): TodolistEvent()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean): TodolistEvent()
    data class OnImportantChange(val todo: Todo, val isImportant: Boolean): TodolistEvent()
    object OnUndoDeleteClick: TodolistEvent()
    data class OnTodoClick(val todo: Todo): TodolistEvent()
    object AddTodoClick: TodolistEvent()

}