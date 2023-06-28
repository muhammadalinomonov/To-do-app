package uz.gita.todoapp.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.todoapp.presentation.screen.intro.IntroPage

class PageAdapter(f: FragmentActivity) : FragmentStateAdapter(f) {
    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
        val introFragment = IntroPage()

        introFragment.arguments = Bundle().apply {
            putInt("pos", position)
        }

        return introFragment
    }
}