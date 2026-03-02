package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter
    private lateinit var inputField: TextInputEditText
    private lateinit var addButton: MaterialButton
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        inputField = findViewById(R.id.todoInput)
        addButton = findViewById(R.id.addButton)
        recyclerView = findViewById(R.id.recyclerView)

        // Setup ViewModel
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        // Setup RecyclerView
        adapter = TodoAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observe todo list changes
        viewModel.todos.observe(this) { todos ->
            adapter.submitList(todos)
        }

        // Add button click listener
        addButton.setOnClickListener {
            val text = inputField.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.addTodo(text)
                inputField.text?.clear()
            }
        }

        // Delete listener
        adapter.onDeleteClick = { todo ->
            viewModel.deleteTodo(todo)
        }

        // Toggle complete listener
        adapter.onToggleComplete = { todo ->
            viewModel.updateTodo(todo.copy(isCompleted = !todo.isCompleted))
        }
    }
}