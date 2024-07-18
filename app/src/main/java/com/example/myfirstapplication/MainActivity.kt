package com.example.myfirstapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var todoAdapter: TodoAdapter
    private var todos: MutableList<Todo> = mutableListOf() // List to store todos

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        todoAdapter = TodoAdapter(todos)
        val rvTodoItems: RecyclerView = findViewById(R.id.rvTodoItems)
        val btnAddTodo: Button = findViewById(R.id.btnAddTodo)
        val etTodoTitle: EditText = findViewById(R.id.etTodoTitle)
        val btnDeleteDone: Button = findViewById(R.id.btnDeleteDone)

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        // Setting padding based on system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo(
                    id = todos.size,  // Generate unique ID based on list size or another mechanism
                    title = todoTitle,
                    isChecked = false // New todos are typically unchecked initially
                )
                todos.add(todo)
                todoAdapter.notifyItemInserted(todos.size - 1)
                etTodoTitle.text.clear()
            }
        }

        btnDeleteDone.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }
}
