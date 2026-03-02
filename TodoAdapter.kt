package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView

class TodoAdapter : ListAdapter<TodoItem, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    var onDeleteClick: ((TodoItem) -> Unit)? = null
    var onToggleComplete: ((TodoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    inner class TodoViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        private val checkbox: MaterialCheckBox = itemView.findViewById(R.id.todoCheckbox)
        private val textView: MaterialTextView = itemView.findViewById(R.id.todoText)
        private val deleteButton: MaterialButton = itemView.findViewById(R.id.deleteButton)

        fun bind(todo: TodoItem) {
            checkbox.isChecked = todo.isCompleted
            textView.text = todo.text
            
            // Apply strikethrough if completed
            if (todo.isCompleted) {
                textView.paintFlags = textView.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textView.paintFlags = textView.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            checkbox.setOnCheckedChangeListener { _, _ ->
                onToggleComplete?.invoke(todo)
            }

            deleteButton.setOnClickListener {
                onDeleteClick?.invoke(todo)
            }
        }
    }

    private class TodoDiffCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem) =
            oldItem == newItem
    }
}