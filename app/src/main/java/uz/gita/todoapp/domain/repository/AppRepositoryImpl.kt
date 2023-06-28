package uz.gita.todoapp.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.todoapp.data.room.dao.TodoDao
import uz.gita.todoapp.data.room.entity.TaskEntity
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val dao: TodoDao) : AppRepository {
    override fun updateTask(taskEntity: TaskEntity) = dao.updateTask(taskEntity)
    override fun insertTask(taskData: TaskEntity) =
        dao.insertTask(taskData)


    override fun deleteTask(taskEntity: TaskEntity) = dao.deleteTask(taskEntity)


    override fun getAllTasks(): Flow<List<TaskEntity>> = dao.getAllTasks().flowOn(Dispatchers.IO)


    override fun getCompletedTaskByDate(): Flow<List<TaskEntity>> =
        dao.getCompletedTasks().flowOn(Dispatchers.IO)


    override fun getInCompletedTaskByDate(): Flow<List<TaskEntity>> =
        dao.getNotCompletedTasks().flowOn(Dispatchers.IO)

}