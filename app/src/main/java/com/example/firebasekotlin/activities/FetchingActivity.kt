package com.example.firebasekotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.R
import com.example.firebasekotlin.adapter.QtAdapter
import com.example.firebasekotlin.models.QuestionModel
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var qtRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var qtList: ArrayList<QuestionModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        qtRecyclerView = findViewById(R.id.rvEmp)
        qtRecyclerView.layoutManager = LinearLayoutManager(this)
        qtRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        qtList = arrayListOf<QuestionModel>()

        getQuestionsData()

    }

    private fun getQuestionsData() {

        qtRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Questions")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                qtList.clear()
                if (snapshot.exists()){
                    for (stdSnap in snapshot.children){
                        val stdData = stdSnap.getValue(QuestionModel::class.java)
                        qtList.add(stdData!!)
                    }
                    val mAdapter = QtAdapter(qtList)
                    qtRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : QtAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, QuestionsDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("Id", qtList[position].Id)
                            intent.putExtra("etQuestionName", qtList[position].etQName)
                            intent.putExtra("etAnswer", qtList[position].etQAnswer)
                            intent.putExtra("etStudent", qtList[position].etStudent)
                            startActivity(intent)
                        }

                    })

                    qtRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}