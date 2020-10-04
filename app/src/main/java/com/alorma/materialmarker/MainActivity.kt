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
        markerFotocasa0.setOnClickListener { }
        markerFotocasa1.setOnClickListener { }

        enableSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            marker0.isEnabled = isChecked
            marker1.isEnabled = isChecked
            marker2.isEnabled = isChecked

            markerFotocasa0.isChecked = !markerFotocasa0.isChecked
            markerFotocasa1.isChecked = !markerFotocasa1.isChecked
        }
    }
}