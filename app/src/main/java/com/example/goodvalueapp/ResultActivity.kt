package com.example.goodvalueapp

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.parcelize.Parcelize

import com.example.goodvalueapp.ProductData


class ResultActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val productDataList = intent.getParcelableArrayListExtra<ProductData>("PRODUCT_DATA_LIST") ?: arrayListOf()

        setupRecyclerView(productDataList)
    }

    private fun setupRecyclerView(productDataList: List<ProductData>) {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // グラムあたりの価格で並べ替え（安い順）
        val sortedList = productDataList.sortedBy { it.pricePerGram }
        val adapter = ResultAdapter(sortedList)
        recyclerView.adapter = adapter
    }
}