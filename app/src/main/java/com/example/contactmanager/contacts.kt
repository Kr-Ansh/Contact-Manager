package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactmanager.databinding.ActivityContactsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class contacts : AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var binding: ActivityContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }

        database = FirebaseDatabase.getInstance().getReference()
        database.child("Contacts").get().addOnSuccessListener {
            if(it.exists()){
                binding.tv.text = it.value.toString()
            }
            else{
                binding.tv.text = "No Data"
            }
        }

    }
}