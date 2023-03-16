package br.tospendtime.notificationcompose.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import br.tospendtime.notificationcompose.di.ChatChannelNotificationCompatBuilder
import br.tospendtime.notificationcompose.di.RESULT_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
//class MyReceiver: BroadcastReceiver() {
////    override fun onReceive(context: Context?, intent: Intent?) {
////        val message = intent?.getStringExtra("MESSAGE")
////        if(message != null){
////            Toast.makeText(
////                context,
////                message,
////                Toast.LENGTH_SHORT
////            ).show()
////        }
////    }
//}
@AndroidEntryPoint
class MyReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManager: NotificationManagerCompat
    @Inject
    @ChatChannelNotificationCompatBuilder
    lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onReceive(context: Context?, intent: Intent?) {
        val remoteInput = intent?.let { RemoteInput.getResultsFromIntent(it) }
        if (remoteInput != null) {
            val input = remoteInput.getCharSequence(RESULT_KEY).toString()
            val person = Person.Builder().setName("Me").build()
            val message = NotificationCompat.MessagingStyle.Message(
                input, System.currentTimeMillis(), person
            )
            val notificationStyle = NotificationCompat.MessagingStyle(person).addMessage(message)
            notificationManager.notify(
                1,
                notificationBuilder
//                    .setStyle(notificationStyle)
                    .setContentTitle("Sent!")
                    .setStyle(null)
                    .build()
            )
        }
    }
}