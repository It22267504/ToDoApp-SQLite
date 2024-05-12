package com.example.todoappsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todoappsqlite.databinding.ActivityUpdateTaskBinding

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var db: TaskDatabaseHelper
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        taskId = intent.getIntExtra("task_id", -1)
        if(taskId == -1){
            finish()
            return
        }

        val task = db.getTaskById(taskId)
        binding.updateTitleEditText.setText(task.title)
        binding.updateContentEdittext.setText(task.content)

        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEdittext.text.toString()
            val updateTask = Task(taskId, newTitle, newContent)
            db.updateTask(updateTask)
            finish()
            Toast.makeText(this, "Change Saved", Toast.LENGTH_SHORT).show()
        }
    }
}