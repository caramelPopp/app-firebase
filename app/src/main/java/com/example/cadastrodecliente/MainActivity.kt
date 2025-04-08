package com.example.cadastrodecliente

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cadastrodecliente.ui.theme.CadastroDeClienteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CadastroDeClienteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppPreview()
                }
            }
        }
    }
}

@Composable
fun App() {
    var name by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column (
        Modifier.background(Color.LightGray).fillMaxSize()
    ) {
        Row(
            Modifier.fillMaxWidth().padding(20.dp)
        ) {

        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ){
            Text("Cadastro de Cliente", fontFamily = FontFamily.Default, fontSize = 35.sp)
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ){
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome:") }
            )
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ){
            TextField(
                value = birth,
                onValueChange = { birth = it },
                label = { Text("Data de Nascimento:") }
            )
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ){
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail:") }
            )
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ) {
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Telefone:") }
            )
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ) {
            TextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Endereço:") }
            )
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ){
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("Cadastrar")
            }
        }
    }
}

@Preview
@Composable
fun AppPreview(){
    CadastroDeClienteTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            App()
        }
    }
}