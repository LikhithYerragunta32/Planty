package uk.ac.tees.mad.planty.notificaion

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import uk.ac.tees.mad.planty.R
import java.util.Calendar

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "watering_channel",
                "Plantly Water Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Reminders to water your plants regularly"
            }
            notificationManager.createNotificationChannel(channel)
        }


        val message = intent?.getStringExtra("message")
            ?: "Time to water your lovely plants! 🌿"


        val notification = NotificationCompat.Builder(context, "watering_channel")
            .setContentTitle("Plantly 🌱")
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .build()


        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}


fun scheduleDailyWaterReminders(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    val messages = listOf(
        "Don't forget to water your plants today! 💧",
        "Your green friends are thirsty  give them some love! 🌱",
        "Check soil moisture  your plants may need watering! 🪴"
    )


    val times = listOf(8 to 0, 12 to 0, 18 to 0)

    for (i in times.indices) {
        val (hour, minute) = times[i]

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("message", messages[i % messages.size])
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            i,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }


        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}



fun showInstantNotification(context: Context) {
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("message", "💧 Just a reminder: water your plants today!")
    }
    context.sendBroadcast(intent)
}