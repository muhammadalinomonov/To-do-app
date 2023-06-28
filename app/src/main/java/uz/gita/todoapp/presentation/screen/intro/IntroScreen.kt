package uz.gita.todoapp.presentation.screen.intro

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import uz.gita.todoapp.R
import uz.gita.todoapp.data.sharedpref.MySharedPreferencesImpl
import uz.gita.todoapp.databinding.ScreenIntroBinding
import uz.gita.todoapp.presentation.adapter.PageAdapter


class IntroScreen : Fragment(R.layout.screen_intro) {

    private val adapter: PageAdapter by lazy {
        PageAdapter(requireActivity())
    }

    private lateinit var binding: ScreenIntroBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ScreenIntroBinding.bind(view)
        binding.viewPagerIntro.adapter = adapter

        binding.viewPagerIntro.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 0) {
                    binding.btnIntroBack.apply {
                        visibility = View.INVISIBLE
                        isEnabled = false
                    }
                } else {
                    binding.btnIntroBack.apply {
                        visibility = View.VISIBLE
                        isEnabled = true
                    }
                }
                if (position == 2) {
                    binding.introNextBtn.apply {
                        text = "Get Started"
                    }
                } else {
                    binding.introNextBtn.apply {
                        text = "Next"
                    }
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })

        binding.introNextBtn.setOnClickListener {
            val current = binding.viewPagerIntro.currentItem
            if (current == 2) openMain()
            else binding.viewPagerIntro.currentItem = current + 1
        }
        binding.btnIntroBack.setOnClickListener {
            val current = binding.viewPagerIntro.currentItem
            binding.viewPagerIntro.currentItem = current - 1
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openMain() {
        findNavController().navigate(IntroScreenDirections.actionIntroScreenToHomeScreen())
        MySharedPreferencesImpl.getInstance().setRegister(true)
    }

}