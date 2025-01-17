package edu.ucne.registrotecnicos.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TecnicoList : Screen() // Consulta

    @Serializable
    data class Tecnico(val tecnicoId: Int) : Screen() // Registro
}