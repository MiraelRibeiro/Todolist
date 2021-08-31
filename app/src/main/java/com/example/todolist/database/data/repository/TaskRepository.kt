package com.example.todolist.database.data.repository

import com.example.todolist.database.data.TaskEntity
import com.example.todolist.model.Task
import com.example.todolist.ui.RegistrationViewParams

interface TaskRepository {

    fun createTask(registrationViewParams: RegistrationViewParams)

    fun getTask(id: Int): Task

    fun updateTask(taskEntity: TaskEntity)

}