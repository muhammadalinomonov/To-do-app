package uz.gita.todoapp.utils

import android.content.Context
import androidx.work.WorkManager
import java.util.UUID

fun cancelWork(context: Context, workId: UUID) {
    WorkManager.getInstance(context).cancelWorkById(workId)
}