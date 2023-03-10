package br.tospendtime.notificationcompose

import android.app.Application
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.tospendtime.notificationcompose.screen.MainScreen
import br.tospendtime.notificationcompose.ui.theme.NotificationComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application()