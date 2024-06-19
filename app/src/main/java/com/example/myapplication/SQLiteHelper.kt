package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

        companion object{

            private const val DATABASE_VERSION=1
            private const val DATABASE_NAME="course.db"
            internal const val TBL_NAME = "tbl_course"
            private const val ID="id"
            private const val NAME="name"
            private const val PRICE="price"
            private const val HOURS="hours"
            private const val DESCRIPTION="description"
        }
    // Kreira se tabela prilikom prvog kreiranja baze podataka
    override fun onCreate(db: SQLiteDatabase?) {
        val createTblCourse="CREATE TABLE $TBL_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT, $PRICE TEXT, $HOURS TEXT, $DESCRIPTION TEXT)"

        db?.execSQL(createTblCourse)
    }
    // Ažurira tabelu prilikom promene verzije baze podataka
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTblCourse="DROP TABLE IF EXISTS $TBL_NAME"
        db?.execSQL(dropTblCourse)
        onCreate(db)
    }

    fun insertCourse(crs: CourseModel) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(NAME, crs.name)
            put(PRICE, crs.price)
            put(HOURS, crs.hours)
            put(DESCRIPTION, crs.description)
        }

        db.insert(TBL_NAME, null, values)
        db.close()
    }

    // Dohvata sve kurseve iz tabele
    fun getAllCourses(): List<CourseModel> {
        val coursesList = mutableListOf<CourseModel>()
        //Dohvata sve redove iz tabele s informacijama o kursevima.
        val db = readableDatabase
        val query = "SELECT * FROM $TBL_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
            val price = cursor.getString(cursor.getColumnIndexOrThrow(PRICE))
            val hours = cursor.getString(cursor.getColumnIndexOrThrow(HOURS))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION))

            val crs = CourseModel(id, name, price, hours, description)
            coursesList.add(crs)
        }
        cursor.close()
        db.close()
        return coursesList
    }

    fun updateCourse(course: CourseModel): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(NAME, course.name)
            put(PRICE, course.price)
            put(HOURS, course.hours)
            put(DESCRIPTION, course.description)
        }

        return db.update(TBL_NAME, values, "$ID=?", arrayOf(course.id.toString()))
    }

    //Pretraga kurseva
    fun searchCourses(query: String): ArrayList<CourseModel> {
        val cursor = readableDatabase.rawQuery("SELECT * FROM $TBL_NAME WHERE $NAME LIKE '%$query%'", null)
        val resultList = ArrayList<CourseModel>()

        while (cursor.moveToNext()) {
            val courseId = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
            val courseName = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
            val coursepPrice = cursor.getString(cursor.getColumnIndexOrThrow(PRICE))
            val courseHours = cursor.getString(cursor.getColumnIndexOrThrow(HOURS))
            val courseDescription = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION))

            resultList.add(CourseModel(courseId, courseName,coursepPrice,courseHours, courseDescription ))
        }

        cursor.close()
        return resultList
    }

    fun getCourseById(courseId: Int): CourseModel {
        val db = readableDatabase
        val query = "SELECT * FROM $TBL_NAME WHERE $ID = ?"
        val cursor = db.rawQuery(query, arrayOf(courseId.toString()))

        val course: CourseModel

        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
            val price = cursor.getString(cursor.getColumnIndexOrThrow(PRICE))
            val hours = cursor.getString(cursor.getColumnIndexOrThrow(HOURS))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION))

            course = CourseModel(courseId, name, price, hours, description)
        } else {

            course = CourseModel(0, "", "", "", "")
        }

        cursor.close()
        db.close()

        return course
    }
}