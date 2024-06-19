package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.databinding.ActivityCourseDescriptionBinding
import com.google.android.material.navigation.NavigationView



class CourseDescription : AppCompatActivity() {

    private lateinit var binding: ActivityCourseDescriptionBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var dbHelper: SQLiteHelper

    private lateinit var courseNameTextView: TextView
    private lateinit var coursePriceTextView: TextView
    private lateinit var courseHoursTextView: TextView
    private lateinit var courseDescriptionTextView: TextView

    private lateinit var btnBuyNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        dbHelper = SQLiteHelper(this)
        setSupportActionBar(toolbar)

        navigationView.bringToFront()
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        /*Navigation menu*/
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, CoursesPage::class.java)
                    startActivity(intent)
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
                R.id.nav_addCourse -> {
                    val intent = Intent(this, DataCourse::class.java)
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

        courseNameTextView = findViewById(R.id.courseNameDetail)
        coursePriceTextView = findViewById(R.id.coursePriceDetail)
        courseHoursTextView = findViewById(R.id.courseDurationDetail)
        courseDescriptionTextView = findViewById(R.id.courseDescriptDetail)

        val courseId = intent.getIntExtra("ID", 0)
        displayCourseDetails(courseId)

        btnBuyNow = findViewById(R.id.btnBuyNow)
        btnBuyNow.setOnClickListener {

                Toast.makeText(this, "You have purchased a course", Toast.LENGTH_SHORT).show()
        }

    }

    // Klikom na neki element RecycleView-a
    // odnosno nekog kursa, za njega će se prikazati detaljniji opis
    private fun displayCourseDetails(courseId: Int) {
        val db = SQLiteHelper(this)
        val course = db.getCourseById(courseId)

        courseNameTextView.text = course.name
        coursePriceTextView.text = course.price
        courseHoursTextView.text = course.hours
        courseDescriptionTextView.text = course.description
    }

    private fun performLogout() {
        // Implementirajte funkcionalnost odjavljivanja ovde
        // Na primer, vraćanje na ekran za prijavljivanje (login)
        val intent = Intent(this, MainActivity_Dashboard::class.java)
        startActivity(intent)
        finish() // Zatvaranje trenutne aktivnosti
    }
}
