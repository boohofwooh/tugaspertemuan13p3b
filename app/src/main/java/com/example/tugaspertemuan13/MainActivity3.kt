package com.example.tugaspertemuan13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.tugaspertemuan13.databinding.ActivityMain2Binding
import com.example.tugaspertemuan13.databinding.ActivityMain3Binding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executors

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private val firestore = FirebaseFirestore.getInstance()
    private val classcollectionRef = firestore.collection("classes")
    private var updateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val studentID = intent.getStringExtra("studentID")
        val studentName = intent.getStringExtra("studentName")
        val studentMajor = intent.getStringExtra("studentMajor")
        val studentGpa = intent.getStringExtra("studentGpa")
        val studentClassOf = intent.getStringExtra("studentClassOf")

        Log.d("MainActivity3", "Student ID: $studentID")
        Log.d("MainActivity3", "Student Name: $studentName")
        Log.d("MainActivity3", "Student Major: $studentMajor")
        Log.d("MainActivity3", "Student GPA: $studentGpa")
        Log.d("MainActivity3", "Student Class Of: $studentClassOf")

        with(binding) {
            if (studentID != null) {
                updateId = studentID
            }
            edtName.setText(studentName)
            edtMajor.setText(studentMajor)
            edtGpa.setText(studentGpa)
            edtClassOf.setText(studentClassOf)
            btnSave.setOnClickListener {
                updateBudget(Clazz(
                    id = updateId,
                    name = edtName.text.toString(),
                    major = edtMajor.text.toString(),
                    gpa = edtGpa.text.toString(),
                    class_of = edtClassOf.text.toString()
                ))
                finish()
            }
        }
    }

    private fun updateBudget(clazz: Clazz){
        clazz.id = updateId
        classcollectionRef.document(updateId).set(clazz).
        addOnFailureListener {
            Log.d("Main activity", "error updating class", it)
        }
    }
}