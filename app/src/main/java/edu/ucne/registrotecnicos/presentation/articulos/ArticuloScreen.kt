package edu.ucne.registrotecnicos.presentation.articulos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ArticuloScreen(
    viewModel: ArticuloViewModel = hiltViewModel(),
    goBackToArticuloListScreen: () -> Unit,
    articuloId: Int,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ArticuloBodyScreen(
        viewModel = viewModel,
        uiState = uiState,
        goBackToArticuloListScreen = goBackToArticuloListScreen,
        articuloId = articuloId
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticuloBodyScreen(
    viewModel: ArticuloViewModel,
    uiState: ArticuloUiState,
    goBackToArticuloListScreen: () -> Unit,
    articuloId: Int
) {
    LaunchedEffect(articuloId) {
        if (articuloId > 0) viewModel.find(articuloId)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (articuloId > 0) "Editar Artículo" else "Agregar Artículo"
                    )
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
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Campo Descripción
                    OutlinedTextField(
                        label = { Text(text = "Descripción") },
                        value = uiState.descripcion,
                        onValueChange = viewModel::onDescripcionChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Campo Costo
                    OutlinedTextField(
                        label = { Text(text = "Costo") },
                        value = if (uiState.costo == 0.0) "" else uiState.costo.toString(),
                        onValueChange = { newValue ->
                            newValue.toDoubleOrNull()?.let { viewModel.onCostoChange(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    // Campo Ganancia
                    OutlinedTextField(
                        label = { Text(text = "Ganancia (%)") },
                        value = if (uiState.ganancia == 0.0) "" else uiState.ganancia.toString(),
                        onValueChange = { newValue ->
                            newValue.toDoubleOrNull()?.let { viewModel.onGananciaChange(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    // Campo Precio
                    OutlinedTextField(
                        label = { Text(text = "Precio") },
                        value = uiState.precio.toString(),
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    // Espacio para el mensaje de error
                    uiState.errorMessage?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botones según si se está creando o editando el artículo
                    if (articuloId <= 0) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(onClick = { viewModel.new() }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Nuevo",
                                    tint = Color.Blue
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Nuevo")
                            }
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
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(onClick = {
                                viewModel.save()
                                goBackToArticuloListScreen()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Create,
                                    contentDescription = "Modificar",
                                    tint = Color.Green
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Modificar")
                            }
                            OutlinedButton(onClick = {
                                viewModel.delete(uiState.articuloId)
                                goBackToArticuloListScreen()
                            }) {
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
