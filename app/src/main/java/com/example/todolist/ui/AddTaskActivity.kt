package com.example.todolist.ui

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.datasource.TaskDataSource
import com.example.todolist.datasource.TaskManager
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.example.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "list_tasks")

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var taskManager: TaskManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root) // metodo para usar a referencia direta sem o findviewbyid

        taskManager = TaskManager(applicationContext)

        if (intent.hasExtra(TASK_ID)){
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.txtTitle.text = it.title
                binding.txtData.text = it.date
                binding.txtHora.text = it.hour
            }
        }
        insertListeners()
    }

    private fun insertListeners() {
        binding.txtData.editText?.setOnClickListener{
            val datePiker = MaterialDatePicker.Builder.datePicker().build()
            datePiker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.txtData.text = Date(it + offset).format()
            }
            datePiker.show(supportFragmentManager, "DATE_PIKER_TAG")
        }

        binding.txtHora.editText?.setOnClickListener{
            val timePiker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePiker.addOnPositiveButtonClickListener {
                val minute = if (timePiker.minute in 0..9) "0${timePiker.minute}" else "${timePiker.minute}"

                val hour = if (timePiker.hour in 0..9) "0${timePiker.hour}" else "${timePiker.hour}"

                binding.txtHora.text = "${hour}:${minute}"
            }
            timePiker.show(supportFragmentManager, null)
        }

        binding.btnCancel.setOnClickListener { finish() }

        binding.btnNewTak.setOnClickListener {
            val task = Task(
                title = binding.txtTitle.text,
                date = binding.txtData.text,
                hour = binding.txtHora.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }


    companion object{ const val TASK_ID = "task_id"}
}