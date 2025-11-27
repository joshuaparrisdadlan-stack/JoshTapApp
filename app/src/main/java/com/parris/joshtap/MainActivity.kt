package com.parris.joshtap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = MainPagerAdapter(this)

        val tabs = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabs)
        val tabTitles = listOf("Play", "Library", "Cards")
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        // Initialize PlayerController early
        PlayerController.initialize(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        PlayerController.release()
    }
}
