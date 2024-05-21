package com.example.contactmanager

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactmanager.databinding.ActivityContactsBinding
import com.example.contactmanager.databinding.ActivityMainScreenBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainScreen : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    lateinit var dialog: Dialog
    lateinit var binding: ActivityMainScreenBinding

    companion object{
        const val KEY1 = "com.example.ContactManager.name"
        const val KEY2 = "com.example.ContactManager.phone"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSee.setOnClickListener {
            intent = Intent(this, contacts::class.java)
            startActivity(intent)
        }

        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val phone = binding.etPhoneNo.text.toString()

            val contact = ContactsData(name)

            dialog = Dialog(this)
            dialog.setContentView(R.layout.customresource)
            dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.bg_alert_dialogue))

            val btnOk = dialog.findViewById<Button>(R.id.btnOk)

            btnOk.setOnClickListener {
                dialog.dismiss()
            }

            database = FirebaseDatabase.getInstance().getReference("Contacts")

            if (name.isEmpty() || phone.isEmpty()){
                if(name.isEmpty()){
                    binding.etName.error = "Please enter name"
                }
                if(phone.isEmpty()){
                    binding.etPhoneNo.error = "Please enter phone number"
                }
            }
            else{
                database.child(phone).get().addOnSuccessListener {
                    if(it.exists()){
                        Toast.makeText(this, "Contact already exists", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        database.child(phone).setValue(contact).addOnSuccessListener {
                            binding.etName.text?.clear()
                            binding.etPhoneNo.text?.clear()
                            dialog.show()
                            Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}