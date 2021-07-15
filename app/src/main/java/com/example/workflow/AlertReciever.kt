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
                if(!Intent.ACTION_BOOT_COMPLETED.equals(intent.action)) {
                if (!MainActivity.isRest) {
//                    Toast.makeText(context, "OK Take a break now", Toast.LENGTH_SHORT).show()
                    val myint = Intent(context,AlarmService::class.java)
                    context.startService(myint)



                } else {
           //         Toast.makeText(context, "Start working", Toast.LENGTH_SHORT).show()
                    val myint = Intent(context,AlarmService::class.java)
                    context.startService(myint)


                }

            }
        }

//        val bintent = Intent(Context.ALARM_SERVICE)
//        context.startService(bintent)

    }
}