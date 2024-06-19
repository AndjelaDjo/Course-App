package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Profile : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        navigationView.bringToFront();
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        navigationView.setNavigationItemSelectedListener {menuItem->
            when(menuItem.itemId)
            {
                R.id.nav_home -> {
                    val intent = Intent(this, CoursesPage::class.java)
                    startActivity(intent)
                }
                R.id.nav_profile -> {
                    Toast.makeText(applicationContext, "You are already in Profile", Toast.LENGTH_SHORT).show()
                }

                R.id.nav_addCourse -> {
                    val intent = Intent(this,DataCourse::class.java)
                    startActivity(intent)
                }

                R.id.nav_logout -> {
                    // Poziva se kada korisnik izabere stavku "Logout"
                    performLogout()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setOnClickListener {
            if (!drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.openDrawer(navigationView)
            } else {
                drawerLayout.closeDrawer(navigationView)
            }
        }

    }
    private fun performLogout() {
        // Implementirajte funkcionalnost odjavljivanja ovde
        // Na primer, vraćanje na ekran za prijavljivanje (login)
        val intent = Intent(this, MainActivity_Dashboard::class.java)
        startActivity(intent)
        finish() // Zatvaranje trenutne aktivnosti
    }
}