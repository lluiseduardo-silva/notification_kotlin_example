package br.tospendtime.notificationcompose.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import br.tospendtime.notificationcompose.MainViewModel


@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = mainViewModel::showSimpleNotification){
            Text(text="Notificação simples")
        }
        Button(onClick = mainViewModel::updateSimpleNotification){
            Text(text="Atualizar Notificação")
        }
        Button(onClick = mainViewModel::cancelSimpleNotification){
            Text(text="Cancelar Notificação")
        }
    }
}