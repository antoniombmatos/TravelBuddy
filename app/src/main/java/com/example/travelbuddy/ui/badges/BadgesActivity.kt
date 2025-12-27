package com.example.travelbuddy.ui.badges

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelbuddy.R
import com.example.travelbuddy.ui.countries.CountriesActivity
import com.example.travelbuddy.ui.home.MainActivity
import com.example.travelbuddy.ui.settings.SettingsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BadgesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badges)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_badges

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_countries -> {
                    startActivity(Intent(this, CountriesActivity::class.java))
                    true
                }
                R.id.nav_badges -> true
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
