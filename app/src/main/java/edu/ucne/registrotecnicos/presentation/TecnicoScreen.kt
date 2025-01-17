package edu.ucne.registrotecnicos.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.ucne.registrotecnicos.data.local.entity.TecnicoEntity
import edu.ucne.registrotecnicos.data.repository.TecnicoRepository
import kotlinx.coroutines.launch

@Composable
fun TecnicoScreen(
    goBackToList: ()-> Unit,
    tecnicoRepository: TecnicoRepository
){
    var nombres by remember { mutableStateOf("") }
    var sueldo by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Campo Nombres
                    OutlinedTextField(
                        label = { Text(text = "Nombres") },
                        value = nombres,
                        onValueChange = { nombres = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Campo Sueldo
                    OutlinedTextField(
                        label = { Text(text = "Sueldo") },
                        value = sueldo,
                        onValueChange = { sueldo = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Espacio para el mensaje de error
                    Spacer(modifier = Modifier.padding(2.dp))
                    errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }

                    // Botones de Nuevo y Guardar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly, // Espacio entre botones
                        verticalAlignment = Alignment.CenterVertically // Centrado de botones
                    ) {
                        // Boton Nuevo
                        OutlinedButton(onClick = {
                            nombres = ""
                            sueldo = ""
                            errorMessage = null
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "New button",

                                )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text("Nuevo")
                        }

                        val scope = rememberCoroutineScope()
                        // Boton Guardar
                        OutlinedButton(onClick = {
                            if (nombres.isEmpty() || sueldo.isEmpty()) {
                                errorMessage = "Nombres o Sueldo vacíos"
                                return@OutlinedButton
                            }

                            scope.launch {
                                tecnicoRepository.saveTecnico(
                                    TecnicoEntity(
                                        nombres = nombres,
                                        sueldo = sueldo.toDouble()
                                    )
                                )
                                nombres = ""
                                sueldo = ""
                                errorMessage = null
                            }


                        }) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save button"
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(text = "Guardar")
                        }
                    }
                }
            }
        }
    }
}
