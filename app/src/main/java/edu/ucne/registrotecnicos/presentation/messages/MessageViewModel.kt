package edu.ucne.registrotecnicos.presentation.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.repository.TecnicoRepository
import edu.ucne.registrotecnicos.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val tecnicoRepository: TecnicoRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MessageUiState())
    val uiState = _uiState.asStateFlow()

    fun loadMessages(ticketId: Int) {
        viewModelScope.launch {
            val ticket = ticketRepository.find(ticketId)
            val tecnico = tecnicoRepository.find(ticket?.tecnicoId ?: 0)
            val mensajes = ticketRepository.getMessagesByTicketId(ticketId).first()

            val mensajesConInfo = mensajes.map { mensaje ->
                val formattedDate = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault()).format(mensaje.fecha)
                MensajeConDatos(
                    mensaje = mensaje.mensaje,
                    fechaHora = formattedDate,
                    nombreTecnico = tecnico?.nombres ?: "Pedro"
                )
            }
            _uiState.update {
                it.copy(
                    ticketId = ticketId,
                    nombreTecnico = tecnico?.nombres ?: "Juan",
                    mensajes = mensajesConInfo
                )
            }
        }
    }
    fun sendMessages(respuesta: String) {
        viewModelScope.launch {
            ticketRepository.addMessageToTicket(uiState.value.ticketId, respuesta)
            loadMessages(uiState.value.ticketId)
        }
    }
}