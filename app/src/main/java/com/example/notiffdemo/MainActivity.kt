package com.example.notiffdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val channelId = "com.example.notiffdemo"
    var notificationManager: NotificationManager? = null
    private val KEY_REPLY = "key_reply"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelId, "DemoChannel", "this is a demo notification")



        btnGenerateNotification.setOnClickListener {
            displayNotification()

        }
    }

    private fun displayNotification(){
        val notificationId = 45

        // Notification tap action
        val intent1 = Intent(this, SecondActivity::class.java)
        val pendingIntent  = PendingIntent.getActivity(
            this,
            0,
            intent1,
            PendingIntent.FLAG_UPDATE_CURRENT)

        // Reply action
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert your name here")
            build()
        }

        val replyAction : NotificationCompat.Action = NotificationCompat.Action.Builder(
            0,
            "Reply",
            pendingIntent
        ).addRemoteInput(remoteInput)
            .build()



        // Notification action button 1


        val intent2 = Intent(this, DetailsActivity::class.java)
        val pendingIntent2  = PendingIntent.getActivity(
            this,
            0,
            intent2,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val action1: NotificationCompat.Action= NotificationCompat.Action.Builder(0,"Details", pendingIntent2).build()

        //Notification action button 2

        val intent3 = Intent(this, SettingsActivity::class.java)
        val pendingIntent3  = PendingIntent.getActivity(
            this,
            0,
            intent3,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val action2: NotificationCompat.Action= NotificationCompat.Action.Builder(0,"Settings", pendingIntent3).build()



        val notification = NotificationCompat.Builder(this@MainActivity, channelId)
                .setContentTitle("Demo Notification")
                .addAction(action1)
                .addAction(action2)
                .setContentText(" This is to learn how notitfications work")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setAutoCancel(true)
                .addAction(replyAction)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
        notificationManager?.notify(notificationId, notification)



    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            val importanceLevel = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name,importanceLevel).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }



}