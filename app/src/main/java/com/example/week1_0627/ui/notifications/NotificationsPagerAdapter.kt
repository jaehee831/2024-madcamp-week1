package com.example.week1_0627.ui.notifications

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class NotificationsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2 // Contacts와 Images 두 개의 탭
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ContactsFragment() // Contacts 탭에 표시할 프래그먼트
            1 -> ImagesFragment() // Images 탭에 표시할 프래그먼트
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
