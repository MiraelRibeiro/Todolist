package com.example.todolist.database.data.repository

import com.example.todolist.database.data.TaskEntity
import com.example.todolist.database.data.dao.TaskDao
import com.example.todolist.database.data.toTaksEntity
import com.example.todolist.database.data.toTask
import com.example.todolist.model.Task
import com.example.todolist.ui.RegistrationViewParams

class TaskDbDataSource(private val taskDao: TaskDao) : TaskRepository {


    override fun createTask(registrationViewParams: RegistrationViewParams) {
        val taskEntity = registrationViewParams.toTaksEntity()
        taskDao.save(taskEntity)
    }

    override fun getTask(id: Int): Task {
        return taskDao.getTask(id).toTask()
    }

    override fun updateTask(taskEntity: TaskEntity) {
        taskDao.updateTask(taskEntity)
    }


}