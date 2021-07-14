package com.example.workflow

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.*

class MainActivity : AppCompatActivity() {

    val maxTime = 60
    var isRunning = false;
    var isRest = false;
    var workTime = 25
    var restTime = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startButton = findViewById<Button>(R.id.start)
        val work: TextView = findViewById(R.id.work)
        val rest: TextView = findViewById(R.id.rest)
        val initialWait = 5000

        val sharedPref: SharedPreferences = getSharedPreferences("MyPref", 0)

        workTime = sharedPref.getInt("workTime",25)
        restTime = sharedPref.getInt("restTime",5)
        work.setText(Integer.toString(workTime))
        rest.setText(Integer.toString(restTime))


        if (sharedPref.getBoolean("isRunning", false)) {
            startButton.setText("Stop")
            isRunning = true;
        }

        work.setOnClickListener(View.OnClickListener {
            if (!isRunning) {
                val view = this.layoutInflater.inflate(R.layout.numpic, null)
                val numP = view.findViewById<NumberPicker>(R.id.numP)
                numP.minValue = 1
                numP.maxValue = maxTime
                numP.value = workTime


                val builder = android.app.AlertDialog.Builder(this)
                builder.setView(view)
                val alert = builder.create()
                alert.show()
                val doneButton: Button = view.findViewById(R.id.button)
                val restnum: NumberPicker = view.findViewById(R.id.numP)
                doneButton.setOnClickListener(View.OnClickListener {
                    workTime = numP.value
                    work.setText(Integer.toString(workTime));
                    alert.dismiss()


                })


            }
        })

        rest.setOnClickListener(View.OnClickListener {
            if (!isRunning) {
                val view: View = this.layoutInflater.inflate(R.layout.numpic, null)
                val numP = view.findViewById<NumberPicker>(R.id.numP)
                numP.minValue = 1
                numP.maxValue = maxTime
                numP.value = restTime


                val builder = android.app.AlertDialog.Builder(this)
                builder.setView(view)
                val alert = builder.create()
                alert.show()
                val doneButton: Button = view.findViewById(R.id.button)
                val restnum: NumberPicker = view.findViewById(R.id.numP)
                doneButton.setOnClickListener(View.OnClickListener {
                    restTime = numP.value
                    rest.setText(Integer.toString(restTime));
                    alert.dismiss()


                })
            }

        })

        startButton.setOnClickListener(View.OnClickListener {
//            Toast.makeText(this, Integer.toString(workTime), Toast.LENGTH_SHORT).show()
//            Toast.makeText(this,Integer.toString(restTime),Toast.LENGTH_LONG).show()

            if (!isRunning) {


             setAlarm(applicationContext,workTime +0L)
                isRest = true;
               // Toast.makeText(this, "work time is"+Integer.toString(workTime), Toast.LENGTH_SHORT).show()
                startButton.setText("Stop")
                isRunning = true;
            } else {
                var alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent: Intent = Intent(this, AlertReciever::class.java)
                val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
                alarmManager.cancel(pendingIntent)

                startButton.setText("Start")
                isRunning = false
            }

        })

    }
    companion object {
        public fun setAlarm(x:Context,time: Long) {
            var alarmManager: AlarmManager = x.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(x, AlertReciever::class.java)
            val pendingIntent = PendingIntent.getBroadcast(x, 1, intent, 0)
            val c = Calendar.getInstance()
            c.setTimeInMillis(System.currentTimeMillis() + time)
            c.set(Calendar.MILLISECOND, 0)
            c.set(Calendar.SECOND, 0)

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
        }
    }
}