package uz.gita.todoapp.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    @ColumnInfo(name = "is_working")
    val isWorking: Int,
    val priority: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    val uuid: UUID
) : Parcelable
