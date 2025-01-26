package edu.ucne.registrotecnicos.presentation.messages

data class MessageUiState(
    val nombreTecnico: String = "",
    val mensajes: List<MensajeConDatos> = emptyList(),
    val ticketId: Int = 0
)