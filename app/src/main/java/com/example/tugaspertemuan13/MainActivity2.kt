package com.example.tugaspertemuan13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.tugaspertemuan13.databinding.ActivityMain2Binding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val firestore = FirebaseFirestore.getInstance()
    private val classcollectionRef = firestore.collection("classes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnAdd.setOnClickListener {
                val name = edtName.text.toString()
                val major = edtMajor.text.toString()
                val gpa = edtGpa.text.toString()
                val class_of = edtClassOf.text.toString()

                val newClass = Clazz(name = name, major = major, gpa = gpa, class_of = class_of)
                addClass(newClass)
                finish()
            }
        }
    }

    private fun addClass(clazz: Clazz){
        classcollectionRef.add(clazz).addOnSuccessListener { documentReference ->
            val createdClassId = documentReference.id
            clazz.id = createdClassId

            documentReference.set(clazz).addOnFailureListener {
                Log.d("MainActivity", "Error updating class id: ", it)
            }
        }.addOnFailureListener {
            Log.d("MainActivity", "Error adding class id: ", it)
        }
    }

}