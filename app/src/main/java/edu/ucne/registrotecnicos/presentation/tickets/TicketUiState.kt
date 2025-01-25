package edu.ucne.registrotecnicos.presentation.tickets

import edu.ucne.registrotecnicos.data.local.entity.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entity.TicketEntity
import java.util.Date

data class TicketUiState(
    val ticketId: Int? = null,
    val fecha: Date = Date(),
    val fechaSeleccionada: Boolean = false,
    val prioridadId: Int? = null,
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val tecnicoId: Int? = null,
    val tecnicoSeleccionado: TecnicoEntity? = null,
    val errorMessage: String? = null,
    val listaTickets: List<TicketEntity> = emptyList()
)