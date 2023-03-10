package br.tospendtime.notificationcompose.di

import android.app.Notification.VISIBILITY_PRIVATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.tospendtime.notificationcompose.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Singleton
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            context,
            "Main Channel ID"
        )
            .setContentTitle("Yamete Kudasai")
            .setContentText("This is a test notification for you")
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setSmallIcon(R.drawable.icons8_notification_38___)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setPublicVersion(
                NotificationCompat.Builder(context,"Main Channel ID")
                    .setContentText("Escondido")
                    .setContentText("Desbloqueie seu aparelho para ver")
                    .build()
            )
    }

    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Main Channel ID",
                "Main Channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(channel)
        }
        return notificationManager
    }
}