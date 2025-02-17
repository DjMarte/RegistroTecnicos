package edu.ucne.registrotecnicos.data.repository

import android.util.Log
import edu.ucne.registrotecnicos.data.remote.RemoteDataSource
import edu.ucne.registrotecnicos.data.remote.Resource
import edu.ucne.registrotecnicos.data.remote.dto.ArticuloDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ArticuloRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun getAllArticulos(): Flow<Resource<List<ArticuloDto>>> = flow {
        try {
            emit(Resource.Loading())
            val clientes = remoteDataSource.getArticulos()
            emit(Resource.Success(clientes))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            Log.e("ArticuloRepository", "HttpException: $errorMessage")
            emit(Resource.Error("Error de conexion $errorMessage"))
        } catch (e: Exception) {
            Log.e("ArticuloRepository", "Exception: ${e.message}")
            emit(Resource.Error("Error: ${e.message}"))
        }
    }
    suspend fun find(id: Int): ArticuloDto =
        remoteDataSource.getArticuloById(id)

    suspend fun save(articulo: ArticuloDto): ArticuloDto =
        remoteDataSource.saveArticulo(articulo)

    suspend fun update(id: Int, articulo: ArticuloDto) =
        remoteDataSource.updateArticulo(id, articulo)

    suspend fun delete(articuloId: Int) =
        remoteDataSource.deleteArticulo(articuloId)
}