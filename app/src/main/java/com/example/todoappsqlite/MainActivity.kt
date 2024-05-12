package com.example.todoappsqlite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoappsqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TaskDatabaseHelper
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var allTasks: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)
        allTasks = db.getAllTask()
        taskAdapter = TaskAdapter(allTasks, this)

        binding.taskRecycleView.layoutManager = LinearLayoutManager(this)
        binding.taskRecycleView.adapter = taskAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        binding.searchButton.setOnClickListener {
            val searchText = binding.searchEditText.text.toString()
            searchTasks(searchText)
        }


    }

    private fun searchTasks(query: String) {
        val filteredTasks = allTasks.filter { task ->
            task.title.contains(query, true) || task.content.contains(query, true)
        }
        taskAdapter.refreshData(filteredTasks)
    }

    private fun sortTasks() {
        allTasks.sortBy { it.title }
        taskAdapter.refreshData(allTasks)
    }

    override fun onResume() {
        super.onResume()
        allTasks = db.getAllTask()
        taskAdapter.refreshData(allTasks)
    }
}
