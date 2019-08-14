package ru.aholmanov.push_notification_app.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.aholmanov.push_notification_app.presentation.push_list.SendedListFragment
import ru.aholmanov.push_notification_app.presentation.push_sender.SenderFragment

class PushFragmentAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            PAGE_PUSH_SENDER -> SenderFragment.newInstance()
            PAGE_PUSH_SENDED_LIST -> SendedListFragment.newInstance()
            else -> throw IllegalStateException("Unknown page $position")
        }
    }

    override fun getCount(): Int  = PAGES_COUNT

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            PAGE_PUSH_SENDER -> "ОТПРАВКА"
            PAGE_PUSH_SENDED_LIST -> "СПИСОК"
            else -> throw IllegalStateException("Unknown page $position")
        }
    }

    private companion object {
        const val PAGE_PUSH_SENDER = 0
        const val PAGE_PUSH_SENDED_LIST = 1
        const val PAGES_COUNT = 2
    }
}