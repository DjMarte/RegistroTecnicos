package edu.ucne.registrotecnicos.presentation.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MessageScreen(
    viewModel: MessageViewModel = hiltViewModel(),
    ticketId: Int,
    goBackToTicketScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MessageBodyScreen(
        ticketId = ticketId,
        viewModel,
        uiState = uiState,
        goBackToTicketScreen = goBackToTicketScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageBodyScreen(
    ticketId: Int,
    viewModel: MessageViewModel,
    uiState: MessageUiState,
    goBackToTicketScreen: () -> Unit
) {
    LaunchedEffect(ticketId) {
        if (ticketId > 0) viewModel.loadMessages(ticketId)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Agregar Mensaje ticket")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.mensajes) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                             
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "By ",
                                    color = Color.Gray,
                                    style = TextStyle(fontSize = 14.sp)
                                )
                                Text(
                                    text = it.nombreTecnico,
                                    color = Color.Black,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = " on ${it.fechaHora}",
                                    color = Color.Gray,
                                    style = TextStyle(fontSize = 14.sp)
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            // Apartado para ver el mensaje mensaje
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFF2196F3),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Técnico",
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it.mensaje,
                                color = Color.Black,
                                style = TextStyle(fontSize = 16.sp, lineHeight = 20.sp)
                            )
                        }
                    }
                }
            }
            // Apartado para escribir el mensaje
            var texto by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Agrega tu mensaje") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .heightIn(min = 100.dp),
                maxLines = 5,
                singleLine = false
            )
            Text(
                text = "Words: ${texto.text.split("\\s+".toRegex()).size}",
                style = TextStyle(color = Color.Gray),
                modifier = Modifier.align(Alignment.End)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = { goBackToTicketScreen() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.Blue
                    )
                    Spacer(Modifier.padding(ButtonDefaults.IconSpacing))
                    Text(text = "Atrás")
                }
                OutlinedButton(onClick = {
                    if (texto.text.isNotBlank()) {
                        viewModel.sendMessages(texto.text)
                        texto = TextFieldValue("")
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Enviar",
                        tint = Color.Green
                    )
                    Spacer(Modifier.padding(ButtonDefaults.IconSpacing))
                    Text(text = "Enviar")
                }
            }
        }
    }
}

