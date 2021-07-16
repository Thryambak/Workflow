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





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val work: TextView = findViewById(R.id.work)
        val rest: TextView = findViewById(R.id.rest)
        startButton = findViewById<Button>(R.id.start)
        val initialWait = 5000

        val sharedPref: SharedPreferences = getSharedPreferences("MyPref", 0)

        workTime = sharedPref.getLong("workTime",25)
        restTime = sharedPref.getLong("restTime",5)

        work.setText(workTime.toString())
        rest.setText(restTime.toString())


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
                numP.value = workTime.toInt()


                val builder = android.app.AlertDialog.Builder(this,R.style.CustomAlertDialog)
                builder.setView(view)
                val alert = builder.create()
                alert.show()
                val doneButton: Button = view.findViewById(R.id.button)
                val restnum: NumberPicker = view.findViewById(R.id.numP)
                doneButton.setOnClickListener(View.OnClickListener {
                    workTime = numP.value.toLong()
                    work.setText(workTime.toString());
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
                numP.value = restTime.toInt()


                val builder = android.app.AlertDialog.Builder(this,R.style.CustomAlertDialog)
                builder.setView(view)
                val alert = builder.create()
                alert.show()
                val doneButton: Button = view.findViewById(R.id.button)
                val restnum: NumberPicker = view.findViewById(R.id.numP)
                doneButton.setOnClickListener(View.OnClickListener {
                    restTime = numP.value.toLong()
                    rest.setText(restTime.toString());
                    alert.dismiss()


                })
            }

        })

        startButton.setOnClickListener(View.OnClickListener {
//            Toast.makeText(this, Integer.toString(workTime), Toast.LENGTH_SHORT).show()
//            Toast.makeText(this,Integer.toString(restTime),Toast.LENGTH_LONG).show()

            if (!isRunning) {

                Log.i("rest", workTime.toString())
             setAlarm(applicationContext,workTime *unitTime)
                val sharedPref: SharedPreferences = getSharedPreferences("MyPref", 0)
                isRunning = true;
                val editor = sharedPref.edit()
                editor.putLong("workTime", workTime)
                editor.putLong("restTime", restTime)
                editor.putBoolean("isRunning", isRunning)
                editor.commit()
               // isRest = true;
               // Toast.makeText(this, "work time is"+Integer.toString(workTime), Toast.LENGTH_SHORT).show()
                startButton.setText("Stop")

            } else {
                val myint = Intent(this,AlarmService::class.java)
                stopService(myint)
                cancelAlarms(this);



            }

        })

    }
    companion object {
        val unitTime = 60000L
        var workTime = 25L
        var isRunning = false;
       lateinit var  startButton :Button
        var restTime = 5L
        var isRest = false;
        public fun setAlarm(x:Context,time: Long) {
            var alarmManager: AlarmManager = x.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(x, AlertReciever::class.java)
           // intent.putExtra("isRest",isRest)
            intent.putExtra("workTime",workTime)
            intent.putExtra("restTime",restTime)
//            Log.i("rest", workTime.toString())
            val pendingIntent = PendingIntent.getBroadcast(x, 1, intent, 0)
            val c = Calendar.getInstance()
            c.setTimeInMillis(System.currentTimeMillis() + time)
//            c.set(Calendar.MILLISECOND, 0)
//            c.set(Calendar.SECOND, 0)

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
        }

        fun cancelAlarms(x:Context){
            var alarmManager: AlarmManager = x.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent: Intent = Intent(x, AlertReciever::class.java)
            val pendingIntent = PendingIntent.getBroadcast(x, 1, intent, 0)
            alarmManager.cancel(pendingIntent)
            val sharedPref: SharedPreferences = x.getSharedPreferences("MyPref", 0)

         val editor =   sharedPref.edit()
             editor.putBoolean("isRunning",false)
            editor.commit()
            isRest=false
            startButton.setText("Start")

            isRunning = false
        }
    }
}