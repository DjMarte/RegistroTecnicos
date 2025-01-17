package edu.ucne.registrotecnicos.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.ucne.registrotecnicos.data.local.entity.TecnicoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnicoListScreen(
    tecnicoList: List<TecnicoEntity>,
    onAddTecnico: ()-> Unit
) {
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
                items(tecnicoList) {
                    TecnicoRow(it)
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
private fun TecnicoRow(tecnico: TecnicoEntity) {
    Row(
        Modifier.padding(15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.weight(2f), text = tecnico.tecnicoId.toString())
        Text(
            modifier = Modifier.weight(2f),
            text = tecnico.nombres,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(modifier = Modifier.weight(2f), text = tecnico.sueldo.toString())
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 5.dp))
}


