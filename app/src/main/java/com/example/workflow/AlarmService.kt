package com.example.workflow

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Vibrator
import android.provider.MediaStore
import androidx.core.app.NotificationCompat

class AlarmService : Service() {

   lateinit var mp3 : MediaPlayer
    lateinit var vib : Vibrator

    override fun onCreate() {
        super.onCreate()
        if (!MainActivity.isRest) {
            mp3 = MediaPlayer.create(this, R.raw.rest)


        }
        else{
             mp3 = MediaPlayer.create(this, R.raw.work)

        }
        mp3.isLooping = true
        vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator



    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
       val notificationIntent = Intent(this,RingActivity::class.java)
        val pending = PendingIntent.getActivity(this,0,notificationIntent,0)
        val title = intent?.let { String.format("%s Alarm", it.getStringExtra("TITLE"),"Work") };
        val notification = NotificationCompat.Builder(this)
            .setContentTitle(title)
            .setContentText("Times up!")
            .setSmallIcon(R.drawable.ic_baseline_alarm_24)
            .setContentIntent(pending)
            .build()

            mp3.start()
        val pattern = longArrayOf(0,100,1000)
        vib.vibrate(pattern ,0)
        startForeground(1,notification)
        return  START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mp3.stop()
        vib.cancel()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}