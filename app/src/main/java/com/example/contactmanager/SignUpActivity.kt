package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactmanager.databinding.ActivitySignUpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {

            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()

            val user = User(name, email, phone)

            database = FirebaseDatabase.getInstance().getReference("Users")

            if(name.isEmpty() || email.isEmpty() || phone.isEmpty()){
                if(name.isEmpty()){
                    binding.etName.error = "Please enter your name"
                }
                if(email.isEmpty()) {
                    binding.etEmail.error = "Please enter your email"
                }
                if(phone.isEmpty()) {
                    binding.etPhone.error = "Please enter your phone number"
                }
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
            else if(phone.length != 10){
                binding.etPhone.error = "Please enter a valid phone number"
            }
            else{
                database.child(phone).get().addOnSuccessListener {
                    if(it.exists()){
                        Toast.makeText(this, "User already exists\nPlease Sign In", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        database.child(phone).setValue(user).addOnSuccessListener {
                            binding.etName.text?.clear()
                            binding.etEmail.text?.clear()
                            binding.etPhone.text?.clear()
                            Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                            intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed to create account", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }
}