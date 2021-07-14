package com.example.workflow

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class AlertReciever : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context,"hello", Toast.LENGTH_SHORT).show()
        MainActivity.setAlarm(context.applicationContext,4000)
//        val bintent = Intent(Context.ALARM_SERVICE)
//        context.startService(bintent)
        Log.i("Alert","here")
    }
}