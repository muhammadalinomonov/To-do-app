package uz.gita.todoapp.presentation.screen.home.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.todoapp.data.room.entity.TaskEntity

interface HomeViewModel {
    val allTasks: LiveData<List<TaskEntity>>
    val imageLiveData: LiveData<String>

    val openProfileLiveData: LiveData<Unit>

    val openUpdateTaskLiveData: LiveData<TaskEntity>

    val openAddTaskLiveData: LiveData<Unit>

    val openEditDialog: LiveData<TaskEntity>

    val spinnerPosition: LiveData<Int>

    val openBottomMenu: LiveData<Unit>

    fun getTasks(pos: Int)

    fun openProfile()

    fun getImage()

    fun updateTask(taskData: TaskEntity)

    fun editClicked(taskData: TaskEntity)

    fun addTaskClick()


    fun setPosition(pod:Int)

    fun openUpdate(taskEntity: TaskEntity)

    fun menuClick()
}