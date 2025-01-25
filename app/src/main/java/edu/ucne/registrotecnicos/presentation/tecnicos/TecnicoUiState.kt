package edu.ucne.registrotecnicos.presentation.tecnicos

import edu.ucne.registrotecnicos.data.local.entity.TecnicoEntity

data class TecnicoUiState(
    val tecnicoId: Int? = null,
    val nombres: String = "",
    val sueldo: String = "",
    val errorMessage: String? = null,
    val listaTecnicos: List<TecnicoEntity> = emptyList()
)