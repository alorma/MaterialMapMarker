package com.alorma.materialmarker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        marker0.setOnClickListener { }
        marker1.setOnClickListener { }
        marker2.setOnClickListener { }
    }
}