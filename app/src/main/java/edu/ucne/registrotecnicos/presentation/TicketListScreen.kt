package edu.ucne.registrotecnicos.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.registrotecnicos.data.local.entity.TicketEntity
import edu.ucne.registrotecnicos.ui.theme.RegistroTecnicosTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListScreen(
    ticketList: List<TicketEntity>,
    onAddTicket: ()-> Unit,
    goToTicketScreen: (Int) -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Listado de Tickets")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTicket) {
                Icon(Icons.Default.Add, "Agregar ticket")
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
                items(ticketList) {
                    TicketRow(it, goToTicketScreen)
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
            modifier = Modifier.weight(0.2f),
            text = "Id",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier.weight(0.2f),
            text = "Cliente",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier.weight(0.2f),
            text = "Asunto",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier.weight(0.2f),
            text = "Fecha",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier.weight(0.2f),
            text = "Descripción",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier.weight(0.2f),
            text = "PrioridadId",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier.weight(0.2f),
            text = "TecnicoId",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
}

@Composable
private fun TicketRow(
    ticket: TicketEntity,
    goToTicketScreen: (Int) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fechaFormateada = dateFormat.format(ticket.fecha)

    Row(
        Modifier.padding(15.dp)
            .clickable {
                goToTicketScreen((ticket.ticketId)?: 0)
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TicketId
        Text(modifier = Modifier.weight(0.5f), text = ticket.ticketId.toString())
        // Cliente
        Text(
            modifier = Modifier.weight(0.5f),
            text = ticket.cliente,
            style = MaterialTheme.typography.bodyMedium
        )
        // Asunto
        Text(
            modifier = Modifier.weight(0.5f),
            text = ticket.asunto,
            style = MaterialTheme.typography.bodyMedium
        )
        // Fecha
        Text(
            modifier = Modifier.weight(0.5f),
            text = fechaFormateada,
            style = MaterialTheme.typography.bodyMedium
        )
        // Descripción
        Text(
            modifier = Modifier.weight(0.5f),
            text = ticket.descripcion,
            style = MaterialTheme.typography.bodyMedium
        )
        // PrioridadId
        Text(modifier = Modifier.weight(0.5f), text = ticket.prioridadId.toString())

        // TecnicoId
        Text(modifier = Modifier.weight(0.5f), text = ticket.tecnicoId.toString())

    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 5.dp))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MostrarLista(){
    RegistroTecnicosTheme {
        val listaTickets = listOf(
            TicketEntity(
                ticketId = 1,
                fecha = Date() ,
                prioridadId = 1,
                cliente = "Cliente A",
                asunto = "Problema A",
                descripcion = "Descripción A",
                tecnicoId = 101
            ),
            TicketEntity(
                ticketId = 2,
                fecha = Date(),
                prioridadId = 2,
                cliente = "Cliente B",
                asunto = "Problema B",
                descripcion = "Descripción B",
                tecnicoId = 102
            )
        )
    }
}