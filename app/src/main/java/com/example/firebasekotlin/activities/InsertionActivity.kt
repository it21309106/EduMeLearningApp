package com.example.firebasekotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebasekotlin.models.QuestionModel
import com.example.firebasekotlin.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etQName : EditText
    private lateinit var etAnswer : EditText
    private lateinit var etSname : EditText
    private lateinit var btnSaveData : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etQName = findViewById(R.id.etEmpName)
        etAnswer = findViewById(R.id.etEmpAge)
        etSname = findViewById(R.id.etEmpSalary)
        btnSaveData = findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("Questions")

        btnSaveData.setOnClickListener {
            saveStudentData()
        }

    }

    private fun saveStudentData() {
        //getting values
        val QName = etQName.text.toString()
        val QAnswer = etAnswer.text.toString()
        val Sname= etSname.text.toString()

        if (QName.isEmpty()){
            etQName.error = "Please enter question"
        }
        if (QAnswer.isEmpty()){
            etAnswer.error = "Please enter answer"
        }
        if (Sname.isEmpty()){
            etSname.error = "Please enter name"
        }


        val stdId = dbRef.push().key!!

        val student = QuestionModel(stdId,QName,QAnswer,Sname)

        dbRef.child(stdId).setValue(student)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etQName.text.clear()
                etAnswer.text.clear()
                etSname.text.clear()

            }.addOnFailureListener {
                err -> Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}