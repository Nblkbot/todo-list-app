package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TodoRepository(application)
    
    val todos: LiveData<List<TodoItem>> = repository.getAllTodos()

    fun addTodo(text: String) {
        viewModelScope.launch {
            val todo = TodoItem(
                text = text,
                isCompleted = false,
                createdAt = System.currentTimeMillis()
            )
            repository.insertTodo(todo)
        }
    }

    fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: TodoItem) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }
}