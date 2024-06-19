package com.example.myapplication

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SQLiteHelperTest {

    private lateinit var dbHelper: SQLiteHelper

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = SQLiteHelper(context)
        // Clear the database before each test
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM ${SQLiteHelper.TBL_NAME}")
        db.close()
    }

    @Test
    fun testInsertCourse() {
        // Priprema testnih podataka
        val courseModel = CourseModel(1, "Test Course", "100", "10 hours", "Test Description")
        // Poziv metode koju testiramo
        dbHelper.insertCourse(courseModel)
        // Provera da li je insert pozvan i da li se podaci nalaze u bazi
        val insertedCourses = dbHelper.getAllCourses()
        assertTrue(insertedCourses.isNotEmpty())
        val insertedCourse = insertedCourses.first()
        assertEquals(courseModel, insertedCourse)
    }
    /* @Test
    fun testInsertCourse() {
        // Priprema testnih podataka
        val courseModel = CourseModel(1, "Test Course", "100", "10 hours", "Test Description")

        // Poziv metode koju testiramo
        dbHelper.insertCourse(courseModel)

        // Provera da li je insert pozvan i da li se podaci nalaze u bazi
        val insertedCourses = dbHelper.getAllCourses()
        assertEquals(1, insertedCourses.size)

        // Provera da li su podaci tačni
        val insertedCourse = insertedCourses.first()
        assertEquals(courseModel, insertedCourse)
    }*/
    @Test
    fun testUpdateCourse() {
        // Priprema testnih podataka
        val originalCourse = CourseModel(1, "Original Course", "100", "10 hours", "Original Description")
        // Umetanje originalnog kursa
        dbHelper.insertCourse(originalCourse)
        // Promena podataka u kursu
        val updatedCourse = CourseModel(1, "Updated Course", "150", "15 hours", "Updated Description")
        // Poziv metode koju testiramo
        val updatedRowCount = dbHelper.updateCourse(updatedCourse)
        // Provera da li je update izvršen
        assertEquals(1, updatedRowCount)
        // Dohvatanje ažuriranog kursa
        val retrievedCourse = dbHelper.getCourseById(1)
        // Provera da li su podaci ažurirani
        assertEquals(updatedCourse, retrievedCourse)
    }

    @Test
    fun testSearchCourses() {
        // Priprema testnih podataka
        val courseModel1 = CourseModel(1, "Android Development", "150", "15 hours", "Learn Android development")
        val courseModel2 = CourseModel(2, "Kotlin Programming", "100", "10 hours", "Introduction to Kotlin")
        // Umetanje testnih podataka
        dbHelper.insertCourse(courseModel1)
        dbHelper.insertCourse(courseModel2)
        // Poziv metode koju testiramo
        val searchResults = dbHelper.searchCourses("Android")
        // Provera da li su pronađeni odgovarajući kursevi
        assertEquals(1, searchResults.size)
        assertTrue(searchResults.contains(courseModel1))
    }

}
