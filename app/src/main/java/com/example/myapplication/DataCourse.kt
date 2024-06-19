package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.databinding.ActivityDataCourseBinding
import com.google.android.material.navigation.NavigationView

class DataCourse : AppCompatActivity() {

    private lateinit var binding: ActivityDataCourseBinding
    private lateinit var db: SQLiteHelper


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityDataCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = SQLiteHelper(this)

        binding.btnPost.setOnClickListener {
            val name = binding.courseName.text.toString()
            val price = binding.coursePrice.text.toString()
            val hours = binding.courseDuration.text.toString()
            val description = binding.courseDescription.text.toString()

            // Provera da li su sva polja popunjena
            if (name.isEmpty() || price.isEmpty() || hours.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val crs = CourseModel(0, name, price, hours, description)
                db.insertCourse(crs)

                // Dodaj dodatne podatke u Intent
                val intent = Intent(this, CoursesPage::class.java)
                intent.putExtra("ID", crs.id)  // Prosljeđivanje ID-ja kursa
                startActivity(intent)

                finish()
                Toast.makeText(this, "Course Saved", Toast.LENGTH_SHORT).show()
            }
        }


        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)

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


        binding.root.setOnClickListener{
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