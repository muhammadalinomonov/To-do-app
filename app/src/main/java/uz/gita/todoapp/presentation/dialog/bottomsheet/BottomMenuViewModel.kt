package uz.gita.todoapp.presentation.dialog.bottomsheet

import androidx.lifecycle.LiveData

interface BottomMenuViewModel {

    val supportUsLiveData: LiveData<Unit>

    val shareAppLiveData: LiveData<Unit>

    val aboutUsLiveData: LiveData<Unit>

    fun supportClick()

    fun shareClick()

    fun aboutClick()

}