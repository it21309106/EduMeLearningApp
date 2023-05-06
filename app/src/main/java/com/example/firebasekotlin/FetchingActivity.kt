package com.example.firebasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FetchingActivity : AppCompatActivity() {

    private lateinit var qRecyclerView : RecyclerView
    private lateinit var tvLoadingData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        qRecyclerView = findViewById(R.id.rvEmp)
        qRecyclerView.layoutManager = LinearLayoutManager(this)
        qRecyclerView
        tvLoadingData = findViewById(R.id.tvLoadingData)
    }
}