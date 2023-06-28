package uz.gita.todoapp.presentation.screen.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.todoapp.R

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openMainLiveData.observe(this, openMainObserver)
        viewModel.openIntroLiveData.observe(this, openSplashObserver)

    }

    private val openMainObserver = Observer<Unit> {
        findNavController().navigate(SplashScreenDirections.actionSplashScreenToHomeScreen())

    }
    private val openSplashObserver = Observer<Unit> {
        findNavController().navigate(SplashScreenDirections.actionSplashScreenToIntroScreen())
    }
}