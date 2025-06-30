package com.example.goodvalueapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var counterDisplay: TextView
    private lateinit var upButton: Button
    private lateinit var downButton: Button
    private lateinit var confirmButton: Button

    private var counter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
        updateCounter()
    }

    private fun initViews() {
        counterDisplay = findViewById(R.id.counterDisplay)
        upButton = findViewById(R.id.upButton)
        downButton = findViewById(R.id.downButton)
        confirmButton = findViewById(R.id.confirmButton)
    }

    private fun setupClickListeners() {
        upButton.setOnClickListener {
            counter++
            updateCounter()
        }

        downButton.setOnClickListener {
            if (counter > 1) {
                counter--
                updateCounter()
            }
        }

        confirmButton.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra("COUNTER_VALUE", counter)
            startActivity(intent)
        }
    }

    private fun updateCounter() {
        counterDisplay.text = String.format("%02d", counter)
    }
}