package edu.ucne.registrotecnicos.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Tickets")
data class TicketEntity(
    @PrimaryKey
    val ticketId: Int? = null,
    val fecha: Date = Date(),
    val prioridadId: Int?,
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val tecnicoId: Int?
)