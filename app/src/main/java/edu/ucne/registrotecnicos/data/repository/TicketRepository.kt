package edu.ucne.registrotecnicos.data.repository

import edu.ucne.registrotecnicos.data.local.dao.MensajeDao
import edu.ucne.registrotecnicos.data.local.dao.TicketDao
import edu.ucne.registrotecnicos.data.local.database.AdministracionDb
import edu.ucne.registrotecnicos.data.local.entity.MensajeEntity
import edu.ucne.registrotecnicos.data.local.entity.TicketEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketDb: TicketDao,
    private val mensajeDb: MensajeDao
) {
    suspend fun save(ticket: TicketEntity) = ticketDb.save(ticket)

    suspend fun find(id: Int): TicketEntity? = ticketDb.find(id)

    suspend fun delete(ticket: TicketEntity) =  ticketDb.delete(ticket)

    fun getAll(): Flow<List<TicketEntity>> = ticketDb.getAll()

    suspend fun addMessageToTicket(ticketId: Int, contenido: String) {
        val mensaje = MensajeEntity(ticketId = ticketId, mensaje = contenido)
        mensajeDb.save(mensaje)
    }

    fun getMessagesByTicketId(ticketId: Int): Flow<List<MensajeEntity>> {
        return mensajeDb.getMessagesByTicketId(ticketId)
    }
}