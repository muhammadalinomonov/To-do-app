package uz.gita.todoapp.domain.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.todoapp.data.room.entity.TaskEntity

interface AppRepository {
    fun updateTask(taskEntity: TaskEntity)
    fun insertTask(taskData: TaskEntity)
    fun deleteTask(taskEntity: TaskEntity)
    fun getAllTasks():Flow<List<TaskEntity>>
    fun getCompletedTaskByDate(): Flow<List<TaskEntity>>
    fun getInCompletedTaskByDate(): Flow<List<TaskEntity>>
}