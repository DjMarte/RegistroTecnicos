package edu.ucne.registrotecnicos.presentation.tecnicos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrotecnicos.data.local.entity.TecnicoEntity

@Composable
fun TecnicoListScreen(
    viewModel: TecnicoViewModel = hiltViewModel(),
    goToTecnicoScreen: (Int) -> Unit,
    onAddTecnico: ()-> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TecnicoListBodyScreen(
        uiState,
        onAddTecnico,
        goToTecnicoScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnicoListBodyScreen(
    uiState: TecnicoUiState,
    onAddTecnico: () -> Unit,
    goToTecnicoScreen: (Int) -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Listado de Técnicos")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTecnico) {
                Icon(Icons.Default.Add, "Agregar técnico")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            TableHeader()
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(uiState.listaTecnicos) {
                    TecnicoRow(it, goToTecnicoScreen)
                }
            }
        }
    }
}

@Composable
private fun TableHeader() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(2f),
            text = "ID",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier.weight(2f),
            text = "Nombres",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier.weight(2f),
            text = "Sueldo",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 5.dp))
}

@Composable
private fun TecnicoRow(
    it: TecnicoEntity,
    goToTecnicoScreen: (Int) -> Unit)
{
    Row(
        Modifier.padding(15.dp)
            .clickable { goToTecnicoScreen(it.tecnicoId!!) },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.weight(2f), text = it.tecnicoId.toString())
        Text(
            modifier = Modifier.weight(2f),
            text = it.nombres,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(modifier = Modifier.weight(2f), text = it.sueldo.toString())
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 5.dp))
}


