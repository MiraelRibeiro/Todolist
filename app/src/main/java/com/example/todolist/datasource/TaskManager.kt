package com.example.todolist.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskManager(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "list_tasks")
    private val mDataStore: DataStore<Preferences> = context.dataStore

    companion object{

        val ID_TASK_KEY = stringPreferencesKey("id")
        val TITLE_TASK_KEY = stringPreferencesKey("title")
        val DATE_TASK_KEY = stringPreferencesKey("date")
        val HOUR_TASK_KEY = stringPreferencesKey("hour")
    }

    suspend fun saveTask(id:String, title:String, date: String, hour:String){
        mDataStore.edit {listTasks ->
            listTasks[ID_TASK_KEY] = id
            listTasks[TITLE_TASK_KEY] = title
            listTasks[DATE_TASK_KEY] = date
            listTasks[HOUR_TASK_KEY] = hour
        }
    }

    suspend fun save(id:Int, task: Task){
        mDataStore.edit {listTasks ->
            listTasks[ID_TASK_KEY]
        }
    }

    fun readTask(id:Int, title:String, date: String, hour:String): Flow<String> {
        val ID_TASK_KEY = intPreferencesKey(id.toString())
        val TITLE_TASK_KEY = stringPreferencesKey(title)
        val DATE_TASK_KEY = stringPreferencesKey(date)
        val HOUR_TASK_KEY = stringPreferencesKey(hour)
        return mDataStore.data.map { listTasks ->
            listTasks[ID_TASK_KEY] ?: "0"
            listTasks[TITLE_TASK_KEY] ?: ""
            listTasks[DATE_TASK_KEY] ?: ""
            listTasks[HOUR_TASK_KEY] ?: ""
        }
    }
}