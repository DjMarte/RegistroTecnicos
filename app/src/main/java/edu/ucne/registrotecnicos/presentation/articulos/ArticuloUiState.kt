package edu.ucne.registrotecnicos.presentation.articulos

import edu.ucne.registrotecnicos.data.local.entity.ArticuloEntity

data class ArticuloUiState(
    val articuloId: Int = 0,
    val descripcion: String = "",
    val costo: Double = 0.0,
    val ganancia: Double = 0.0,
    val precio: Double = 0.0,
    val errorMessage: String? = null,
    val listaArticulos: List<ArticuloEntity> = emptyList(),
    val isLoading: Boolean = false
)