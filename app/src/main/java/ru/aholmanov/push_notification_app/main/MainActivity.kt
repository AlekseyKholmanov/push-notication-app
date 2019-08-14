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

    companion object {
        const val API_KEY = "agnrmy8dy7t1h7z4w8zjix9otr6qqi"
        const val USER_KEY = "ugaadj4e8sa7yc5a79ntr4kohzi4g2"
    }
}
