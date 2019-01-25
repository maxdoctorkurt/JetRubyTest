package com.example.maxdo.jetrubytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.maxdo.jetrubytest.channels.CustomPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewPager.adapter = CustomPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)?.setText(R.string.all_channels)
        tabs.getTabAt(1)?.setText(R.string.fav_channels)

    }
}
