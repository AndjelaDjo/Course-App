package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity_Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityMainDashboardBinding
    private lateinit var firebaseAuth: FirebaseAuth

    //  Funkcija za prikazivanje Toast poruke
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Funkcija za prijavu korisnika putem email adrese i lozinke
    fun signInWithEmailAndPassword(email: String, password: String) {
        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                // Pokušaj prijave korisnika putem Firebase Authentication
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            // Ako je prijava uspešna, preusmeri korisnika na ekran CoursesPage
                            val intent = Intent(this@MainActivity_Dashboard, CoursesPage::class.java)
                            startActivity(intent)
                        } else {
                            // Ako nije, prikaži poruku o neispravnoj lozinci
                            showToast("Incorrect password")
                        }
                    }
            } else {
                showToast("Enter password")
            }
        } else {
            showToast("Enter email")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        // Funkcionalnost za dugme LOGIN
        binding.loginButton.setOnClickListener {
            // Prikupljanje unetih podataka (email i lozinka)
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            signInWithEmailAndPassword(email, password)

            // Preusmeravanje na ekran za registraciju
            val signUpTextView = findViewById<TextView>(R.id.textSignUp)
            signUpTextView.setOnClickListener {
                val intent = Intent(this@MainActivity_Dashboard, Registration::class.java)
                startActivity(intent)
            }
            // Preusmeravanje na ekran za registraciju
            // Postavljena dva setOnClickListener za prelazak na isti ekran
            // zbog baga na telefonu
            val bottomText = findViewById<TextView>(R.id.bottomTextView1)
            bottomText.setOnClickListener {
                val intent = Intent(this@MainActivity_Dashboard, Registration::class.java)
                startActivity(intent)
            }

        }
    }
}