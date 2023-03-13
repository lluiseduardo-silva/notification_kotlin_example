package br.tospendtime.notificationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import br.tospendtime.notificationcompose.navigation.SetupNavGraph
import br.tospendtime.notificationcompose.ui.theme.NotificationComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationComposeTheme {
               SetupNavGraph(navController = rememberNavController())
            }
        }
    }
}
