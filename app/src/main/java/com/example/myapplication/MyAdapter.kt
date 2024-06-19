package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


// Adapter za RecyclerView koji se koristi za popunjavanje liste kurs modela
class MyAdapter(var courses: List<CourseModel>,
                private val context: Context,
                private val itemClickListener: OnItemClickListener,
                private val db: SQLiteHelper): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(course: CourseModel)
    }
    // ViewHolder koji drži referencu na prikaz elemenata jednog reda u RecyclerView-u
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.textTitle)
        var hours = view.findViewById<TextView>(R.id.textHours)
        var price = view.findViewById<TextView>(R.id.textPrice)
        var description = view.findViewById<TextView>(R.id.courseDescription)

        init {
            view.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedCourse = courses[position]
                    itemClickListener.onItemClick(clickedCourse)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_courses, parent, false)
        return MyViewHolder(view)
    }

    // Podaci koji će biti prikazanu i okviru svako RecycleView-a
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val course = courses[position]
        holder.name.text = course.name
        holder.price.text = course.price
        holder.hours.text = course.hours
        // Nema prikaza deskripcije
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun refreshData(newCourses: List<CourseModel>){
        courses=newCourses
        notifyDataSetChanged()

    }

    // Filtriranje kurs modela na osnovu unetog upita i osvežavanje podataka u adapteru
    fun search(query: String) {
        val filteredList = db.searchCourses(query)
        refreshData(filteredList)
    }



}
