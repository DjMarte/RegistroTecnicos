package edu.ucne.registrotecnicos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registrotecnicos.data.local.entity.MensajeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MensajeDao {
    @Upsert()
    suspend fun save(mensaje: MensajeEntity)

    @Query(
        """
            Select * From Mensajes
            Where mensajeId=:id
            Limit 1
        """
    )
    suspend fun find(id: Int): MensajeEntity?

    @Delete()
    suspend fun delete(mensaje: MensajeEntity)

    @Query("Select * From Mensajes")
    fun getAll(): Flow<List<MensajeEntity>>

    @Query("SELECT * FROM Mensajes WHERE ticketId = :ticketId")
    fun getMessagesByTicketId(ticketId: Int): Flow<List<MensajeEntity>>

    @Query("UPDATE Mensajes SET mensaje = :nuevoContenido WHERE mensajeId = :mensajeId")
    suspend fun updateMessageContent(mensajeId: Int, nuevoContenido: String)
}