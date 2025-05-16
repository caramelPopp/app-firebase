package com.example.cadastrodecliente

import android.content.ContentValues.TAG
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType.Companion.Email
import androidx.compose.ui.text.input.KeyboardType.Companion.Phone
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.cadastrodecliente.ui.theme.CadastroDeClienteTheme
import com.example.cadastrodecliente.ui.theme.Purple40
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

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

@OptIn(UnstableApi::class)
@Composable
fun App() {
    val db = Firebase.firestore
    var exibition = ""

    var name by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column (
        Modifier.background(Color.Transparent).fillMaxSize()
    ) {
        Row(
            Modifier.fillMaxWidth().padding(20.dp)
        ) {

        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ){
            Text("Crie sua conta!", fontFamily = FontFamily.Default, fontSize = 35.sp, color = Purple40)
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ){
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome:") }
            )
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ){
            OutlinedTextField(
                value = birth,
                onValueChange = { birth = it },
                label = { Text("Data de Nascimento:") },
                singleLine = true
            )
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ){
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail:") },
                maxLines = 2,
                keyboardOptions = KeyboardOptions(keyboardType = Email)
            )
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ) {
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Telefone:") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = Phone)
            )
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ) {
            OutlinedTextField(
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
                    val user = hashMapOf(
                        "nome" to name,
                        "nascimento" to birth,
                        "email" to email,
                        "telefone" to phone,
                        "endereco" to address
                    )

                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }

                    name = ""
                    birth = ""
                    email = ""
                    phone = ""
                    address = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = Purple40)
            ) {
                Text("Cadastrar")
            }
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp)
        ) {
            Button(
                onClick = {
                    db.collection("users")
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                exibition += Log.d(TAG, "${document.id} => ${document.data}").toString()
                            }
                        }
                        .addOnFailureListener { exception ->
                            exibition = Log.w(TAG, "Error getting documents.", exception).toString()
                        }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Purple40)
            ) {
                Text("Verificar Registros")
            }
        }
        Row(
            Modifier.fillMaxWidth().padding(20.dp)
        ){
            Text(exibition)
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
