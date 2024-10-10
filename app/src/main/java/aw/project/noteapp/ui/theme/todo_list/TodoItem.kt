package aw.project.noteapp.ui.theme.todo_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import aw.project.noteapp.data.Todo

@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodolistEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), // Adds padding around the entire row
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Column to hold the ToDo title, description, and delete button
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth() // Fill the width for alignment
            ) {
                Text(
                    text = todo.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Delete icon button with event handler
                IconButton(
                    onClick = {
                        onEvent(TodolistEvent.OnDeleteTodo(todo))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }

            todo.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Column to hold the "Important" checkbox and label
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Checkbox(
                checked = todo.Important,
                onCheckedChange = { isChecked ->
                    onEvent(TodolistEvent.OnImportantChange(todo, isChecked))
                },
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
            )
            Text(
                text = "Important",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Column to hold the "Completed" checkbox and label
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = { isChecked ->
                    onEvent(TodolistEvent.OnDoneChange(todo, isChecked))
                },
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.secondary)
            )
            Text(
                text = "Completed",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
