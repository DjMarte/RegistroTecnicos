package edu.ucne.registrotecnicos.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrotecnicos.data.local.dao.TicketDao
import edu.ucne.registrotecnicos.data.local.entity.TicketEntity


@Database(
    entities = [
        TicketEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TicketDb:RoomDatabase(){
    abstract fun ticketDao(): TicketDao
}