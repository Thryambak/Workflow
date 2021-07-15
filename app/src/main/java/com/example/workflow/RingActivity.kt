package com.example.workflow

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.workflow.MainActivity.Companion.unitTime
import com.example.workflow.databinding.ActivityRingBinding

class RingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.okay.setOnClickListener(View.OnClickListener {
            if(MainActivity.isRest) {
                val myint = Intent(this,AlarmService::class.java)
                stopService(myint)
                MainActivity.setAlarm(this.applicationContext, MainActivity.workTime * unitTime)
                MainActivity.isRest = false
                finish()
            }else {
                val myint = Intent(this,AlarmService::class.java)
                stopService(myint)
                MainActivity.setAlarm(this.applicationContext, MainActivity.restTime * unitTime)
                MainActivity.isRest = true
                finish()
            }

        })
        binding.cancel.setOnClickListener(View.OnClickListener {
            val myint = Intent(this,AlarmService::class.java)
            stopService(myint)
           MainActivity.cancelAlarms(applicationContext)
            MainActivity.isRest = false
            finish()
        })

        if(MainActivity.isRest)
            binding.textView.text = "Start working"
        else
            binding.textView.text = "Go Rest"



    }
}