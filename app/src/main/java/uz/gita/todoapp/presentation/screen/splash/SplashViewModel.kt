package uz.gita.todoapp.presentation.screen.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.todoapp.data.sharedpref.MySharedPreferences
import uz.gita.todoapp.data.sharedpref.MySharedPreferencesImpl
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val sharedPreferences: MySharedPreferences = MySharedPreferencesImpl.getInstance()

    private var _openMainLiveData: MutableLiveData<Unit> = MutableLiveData()
    val openMainLiveData: LiveData<Unit> = _openMainLiveData

    private var _openIntroLiveData: MutableLiveData<Unit> = MutableLiveData()
    val openIntroLiveData: LiveData<Unit> = _openIntroLiveData

    init {
        viewModelScope.launch {
            delay(2000)
            if (sharedPreferences.getRegister())
                _openMainLiveData.value = Unit
            else _openIntroLiveData.value = Unit
        }
    }
}