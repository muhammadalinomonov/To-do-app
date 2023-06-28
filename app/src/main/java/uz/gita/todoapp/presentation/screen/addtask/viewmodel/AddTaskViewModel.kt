package uz.gita.todoapp.presentation.screen.addtask.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.todoapp.data.model.CategoryEntity
import java.util.UUID

interface AddTaskViewModel {
    val titleLiveData: LiveData<String>

    val descriptionLiveData: LiveData<String>

    val dateLiveData: LiveData<String>

    val timeLiveData: LiveData<String>

    val categoryLiveData: LiveData<CategoryEntity>

    val taskPriorityLiveData: LiveData<Int>

    val closeLiveData: LiveData<Unit>

    val editLiveData: LiveData<Unit>

    val addTaskLiveData: LiveData<Unit>

    val openHeaderDialog: LiveData<Unit>

    val openDateDialog: LiveData<Unit>

    val openTimeDialog: LiveData<Unit>

    val openCategoryDialog: LiveData<Unit>

    val openPriorityDialog: LiveData<Unit>

    val messageLiveData: LiveData<String>


    fun openHeaderDialog()

    fun openDateDialog()

    fun openTimeDialog()

    fun openCategoryDialog()

    fun openPriorityDialog()

    fun closedClick()

    fun addClicked()

    fun addTask(id:UUID)

    fun setHeader(title: String)

    fun setDescription(description: String)

    fun setDate(date: String)

    fun setTime(time: String)

    fun setPriority(priority: Int)

    fun setCategory(categoryEntity: CategoryEntity)
}