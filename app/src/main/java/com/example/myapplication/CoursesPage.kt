package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityCoursesPageBinding
import com.google.android.material.navigation.NavigationView


class CoursesPage : AppCompatActivity(), MyAdapter.OnItemClickListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    private lateinit var binding: ActivityCoursesPageBinding
    private lateinit var db:SQLiteHelper
    private lateinit var myAdapter:MyAdapter

    private var coursesList = ArrayList<CourseModel>()
    private lateinit var adapter:MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCoursesPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicijalizacija baze podataka i adaptera za RecyclerView
        db = SQLiteHelper(this)
        myAdapter = MyAdapter(db.getAllCourses(), this, this, db)
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter=myAdapter

        // Inicijalizacija Navigation Bar-a
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Postavljanje ActionBarDrawerToggle-a za otvaranje i zatvaranje DrawerLayout-a
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
        //Search
        binding.searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    myAdapter.search(it)
                }
                return true
            }
        })
    }
    override fun onItemClick(course: CourseModel) {
        // Otvorite novu aktivnost ili fragment sa specifičnim podacima za dati kurs
        val intent = Intent(this, CourseDescription::class.java)
        intent.putExtra("ID", course.id)
        startActivity(intent)
    }
    // Osvežavanje podataka pri povratku na ekran
    override fun onResume()
    {
        super.onResume()
        myAdapter.refreshData(db.getAllCourses())
    }

    // Funkcija za logout, vraća na Login
    private fun performLogout() {
        // Implementirajte funkcionalnost odjavljivanja ovde
        // Na primer, vraćanje na ekran za prijavljivanje (login)
        val intent = Intent(this, MainActivity_Dashboard::class.java)
        startActivity(intent)
        finish() // Zatvaranje trenutne aktivnosti
    }
}
