package edu.ucne.registrotecnicos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registrotecnicos.data.repository.TecnicoRepository
import edu.ucne.registrotecnicos.data.repository.TicketRepository
import edu.ucne.registrotecnicos.presentation.HomeScreen
import edu.ucne.registrotecnicos.presentation.TecnicoListScreen
import edu.ucne.registrotecnicos.presentation.TecnicoScreen
import edu.ucne.registrotecnicos.presentation.TicketListScreen
import edu.ucne.registrotecnicos.presentation.TicketScreen

@Composable
fun AdministracionNavHost(
    navHostController: NavHostController,
    tecnicoRepository: TecnicoRepository,
    ticketRepository: TicketRepository
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val tecnicoList by tecnicoRepository.getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )
    val ticketList by ticketRepository.getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.TecnicoList> {
            TecnicoListScreen(
                tecnicoList = tecnicoList,
                onAddTecnico = {navHostController.navigate(Screen.Tecnico(0))}
            )
        }
        composable<Screen.Tecnico> {
            TecnicoScreen(
                tecnicoRepository = tecnicoRepository,
                goBackToList = {navHostController.navigateUp()}
            )
        }
        composable<Screen.TicketList> {
            TicketListScreen(
                ticketList = ticketList,
                onAddTicket = { navHostController.navigate(Screen.Ticket(0)) },
                goToTicketScreen = { navHostController.navigate(Screen.Ticket(it)) }
            )
        }
        composable<Screen.Ticket> {
            val ticketId = it.toRoute<Screen.Ticket>().ticketId
            TicketScreen(
                ticketRepository = ticketRepository,
                goBackToListScreen = { navHostController.navigateUp() },
                tecnicoList = tecnicoList,
                ticketId = ticketId
            )
        }
        composable<Screen.HomeScreen> {
            HomeScreen(
                goToTecnicoList = { navHostController.navigate(Screen.TecnicoList) },
                goToTicketList = { navHostController.navigate(Screen.TicketList) }
            )
        }
    }
}