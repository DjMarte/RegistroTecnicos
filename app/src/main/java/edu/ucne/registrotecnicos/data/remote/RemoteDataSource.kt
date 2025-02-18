package edu.ucne.registrotecnicos.data.remote

import edu.ucne.registrotecnicos.data.remote.dto.ArticuloDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val articuloManagerApi: ArticuloManagerApi
){
    suspend fun getArticulos(): List<ArticuloDto> = articuloManagerApi.getArticulos()

    suspend fun getArticuloById(id: Int) = articuloManagerApi.getArticuloById(id)

    suspend fun saveArticulo(articulo: ArticuloDto): ArticuloDto = articuloManagerApi.saveArticulo(articulo)

    suspend fun updateArticulo(id: Int, articulo: ArticuloDto) = articuloManagerApi.updateArticulo(id, articulo)


    suspend fun deleteArticulo(articuloId: Int) = articuloManagerApi.deleteArticulo(articuloId)


}