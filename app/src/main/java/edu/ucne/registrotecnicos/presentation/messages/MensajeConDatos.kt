package edu.ucne.registrotecnicos.presentation.messages

data class MensajeConDatos(
    val mensaje: String,
    val fechaHora: String,
    val nombreTecnico: String
)