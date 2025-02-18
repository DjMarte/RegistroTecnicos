package edu.ucne.registrotecnicos.data.remote.dto

data class ArticuloDto(
    val articuloId: Int,
    val descripcion: String,
    val costo: Double,
    val ganancia: Double,
    val precio: Double
)