package aw.project.noteapp.ui.theme.todo_list

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aw.project.noteapp.data.Todo
import aw.project.noteapp.data.TodoRepository
import aw.project.noteapp.util.Routes
import aw.project.noteapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {
    val todos=repository.getTodos()

    private val _uiEvents= Channel<UiEvent>()
    val uiEvent=_uiEvents.receiveAsFlow()

    private var deletedTodo: Todo? = null

    fun onEvent(event: TodolistEvent){
        when(event){
            is TodolistEvent.OnTodoClick->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO
                        + "?todoId=${event.todo.id}"))
            }
            is TodolistEvent.AddTodoClick->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodolistEvent.OnUndoDeleteClick->{
                deletedTodo?.let { todo->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
                }
            }
            is TodolistEvent.OnDeleteTodo->{
                viewModelScope.launch {
                    deletedTodo=event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Todo deleted",
                        action = "Undo"
                    ))
                }
            }
            is TodolistEvent.OnDoneChange-> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
            is TodolistEvent.OnImportantChange->{
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            Important = event.isImportant
                        ))
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }
}