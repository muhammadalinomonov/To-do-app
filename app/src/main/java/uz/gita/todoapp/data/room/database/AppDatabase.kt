package uz.gita.todoapp.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.todoapp.data.room.dao.TodoDao
import uz.gita.todoapp.data.room.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTasksDao(): TodoDao

}