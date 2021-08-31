package com.example.todolist.database.data.dao

import androidx.room.*
import com.example.todolist.database.data.TaskEntity

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(taskEntity: TaskEntity)

    @Query("SELECT * FROM listTask WHERE id = :id")
    fun getTask(id: Int): TaskEntity

    @Delete
    fun deleteTask(taskEntity: TaskEntity)

    @Update
    fun updateTask(taskEntity: TaskEntity)
}