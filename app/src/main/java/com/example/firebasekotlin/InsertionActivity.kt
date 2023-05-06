package com.example.firebasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etQuestionName : EditText
    private lateinit var etQAnswer : EditText
    private lateinit var etStudent : EditText
    private lateinit var btnSaveData : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etQuestionName = findViewById(R.id.etEmpName)
        etQAnswer = findViewById(R.id.etEmpAge)
        etStudent = findViewById(R.id.etEmpSalary)
        btnSaveData = findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("Questions")

        btnSaveData.setOnClickListener {
            saveStudentData()
        }

    }

    private fun saveStudentData() {
        //getting values
        val stQuestion = etQuestionName.text.toString()
        val stAnswer = etQAnswer.text.toString()
        val stStudent= etStudent.text.toString()

        if (stQuestion.isEmpty()){
            etQuestionName.error = "Please enter question"
        }
        if (stAnswer.isEmpty()){
            etQAnswer.error = "Please enter answer"
        }
        if (stStudent.isEmpty()){
            etStudent.error = "Please enter name"
        }


        val Id = dbRef.push().key!!

        val question = QuestionModel(Id,stQuestion,stAnswer,stStudent)

        dbRef.child(Id).setValue(question)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etQuestionName.text.clear()
                etQAnswer.text.clear()
                etStudent.text.clear()

            }.addOnFailureListener {
                err -> Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}