package uz.gita.todoapp.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uz.gita.todoapp.data.room.entity.TaskEntity

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(taskEntity: TaskEntity)

    @Update
    fun updateTask(taskEntity: TaskEntity)

    @Delete
    fun deleteTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM tasks WHERE date= :date")
    fun getTasks(date: String): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>


    @Query("SELECT*FROM tasks WHERE is_working")
    fun getCompletedTasks():Flow<List<TaskEntity>>

    @Query("SELECT*FROM tasks WHERE NOT is_working")
    fun getNotCompletedTasks():Flow<List<TaskEntity>>
}