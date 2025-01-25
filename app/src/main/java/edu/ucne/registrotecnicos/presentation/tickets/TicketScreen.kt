package edu.ucne.registrotecnicos.presentation.tickets

import android.app.DatePickerDialog
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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun TicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    goBackToListScreen: () -> Unit,
    ticketId: Int
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketBodyScreen(
        viewModel,
        uiState = uiState,
        goBackToListScreen,
        ticketId = ticketId
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBodyScreen(
    viewModel: TicketViewModel,
    uiState: TicketUiState,
    goBackToListScreen: () -> Unit,
    ticketId: Int
){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    val tecnicoSeleccionado = uiState.tecnicoSeleccionado
    var expandedTecnico by remember { mutableStateOf(false) }
    val tecnicoList by viewModel.tecnicoList.collectAsStateWithLifecycle()

    LaunchedEffect(ticketId) {
        if (ticketId > 0)
            viewModel.find(ticketId)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = if (ticketId > 0) "Editar ticket" else "Agregar ticket")
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
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Campo Cliente
                    OutlinedTextField(
                        label = { Text("Cliente") },
                        value = uiState.cliente,
                        onValueChange = viewModel::onClienteChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo Asunto
                    OutlinedTextField(
                        label = { Text("Asunto") },
                        value = uiState.asunto,
                        onValueChange = viewModel::onAsuntoChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo Fecha
                    OutlinedButton(
                        onClick = {
                            val datePicker = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    calendar.set(year, month, dayOfMonth)
                                    val selectedDate = calendar.time
                                    viewModel.onFechaChange(selectedDate)
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
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Seleccionar Fecha",
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        Text(text = if (!uiState.fechaSeleccionada) "Seleccionar Fecha" else dateFormatter.format(uiState.fecha))
                    }

                    // Campo Descripción
                    OutlinedTextField(
                        label = { Text("Descripción") },
                        value = uiState.descripcion,
                        onValueChange = viewModel::onDescripcionChange,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2,
                        singleLine = false
                    )

                    // Campo Prioridad
                    val prioridades = listOf(1, 2, 3)
                    var expandedPrioridad by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expandedPrioridad,
                        onExpandedChange = { expandedPrioridad = !expandedPrioridad }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            readOnly = true,
                            value = uiState.prioridadId?.toString() ?: "Seleccionar Prioridad",
                            onValueChange = {},
                            label = { Text("Prioridad") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPrioridad) }
                        )
                        ExposedDropdownMenu(
                            expanded = expandedPrioridad,
                            onDismissRequest = { expandedPrioridad = false }
                        ) {
                            prioridades.forEach { numero ->
                                DropdownMenuItem(
                                    text = { Text(text = numero.toString()) },
                                    onClick = {
                                        expandedPrioridad = false
                                        viewModel.onPrioridadChange(numero)
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
                                        viewModel.onTecnicoChange(tecnico)
                                        expandedTecnico = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(2.dp))
                    uiState.errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }

                    // Botones Nuevo y Guardar
                    if(ticketId <= 0){
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Botón Nuevo
                            OutlinedButton(onClick = {
                                viewModel.new()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Nuevo",
                                    tint = Color.Blue
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text("Nuevo")
                            }

                            // Botón Guardar
                            OutlinedButton(onClick = {
                                viewModel.save()

                            }) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Guardar",
                                    tint = Color.Green
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Guardar")
                            }
                        }
                    }
                    else{
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Botón Modificar
                            OutlinedButton(onClick = {
                                viewModel.save()
                                goBackToListScreen()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Create,
                                    contentDescription = "Modificar",
                                    tint = Color.Green
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Modificar")
                            }

                            // Boton Eliminar
                            OutlinedButton(
                                onClick = {
                                    viewModel.delete()
                                    goBackToListScreen()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color.Red
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}

