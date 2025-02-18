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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ProductionQuantityLimits
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    goToTecnicoList: () -> Unit,
    goToTicketList: () -> Unit,
    goToArticuloList: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Home")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = goToTecnicoList
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Tecnicos"
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Registro Técnicos")
            }
            OutlinedButton(
                onClick = goToTicketList
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Tickets"
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Registro Tickets")
            }
            OutlinedButton(
                onClick = goToArticuloList
            ) {
                Icon(
                    imageVector = Icons.Default.ProductionQuantityLimits,
                    contentDescription = "Articulos"
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Registro Articulos")
            }
        }
    }
}