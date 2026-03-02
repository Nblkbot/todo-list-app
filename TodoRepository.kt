package com.example.todolist

import android.app.Application
import androidx.lifecycle.LiveData

class TodoRepository(application: Application) {

    private val todoDao = TodoDatabase.getDatabase(application).todoDao()

    fun getAllTodos(): LiveData<List<TodoItem>> {
        return todoDao.getAllTodos()
    }

    suspend fun insertTodo(todo: TodoItem) {
        todoDao.insert(todo)
    }

    suspend fun updateTodo(todo: TodoItem) {
        todoDao.update(todo)
    }

    suspend fun deleteTodo(todo: TodoItem) {
        todoDao.delete(todo)
    }
}