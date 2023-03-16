package br.tospendtime.notificationcompose

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.tospendtime.notificationcompose.di.ChatChannelNotificationCompatBuilder
import br.tospendtime.notificationcompose.di.DownloadChannelNotificationCompatBuilder
import br.tospendtime.notificationcompose.di.MainChannelNotificationCompatBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @MainChannelNotificationCompatBuilder
    private val notificationBuilder: NotificationCompat.Builder,
    @DownloadChannelNotificationCompatBuilder
    private val downloadNotificationBuilder: NotificationCompat.Builder,
    @ChatChannelNotificationCompatBuilder
    private val chatNotificationCompatBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat
): ViewModel(){
    fun showSimpleNotification(){
        notificationManager.notify(1, notificationBuilder.build())
    }

    fun updateSimpleNotification() {
        notificationManager.notify(
            1, notificationBuilder
                .setContentText("Essa notificação foi atualizada")
                .build()
        )
    }

    fun showProgress(){
        val max  = 20
        var progress = 0

        viewModelScope.launch {
            while(progress != max){
                delay(1000)

                progress++

                notificationManager.notify(
                    1,
                    downloadNotificationBuilder
                        .setContentTitle("Downloading...")
                        .setContentText("${progress}/${max}")
                        .setProgress(max, progress, false)
                        .build()
                )
            }
            notificationManager.notify(
                1,
                downloadNotificationBuilder
                    .setContentTitle("Completed!")
                    .setContentText("")
                    .setContentIntent(null)
                    .clearActions()
                    .setProgress(0,0,false)
                    .build()
            )
        }
    }
    fun cancelSimpleNotification(){
        notificationManager.cancel(1)
    }

    fun showChatNotification(){
        notificationManager.notify(1,chatNotificationCompatBuilder.build())
    }
}