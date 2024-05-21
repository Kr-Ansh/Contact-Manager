package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactmanager.databinding.ActivitySignInBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            val phone = binding.etPhoneNo.text.toString()
            if(phone.isNotEmpty()){
                readData(phone)
            }
            else{
                binding.etPhoneNo.error = "Please enter your phone number"
            }
        }

    }

    fun readData(phone : String){
        database = FirebaseDatabase.getInstance().getReference("Users")

        database.child(phone).get().addOnSuccessListener {
            if(it.exists()){
                intent = Intent(this, MainScreen::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "User does not exist\nPlease Sign Up", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
    }
}