package uz.gita.todoapp.presentation.screen.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.todoapp.data.room.entity.TaskEntity
import uz.gita.todoapp.data.sharedpref.MySharedPreferencesImpl
import uz.gita.todoapp.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(private val repository: AppRepository) : ViewModel(),
    HomeViewModel {

    private val sharedPreferences = MySharedPreferencesImpl.getInstance()

    override val allTasks = MutableLiveData<List<TaskEntity>>()
    override val imageLiveData = MutableLiveData<String>()
    override val openProfileLiveData = MutableLiveData<Unit>()
    override val openUpdateTaskLiveData = MutableLiveData<TaskEntity>()
    override val openAddTaskLiveData = MutableLiveData<Unit>()
    override val openEditDialog = MutableLiveData<TaskEntity>()
    override val spinnerPosition = MutableLiveData<Int>()
    override val openBottomMenu = MutableLiveData<Unit>()

    init {
        getTasks(0)
    }

    override fun getTasks(pos: Int) {

        when (pos) {
            0 -> repository.getAllTasks().onEach {
                allTasks.value = it
            }.launchIn(viewModelScope)

            1 -> repository.getCompletedTaskByDate().onEach {
                allTasks.value = it
            }.launchIn(viewModelScope)

            else -> repository.getInCompletedTaskByDate().onEach {
                allTasks.value = it
            }.launchIn(viewModelScope)
        }
    }

    override fun openProfile() {
        openProfileLiveData.value = Unit
    }

    override fun getImage() {
        imageLiveData.value = sharedPreferences.getImageUri()
    }

    override fun updateTask(taskData: TaskEntity) {
        repository.updateTask(taskData)
    }

    override fun editClicked(taskData: TaskEntity) {
        openEditDialog.value = taskData
    }

    override fun addTaskClick() {
        openAddTaskLiveData.value = Unit
    }

    override fun setPosition(pod: Int) {
        spinnerPosition.value = pod
        getTasks(pod)
    }

    override fun openUpdate(taskEntity: TaskEntity) {
        openUpdateTaskLiveData.value = taskEntity
    }

    override fun menuClick() {
        openBottomMenu.value = Unit
    }
}