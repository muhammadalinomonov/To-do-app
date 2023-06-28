package uz.gita.todoapp.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.gita.todoapp.data.room.database.AppDatabase
import uz.gita.todoapp.data.sharedpref.MySharedPreferencesImpl

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MySharedPreferencesImpl.init(this)
    }

}