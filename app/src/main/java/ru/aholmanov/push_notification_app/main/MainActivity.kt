package ru.aholmanov.push_notification_app.main

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.aholmanov.push_notification_app.R
import ru.aholmanov.push_notification_app.mvp.AndroidXMvpAppCompatActivity

class MainActivity : AndroidXMvpAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabs.setupWithViewPager(viewPager)
        viewPager.adapter = PushFragmentAdapter(this, supportFragmentManager)
    }
}
