package edu.ucne.registrotecnicos.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TecnicoList : Screen() // Consulta

    @Serializable
    data class Tecnico(val tecnicoId: Int) : Screen() // Registro

    @Serializable
    data object TicketList : Screen() // Consulta

    @Serializable
    data class Ticket(val ticketId: Int) : Screen()

    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data class TicketMessage(val ticketId: Int) : Screen()

    @Serializable
    data object ArticuloList : Screen()

    @Serializable
    data class Articulo(val articuloId: Int) : Screen()
}