package uz.gita.todoapp.presentation.screen.update.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.todoapp.data.model.CategoryEntity
import uz.gita.todoapp.data.room.entity.TaskEntity
import uz.gita.todoapp.domain.repository.AppRepository
import uz.gita.todoapp.utils.Constants
import uz.gita.todoapp.utils.getCurrentDate
import uz.gita.todoapp.utils.getCurrentTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UpdateViewModelImpl @Inject constructor(private val repository: AppRepository) : ViewModel(),
    UpdateViewModel {

    private var _titleLiveData: MutableLiveData<String> = MutableLiveData("Do math homework")
    override val titleLiveData: LiveData<String> = _titleLiveData

    private var _descriptionLiveData: MutableLiveData<String> = MutableLiveData("Do math homework")
    override val descriptionLiveData: LiveData<String> = _descriptionLiveData

    private var _dateLiveDta: MutableLiveData<String> = MutableLiveData(getCurrentDate())
    override val dateLiveData: LiveData<String> = _dateLiveDta

    private var _timeLiveData: MutableLiveData<String> = MutableLiveData(getCurrentTime())
    override val timeLiveData: LiveData<String> = _timeLiveData

    private var _categoryLiveData: MutableLiveData<CategoryEntity> =
        MutableLiveData(Constants.categoryList[0])
    override val categoryLiveData: LiveData<CategoryEntity> = _categoryLiveData

    private var _taskPriorityLiveData: MutableLiveData<Int> = MutableLiveData(1)
    override val taskPriorityLiveData: LiveData<Int> = _taskPriorityLiveData

    private var _closedLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val closeLiveData: LiveData<Unit> = _closedLiveData

    private var _editLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val editLiveData: LiveData<Unit> = _editLiveData

    private var _addTaskLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val addTaskLiveData: LiveData<Unit> = _addTaskLiveData

    private var _openHeaderDialog: MutableLiveData<Unit> = MutableLiveData()
    override val openHeaderDialog: LiveData<Unit> = _openHeaderDialog

    private var _openDateDialog: MutableLiveData<Unit> = MutableLiveData()
    override val openDateDialog: LiveData<Unit> = _openDateDialog

    private var _openTimeDialog: MutableLiveData<Unit> = MutableLiveData()
    override val openTimeDialog: LiveData<Unit> = _openTimeDialog

    private var _openCategoryDialog: MutableLiveData<Unit> = MutableLiveData()
    override val openCategoryDialog: LiveData<Unit> = _openCategoryDialog

    private var _openPriorityDialog: MutableLiveData<Unit> = MutableLiveData()
    override val openPriorityDialog: LiveData<Unit> = _openPriorityDialog

    private var _messageLiveData: MutableLiveData<String> = MutableLiveData()
    override val messageLiveData: LiveData<String> = _messageLiveData


    override fun openHeaderDialog() {
        _openHeaderDialog.postValue(Unit)
    }

    override fun openDateDialog() {
        _openDateDialog.postValue(Unit)
    }

    override fun openTimeDialog() {
        _openTimeDialog.postValue(Unit)
    }

    override fun openCategoryDialog() {
        _openCategoryDialog.postValue(Unit)
    }

    override fun openPriorityDialog() {
        _openPriorityDialog.postValue(Unit)
    }

    override fun closedClick() {
        _closedLiveData.postValue(Unit)
    }

    override fun addClicked() {
        _addTaskLiveData.postValue(Unit)
    }

    override fun updateTask(id: Int, uuid: UUID) {
        val title = titleLiveData.value!!
        val description = descriptionLiveData.value!!
        val date = dateLiveData.value!!
        val time = timeLiveData.value!!
        val category = categoryLiveData.value!!
        val priority = taskPriorityLiveData.value!!
        repository.updateTask(
            TaskEntity(
                id, title, description, date, time, 0, priority, category.id,uuid
            )
        )
        _messageLiveData.postValue("Successfully Updated")
    }

    override fun setHeader(title: String) {
        _titleLiveData.postValue(title)
    }

    override fun setDescription(description: String) {
        _descriptionLiveData.postValue(description)
    }

    override fun setDate(date: String) {
        _dateLiveDta.postValue(date)
    }

    override fun setTime(time: String) {
        _timeLiveData.postValue(time)
    }

    override fun setPriority(priority: Int) {
        _taskPriorityLiveData.postValue(priority)
    }

    override fun setCategory(categoryEntity: CategoryEntity) {
        _categoryLiveData.postValue(categoryEntity)
    }

    override fun setTask(taskEntity: TaskEntity) {
        _titleLiveData.postValue(taskEntity.title)
        _descriptionLiveData.postValue(taskEntity.description)
        _dateLiveDta.postValue(taskEntity.date)
        _timeLiveData.postValue(taskEntity.time)
        _taskPriorityLiveData.postValue(taskEntity.priority)
        _categoryLiveData.postValue(Constants.categoryList[taskEntity.categoryId - 1])
    }

    override fun deleteTask(taskEntity: TaskEntity) {
        repository.deleteTask(taskEntity)
    }
}