package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var firebaseAuth: FirebaseAuth

    fun setFirebaseAuth(auth: FirebaseAuth) {
        firebaseAuth = auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance();

        binding.signUpButton.setOnClickListener {
            // Prikupljanje unetih podataka
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.passwordConfirmEditText.text.toString()

            // Provera ispravnosti unetih podataka (da li su sva polja uneta)
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            // Ukoliko je registracija uspešna
                            Toast.makeText(this@Registration, "Registration successful!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@Registration, MainActivity_Dashboard::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@Registration, it.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@Registration, "Passwords don't match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@Registration, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }


        //val signUpTextView = findViewById<TextView>(R.id.bottomTextView2)
        binding.bottomTextView2.setOnClickListener { // Otvorite RegistrationActivity kada se klikne na TextView "Sign Up"
            val intent = Intent(this@Registration, MainActivity_Dashboard::class.java)
            startActivity(intent)
        }
    }

    // Kreira korisnika putem Firebase Authentication
    fun registerUser(email: String, password: String, confirmPassword: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this@Registration, MainActivity_Dashboard::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}