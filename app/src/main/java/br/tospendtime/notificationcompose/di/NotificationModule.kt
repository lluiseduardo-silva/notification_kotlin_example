package br.tospendtime.notificationcompose.di

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.net.toUri
import br.tospendtime.notificationcompose.MainActivity
import br.tospendtime.notificationcompose.R
import br.tospendtime.notificationcompose.navigation.MY_ARG
import br.tospendtime.notificationcompose.navigation.MY_URI
import br.tospendtime.notificationcompose.receiver.MyReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

const val RESULT_KEY = "RESULT_KEY"
@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Singleton
    @Provides
    @MainChannelNotificationCompatBuilder
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        val intent = Intent(context, MyReceiver::class.java).apply {
            putExtra("MESSAGE", "Clicked!")
        }
        val flag =
            PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            flag
        )
        val clickIntent = Intent(
            Intent.ACTION_VIEW,
            "$MY_URI/$MY_ARG=Coming from Notification".toUri(),
            context,
            MainActivity::class.java
        )
        val clickPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(1, flag)
        }
        return NotificationCompat.Builder(
            context,
            "Main Channel ID"
        )
                //Define o titulo da notificação
            .setContentTitle("Yamete Kudasai")
                //Define o conteudo da notificação
            .setContentText("This is a test notification for you")
                //Define a visibilidade da notificação
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                //Define o icone da notificação
            .setSmallIcon(R.drawable.icons8_notification_38___)
                //Define a prioridade da notificaçao
            .setPriority(NotificationCompat.PRIORITY_HIGH)
                //Define conteudo publico na tela de bloquei em notificações protegidas na tela de bloqueio
            .setPublicVersion(
                NotificationCompat.Builder(context,"Main Channel ID")
                    .setContentText("Escondido")
                    .setContentText("Desbloqueie seu aparelho para ver")
                    .build()
            )
                //Adiciona uma açao ao botão na notificação
            .addAction(0,"ACTION",pendingIntent)
                //Configura a abertura de telas expecificas pela notificaçao
            .setContentIntent(clickPendingIntent)
    }


    @Singleton
    @Provides
    @ChatChannelNotificationCompatBuilder
    fun provideChatNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder{

        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_MUTABLE
            } else
                0
        val remoteInput = RemoteInput.Builder(RESULT_KEY)
            .setLabel("Type here...")
            .build()
        val replyIntent = Intent(context, MyReceiver::class.java)
        val replyPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            replyIntent,
            flag
        )
        val replyAction = NotificationCompat.Action.Builder(
            0,
            "Reply",
            replyPendingIntent
        ).addRemoteInput(remoteInput).build()

        val person = Person.Builder().setName("Johny").build()
        val notificationStyle = NotificationCompat.MessagingStyle(person)
            .addMessage("Hi there!", System.currentTimeMillis(), person)
        return NotificationCompat.Builder(context, "Chat Channel ID")
            .setSmallIcon(R.drawable.icons8_notification_38___)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOnlyAlertOnce(true)
            .setStyle(notificationStyle)
            .addAction(replyAction)
    }


    @Singleton
    @Provides
    @DownloadChannelNotificationCompatBuilder
    fun provideDownloadNotificationBuilder(@ApplicationContext context:Context):NotificationCompat.Builder{
        return NotificationCompat.Builder(context, "Download Channel ID")
            .setSmallIcon(R.drawable.icons8_notification_38___)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
    }
    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Main Channel ID",
                "Main Channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            val downloadChannel = NotificationChannel(
                "Download Channel ID",
                "Download Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val chatChannel = NotificationChannel(
                "Chat Channel ID",
                "Chat Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(downloadChannel)
        }
        return notificationManager
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainChannelNotificationCompatBuilder
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DownloadChannelNotificationCompatBuilder

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChatChannelNotificationCompatBuilder