package edu.ucne.registrotecnicos.data.repository

import android.util.Log
import edu.ucne.registrotecnicos.data.local.dao.ArticuloDao
import edu.ucne.registrotecnicos.data.local.entity.ArticuloEntity
import edu.ucne.registrotecnicos.data.remote.RemoteDataSource
import edu.ucne.registrotecnicos.data.remote.Resource
import edu.ucne.registrotecnicos.data.remote.dto.ArticuloDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ArticuloRepository @Inject constructor(
    private val articuloDao: ArticuloDao,
    private val remoteDataSource: RemoteDataSource
){
    fun getAllArticulos(): Flow<Resource<List<ArticuloEntity>>> = flow {
        emit(Resource.Loading())

        try {
            val articuloRemoto = remoteDataSource.getArticulos()

            val listaArticulo = articuloRemoto.map { dto ->
                ArticuloEntity(
                    articuloId = dto.articuloId,
                    descripcion = dto.descripcion,
                    costo = dto.costo,
                    ganancia = dto.ganancia,
                    precio = dto.precio
                )
            }
            articuloDao.save(listaArticulo)

            emit(Resource.Success(listaArticulo))

        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            Log.e("ArticuloRepository", "HttpException: $errorMessage")
            emit(Resource.Error("Error de conexión: $errorMessage"))
        } catch (e: Exception) {
            Log.e("ArticuloRepository", "Error al obtener datos remotos: ${e.message}")

            val localArticulos = articuloDao.getAll().first()

            if (localArticulos.isNotEmpty())
                emit(Resource.Success(localArticulos))
            else
                emit(Resource.Error("Error de conexión: ${e.message}"))
        }
    }

    suspend fun find(id: Int): ArticuloDto =
        remoteDataSource.getArticuloById(id)

    suspend fun save(articulo: ArticuloDto): ArticuloDto = remoteDataSource.saveArticulo(articulo)

    suspend fun update(id: Int, articulo: ArticuloDto) =
        remoteDataSource.updateArticulo(id, articulo)

    suspend fun delete(articuloId: Int) =
        remoteDataSource.deleteArticulo(articuloId)
}