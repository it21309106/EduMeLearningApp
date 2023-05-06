package com.example.firebasekotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebasekotlin.R
import com.example.firebasekotlin.models.QuestionModel
import com.google.firebase.database.FirebaseDatabase

class QuestionsDetailsActivity : AppCompatActivity() {

    private lateinit var tvId: TextView
    private lateinit var tvQuestionName: TextView
    private lateinit var tvAnswer: TextView
    private lateinit var tvStudent: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("Id").toString(),
                intent.getStringExtra("etQName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("Id").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Questions").child(id)
        val mTask = dbRef.removeValue()
        
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Questions data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {
            error -> Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(Id: String, etQuestionName: String) {

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etQuestionName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etAnswer = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etStudent = mDialogView.findViewById<EditText>(R.id.etEmpSalary)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etQuestionName.setText(intent.getStringExtra("etQuestionName").toString())
        etAnswer.setText(intent.getStringExtra("etAnswer").toString())
        etStudent.setText(intent.getStringExtra("etStudent").toString())

        mDialog.setTitle("Updating $etQuestionName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateStdData(
                Id,
                etQuestionName.text.toString(),
                etAnswer.text.toString(),
                etStudent.text.toString()
            )

            Toast.makeText(applicationContext, "Question Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvQuestionName.text = etQuestionName.text.toString()
            tvAnswer.text = etAnswer.text.toString()
            tvStudent.text = etStudent.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateStdData(
        id: String,
        question: String,
        answer: String,
        student: String) {

        val dbRef = FirebaseDatabase.getInstance().getReference("Questions").child(id)
        val qInfo = QuestionModel(id,question,answer,student)
        dbRef.setValue(qInfo)
    }

    private fun initView() {
        tvId = findViewById(R.id.tvEmpId)
        tvQuestionName = findViewById(R.id.tvEmpName)
        tvAnswer = findViewById(R.id.tvEmpAge)
        tvStudent = findViewById(R.id.tvEmpSalary)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvId.text = intent.getStringExtra("Id")
        tvQuestionName.text = intent.getStringExtra("etQuestionName")
        tvAnswer.text = intent.getStringExtra("etAnswer")
        tvStudent.text = intent.getStringExtra("etStudent")

    }
}
