package com.example.todolist.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.model.Task
import com.example.todolist.ui.RegistrationViewParams

@Entity(tableName = "listTask")
class TaskEntity (
    @PrimaryKey val id: Int = 0,
    val title: String,
    val hour: String,
    val date: String
)

fun RegistrationViewParams.toTaksEntity(): TaskEntity{
    return with(this){
        TaskEntity(
            title = this.title,
            hour = this.hour,
            date = this.date
        )
    }
}

fun TaskEntity.toTask(): Task{
    return Task(
        title = this.title,
        hour = this.hour,
        date = this.date,
        id = this.id
    )
}
