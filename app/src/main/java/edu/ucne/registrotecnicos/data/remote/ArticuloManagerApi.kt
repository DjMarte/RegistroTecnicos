package edu.ucne.registrotecnicos.data.remote

import edu.ucne.registrotecnicos.data.remote.dto.ArticuloDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ArticuloManagerApi {
    @GET("api/Articulos")
    suspend fun getArticulos(): List<ArticuloDto>

    @GET("api/Articulos/{articuloId}")
    suspend fun getArticuloById(@Path("articuloId") articuloId: Int): ArticuloDto

    @POST("api/Articulos")
    suspend fun saveArticulo(@Body articulo: ArticuloDto?): ArticuloDto

    @PUT("api/Articulos/{articuloId}")
    suspend fun updateArticulo(
        @Path("articuloId") articuloId: Int,
        @Body articulo: ArticuloDto
    ): Response<ArticuloDto>

    @DELETE("api/Articulos/{articuloId}")
    suspend fun deleteArticulo(@Path("articuloId") articuloId: Int): Response<Unit>
}