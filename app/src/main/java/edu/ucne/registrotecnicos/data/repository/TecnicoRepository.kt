package edu.ucne.registrotecnicos.data.repository

import edu.ucne.registrotecnicos.data.local.dao.TecnicoDao
import edu.ucne.registrotecnicos.data.local.database.AdministracionDb
import edu.ucne.registrotecnicos.data.local.entity.TecnicoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TecnicoRepository @Inject constructor(
    private val tecnicoDb: TecnicoDao
) {
    suspend fun saveTecnico(tecnico: TecnicoEntity) = tecnicoDb.save(tecnico)

    suspend fun find(id: Int): TecnicoEntity? =  tecnicoDb.find(id)

    suspend fun delete(tecnico: TecnicoEntity) =  tecnicoDb.delete(tecnico)

    fun getAll(): Flow<List<TecnicoEntity>> = tecnicoDb.getAll()
}