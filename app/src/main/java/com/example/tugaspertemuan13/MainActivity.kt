package com.example.tugaspertemuan13

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugaspertemuan13.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var clazzAdapter: ClazzAdapter
    private val firestore = FirebaseFirestore.getInstance()
    private val classcollectionRef = firestore.collection("classes")
    private val classListLiveData : MutableLiveData<List<Clazz>> by lazy {
        MutableLiveData<List<Clazz>>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnAdd.setOnClickListener {
                val intentToSecondActivity =
                    Intent(this@MainActivity,MainActivity2::class.java)
                startActivity(intentToSecondActivity)
            }


            rvClass.layoutManager = LinearLayoutManager(this@MainActivity)
            clazzAdapter = ClazzAdapter(
                onClickClass = {  },
                onDelete = { clazz ->
                    deleteClass(clazz) },
                onEdit = { clazz ->
                    val intentToThirdActivity =
                        Intent(this@MainActivity,MainActivity3::class.java)
                    intentToThirdActivity.putExtra("studentID", clazz.id)
                    intentToThirdActivity.putExtra("studentName", clazz.name)
                    intentToThirdActivity.putExtra("studentMajor", clazz.major)
                    intentToThirdActivity.putExtra("studentGpa", clazz.gpa)
                    intentToThirdActivity.putExtra("studentClassOf", clazz.class_of)

                    startActivity(intentToThirdActivity)
                }
            )
            rvClass.adapter = clazzAdapter

            observeClasses()
            getAllClasses()

        }
    }


    private fun getAllClasses(){
        observeClassesChanges();
    }

    private fun observeClassesChanges(){
        classcollectionRef.addSnapshotListener{ snapshots, error ->
            if (error != null){
                Log.d("MainActivity","Error listening for class changes:", error)
            }
            val budgets = snapshots?.toObjects(Clazz::class.java)
            if (budgets != null){
                classListLiveData.postValue(budgets)
            }
        }
    }

    private fun observeClasses(){
        classListLiveData.observe(this){
                classes ->
            clazzAdapter.submitList(classes)
        }
    }

    private fun deleteClass(clazz: Clazz){
        if (clazz.id.isEmpty()){
            Log.d("MainActivity","error delete item: class Id is empty!")
            return
        }
        classcollectionRef.document(clazz.id).delete().addOnFailureListener {
            Log.d("Main activity", "Error deleting class", it)
        }
    }

}
