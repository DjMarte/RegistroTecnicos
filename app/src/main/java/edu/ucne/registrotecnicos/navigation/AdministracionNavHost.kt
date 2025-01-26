package edu.ucne.registrotecnicos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registrotecnicos.presentation.HomeScreen
import edu.ucne.registrotecnicos.presentation.messages.MessageScreen
import edu.ucne.registrotecnicos.presentation.tecnicos.TecnicoListScreen
import edu.ucne.registrotecnicos.presentation.tecnicos.TecnicoScreen
import edu.ucne.registrotecnicos.presentation.tickets.TicketListScreen
import edu.ucne.registrotecnicos.presentation.tickets.TicketScreen

@Composable
fun AdministracionNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.TecnicoList> {
            TecnicoListScreen(
                onAddTecnico = {navHostController.navigate(Screen.Tecnico(0))},
                goToTecnicoScreen = { tecnicoId ->
                    navHostController.navigate(Screen.Tecnico(tecnicoId = tecnicoId))
                }
            )
        }
        composable<Screen.Tecnico> {
            val tecnicoId = it.toRoute<Screen.Tecnico>().tecnicoId
            TecnicoScreen(
                tecnicoId = tecnicoId,
                goBackToList = {navHostController.navigateUp()}
            )
        }
        composable<Screen.TicketList> {
            TicketListScreen(
                onAddTicket = {navHostController.navigate(Screen.Ticket(0))},
                goToTicketScreen = { ticketId ->
                    navHostController.navigate(Screen.Ticket(ticketId = ticketId))
                }
            )
        }
        composable<Screen.Ticket> {
            val ticketId = it.toRoute<Screen.Ticket>().ticketId
            TicketScreen(
                ticketId = ticketId,
                goBackToListScreen = { navHostController.navigateUp() },
                goToMessageScreen = { navHostController.navigate(Screen.TicketMessage(ticketId)) }
            )
        }
        composable<Screen.HomeScreen> {
            HomeScreen(
                goToTecnicoList = { navHostController.navigate(Screen.TecnicoList) },
                goToTicketList = { navHostController.navigate(Screen.TicketList) }
            )
        }
        composable<Screen.TicketMessage> {
            val ticketId = it.toRoute<Screen.TicketMessage>().ticketId
            MessageScreen(
                ticketId = ticketId,
                goBackToTicketScreen = { navHostController.navigateUp() }
            )
        }
    }
}