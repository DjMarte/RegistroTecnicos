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
import edu.ucne.registrotecnicos.data.repository.TicketRepository
import kotlinx.coroutines.launch
import android.app.DatePickerDialog
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import edu.ucne.registrotecnicos.data.local.entity.TicketEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen(
    goBackToListScreen: () -> Unit,
    ticketRepository: TicketRepository,
    tecnicoList: List<TecnicoEntity>,
    ticketId: Int
) {
    var cliente by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("") }
    var tecnicoSeleccionado by remember { mutableStateOf<TecnicoEntity?>(null) }
    var fecha by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }

    var expandedPrioridad by remember { mutableStateOf(false) }
    var expandedTecnico by remember { mutableStateOf(false) }
    val opcionesPrioridad = listOf("1", "2", "3")
    val calendar = remember { Calendar.getInstance() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(ticketId) {
        if (ticketId > 0) {
            val ticket = ticketRepository.find(ticketId)
            ticket?.let {
                fecha = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(it.fecha)
                cliente = it.cliente
                asunto = it.asunto
                descripcion = it.descripcion
                prioridad = it.prioridadId.toString()
                tecnicoSeleccionado = tecnicoList.find { tecnico ->
                    tecnico.tecnicoId == it.tecnicoId
                }
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Campo Cliente
                    OutlinedTextField(
                        label = { Text("Cliente") },
                        value = cliente,
                        onValueChange = { cliente = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo Asunto
                    OutlinedTextField(
                        label = { Text("Asunto") },
                        value = asunto,
                        onValueChange = { asunto = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo Fecha
                    OutlinedButton(
                        onClick = {
                            val datePicker = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    calendar.set(year, month, dayOfMonth)
                                    val formattedDate =
                                        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                            .format(calendar.time)
                                    fecha = formattedDate
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePicker.show()
                        },
                        modifier = Modifier
                            .size(200.dp, 50.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = if (fecha.isEmpty()) "Seleccionar Fecha" else fecha)
                    }

                    // Campo Descripción
                    OutlinedTextField(
                        label = { Text("Descripción") },
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo Prioridad
                    ExposedDropdownMenuBox(
                        expanded = expandedPrioridad,
                        onExpandedChange = { expandedPrioridad = !expandedPrioridad }
                    ) {
                        OutlinedTextField(
                            value = prioridad,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Prioridad") },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPrioridad)
                            },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedPrioridad,
                            onDismissRequest = { expandedPrioridad = false }
                        ) {
                            opcionesPrioridad.forEach { opcion ->
                                DropdownMenuItem(
                                    text = { Text(opcion) },
                                    onClick = {
                                        prioridad = opcion
                                        expandedPrioridad = false
                                    }
                                )
                            }
                        }
                    }

                    // Campo Técnico
                    ExposedDropdownMenuBox(
                        expanded = expandedTecnico,
                        onExpandedChange = { expandedTecnico = !expandedTecnico }
                    ) {
                        OutlinedTextField(
                            value = tecnicoSeleccionado?.nombres ?: "Seleccione un técnico",
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Técnico") },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTecnico)
                            },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedTecnico,
                            onDismissRequest = { expandedTecnico = false }
                        ) {
                            tecnicoList.forEach { tecnico ->
                                DropdownMenuItem(
                                    text = { Text(tecnico.nombres) },
                                    onClick = {
                                        tecnicoSeleccionado = tecnico
                                        expandedTecnico = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(2.dp))
                    errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }

                    // Botones Nuevo y Guardar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botón Nuevo
                        OutlinedButton(onClick = {
                            cliente = ""
                            asunto = ""
                            descripcion = ""
                            prioridad = ""
                            fecha = ""
                            tecnicoSeleccionado = null
                            errorMessage = null
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Nuevo"
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text("Nuevo")
                        }

                        // Botón Guardar
                        OutlinedButton(onClick = {
                            if (cliente.isEmpty() || asunto.isEmpty() || descripcion.isEmpty() ||
                                prioridad.isEmpty() || tecnicoSeleccionado == null || fecha.isEmpty()
                            ) {
                                errorMessage = "Todos los campos son obligatorios"
                                return@OutlinedButton
                            }


                            val fechaConvertida =
                                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                    .parse(fecha)
                            scope.launch {
                                ticketRepository.save(
                                    TicketEntity(
                                        ticketId = if (ticketId > 0) ticketId else null,
                                        fecha = fechaConvertida ?: Date(),
                                        prioridadId = prioridad.toInt(),
                                        cliente = cliente,
                                        asunto = asunto,
                                        descripcion = descripcion,
                                        tecnicoId = tecnicoSeleccionado!!.tecnicoId
                                    )
                                )
                                cliente = ""
                                asunto = ""
                                descripcion = ""
                                prioridad = ""
                                fecha = ""
                                tecnicoSeleccionado = null
                                errorMessage = null
                            }

                        }) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Guardar"
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(text = if (ticketId > 0) "Modificar" else "Guardar")
                        }
                        if(ticketId > 0){
                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        ticketRepository.delete(
                                            TicketEntity(
                                                ticketId = ticketId,
                                                fecha = Date(),
                                                prioridadId = 0,
                                                cliente = "",
                                                asunto = "",
                                                descripcion = "",
                                                tecnicoId = 0
                                            )
                                        )
                                        cliente = ""
                                        asunto = ""
                                        descripcion = ""
                                        prioridad = ""
                                        fecha = ""
                                        tecnicoSeleccionado = null
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color.Red
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Eliminar", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}

