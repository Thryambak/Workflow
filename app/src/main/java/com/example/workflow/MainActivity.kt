package com.example.workflow

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    val maxTime = 60
    var isRunning = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startButton = findViewById<Button>(R.id.start)
        val work : TextView = findViewById(R.id.work)
        val rest : TextView = findViewById(R.id.rest)

        val sharedPref : SharedPreferences = getSharedPreferences("isRunning",0)
        if(sharedPref.getBoolean("isRunning",false)){
            startButton.setText("Stop")
            isRunning = true;
        }

        work.setOnClickListener(View.OnClickListener {
            if(!isRunning){
                val view = this.layoutInflater.inflate(R.layout.numpic,null)
                val numP =view.findViewById<NumberPicker>(R.id.numP)
                numP.minValue = 1
                numP.maxValue = maxTime


                val builder = android.app.AlertDialog.Builder(this)
                builder.setView(view)
                val alert = builder.create()
                alert.show()

            }
        })


    }





}