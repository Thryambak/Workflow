package com.example.workflow

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class AlertReciever : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        if (intent != null) {
            if(!MainActivity.isRest)
            {
                Toast.makeText(context,"OK Take a break now", Toast.LENGTH_SHORT).show()
                MainActivity.isRest=true
               MainActivity.setAlarm(context.applicationContext,MainActivity.restTime*1000L)
            }
            else{
                Toast.makeText(context,"Start working", Toast.LENGTH_SHORT).show()
                MainActivity.isRest=false
               MainActivity.setAlarm(context.applicationContext,MainActivity.workTime*1000L)
            }

        }

//        val bintent = Intent(Context.ALARM_SERVICE)
//        context.startService(bintent)

    }
}