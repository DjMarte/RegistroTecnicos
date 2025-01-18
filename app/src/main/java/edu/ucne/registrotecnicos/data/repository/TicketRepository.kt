package edu.ucne.registrotecnicos.data.repository

import edu.ucne.registrotecnicos.data.local.database.TicketDb
import edu.ucne.registrotecnicos.data.local.entity.TicketEntity
import kotlinx.coroutines.flow.Flow

class TicketRepository(
    private val ticketDb: TicketDb
) {
    suspend fun save(ticket: TicketEntity){
        ticketDb.ticketDao().save(ticket)
    }
    suspend fun find(id: Int): TicketEntity?{
        return ticketDb.ticketDao().find(id)
    }
    suspend fun delete(ticket: TicketEntity){
        return ticketDb.ticketDao().delete(ticket)
    }
    fun getAll(): Flow<List<TicketEntity>>{
        return ticketDb.ticketDao().getAll()
    }
}