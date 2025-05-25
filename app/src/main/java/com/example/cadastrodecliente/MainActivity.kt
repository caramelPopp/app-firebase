package com.example.cadastrodecliente

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    // Tela principal da aplicação, que aparece antes de qualquer outra
    var currentScreen by remember { mutableStateOf("home") }

    // Registrando os dados necessários para a navegação interna entre telas
    when (currentScreen) {
        /*
        - 'onBack' levará da tela do momento de volta para a Home.
        - 'onNavigateToRegister' levará a tela de cadastro.
        - 'onNavigateToVisualize' levará a tela de listagem de usuários.
        - A variável de instância do banco só é criada aqui, mas estará presente
        nas outras telas como parâmetro.
        */

        "home" -> HomeScreen(
            onNavigateToRegister = { currentScreen = "cadastro" }
        )

        "cadastro" -> RegisterScreen(
            onNavigateToVisualize = { currentScreen = "visualizar" },
            onBack = { currentScreen = "home" },
            db = db
        )

        "visualizar" -> VisualizeScreen(
            onBack = { currentScreen = "home" },
            db = db
        )
    }

}

@Composable
fun HomeScreen(
    onNavigateToRegister: () -> Unit
) {
    Column(
        Modifier.background(Color.Transparent).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cadastro de Cliente", fontSize = 35.sp, color = Purple40)
        Spacer(modifier = Modifier.height(28.dp))
        Button(
            onClick = onNavigateToRegister,
            colors = ButtonDefaults.buttonColors(containerColor = Purple40)
        ) {
            Text("Cadastrar-se")
        }
    }
}

@Composable
fun RegisterScreen(
    onNavigateToVisualize: () -> Unit,
    onBack: () -> Unit,
    db: com.google.firebase.firestore.FirebaseFirestore
) {
    var name by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ) {
            Text("Cadastre-se!", fontSize = 32.sp, color = Purple40)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Campo 'Nome'
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") })

        // Campo 'Data de Nascimento'
        OutlinedTextField(
            value = birth,
            onValueChange = { birth = it },
            label = { Text("Data de Nascimento")})

        // Campo 'Email'
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = Email)
        )

        // Campo 'Telefone'
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Telefone") },
            keyboardOptions = KeyboardOptions(keyboardType = Phone)
        )

        // Campo 'Endereço'
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Endereço") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val user = hashMapOf(
                    "nome" to name,
                    "nascimento" to birth,
                    "email" to email,
                    "telefone" to phone,
                    "endereco" to address
                )
                db.collection("users").add(user)

                // Limpa campos
                name = ""
                birth = ""
                email = ""
                phone = ""
                address = ""

            },
            colors = ButtonDefaults.buttonColors(containerColor = Purple40)
        ) {
            Text("Salvar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToVisualize,
            colors = ButtonDefaults.buttonColors(containerColor = Purple40)
        ) {
            Text("Listar Usuários")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Absolute.Left
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Purple40)
            ) {
                Text("Voltar")
            }
        }
    }
}

@Composable
fun VisualizeScreen(
    onBack: () -> Unit,
    db: com.google.firebase.firestore.FirebaseFirestore
) {
    var answer by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val builder = StringBuilder()
                for (document in result) {
                    val name = document.getString("nome") ?: ""
                    val birth = document.getString("nascimento") ?: ""
                    val email = document.getString("email") ?: ""
                    val phone = document.getString("telefone") ?: ""
                    val address = document.getString("endereco") ?: ""

                    builder.append(
                        """
                        Nome: $name
                        Nascimento: $birth
                        Email: $email
                        Telefone: $phone
                        Endereço: $address
                        """.trimIndent()
                    )
                    builder.append("\n\n--------------------------------------\n\n")
                }
                answer = builder.toString()
            }
    }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            Arrangement.Center
        ) {
            Text("Usuários Cadastrados", fontSize = 32.sp, color = Purple40)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(answer, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = Purple40)
        ) {
            Text("Voltar")
        }

        Spacer(modifier = Modifier.height(28.dp))
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
