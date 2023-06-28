package uz.gita.todoapp.presentation.dialog.bottomsheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomMenuViewModelImpl : BottomMenuViewModel, ViewModel() {
    override val supportUsLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val shareAppLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val aboutUsLiveData: MutableLiveData<Unit> = MutableLiveData()

    override fun supportClick() {
        supportUsLiveData.postValue(Unit)
    }

    override fun shareClick() {
        shareAppLiveData.postValue(Unit)
    }

    override fun aboutClick() {
        aboutUsLiveData.postValue(Unit)
    }
}