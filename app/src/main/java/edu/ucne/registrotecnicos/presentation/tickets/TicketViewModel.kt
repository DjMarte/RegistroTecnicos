package edu.ucne.registrotecnicos.presentation.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.local.entity.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entity.TicketEntity
import edu.ucne.registrotecnicos.data.repository.TecnicoRepository
import edu.ucne.registrotecnicos.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val tecnicoRepository: TecnicoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    private val _tecnicoList = MutableStateFlow<List<TecnicoEntity>>(emptyList())
    val tecnicoList = _tecnicoList.asStateFlow()

    init {
        getTickets()
        getTecnicos()
    }

    private fun getTecnicos() {
        viewModelScope.launch {
            tecnicoRepository.getAll().collect { listaTecnicos ->
                _tecnicoList.value = listaTecnicos
            }
        }
    }

    private fun getTickets() {
        viewModelScope.launch {
            ticketRepository.getAll().collect { listaTickets ->
                _uiState.update {
                    it.copy(listaTickets = listaTickets)
                }
            }
        }
    }

    fun save() {
        viewModelScope.launch {
            val state = _uiState.value

            if (state.cliente.isBlank() ||
                state.asunto.isBlank() ||
                state.descripcion.isBlank() ||
                state.prioridadId == null ||
                state.tecnicoId == null ||
                state.fecha == Date()
            ) {
                _uiState.update {
                    it.copy(errorMessage = "Todos los campos son obligatorios.")
                }
                return@launch
            } else {
                ticketRepository.save(state.toEntity())
                new()
            }
        }
    }

    fun new() {
        _uiState.value = TicketUiState()
    }

    fun delete() {
        viewModelScope.launch {
            ticketRepository.delete(uiState.value.toEntity())
        }
    }

    fun find(ticketId: Int) {
        viewModelScope.launch {
            if (ticketId > 0) {
                val ticket = ticketRepository.find(ticketId)
                if (ticket != null) {
                    _uiState.update {
                        it.copy(
                            tecnicoId = ticket.tecnicoId,
                            tecnicoSeleccionado = tecnicoList.value.find { it.tecnicoId == ticket.tecnicoId },
                            fecha = ticket.fecha,
                            fechaSeleccionada = true,
                            cliente = ticket.cliente,
                            asunto = ticket.asunto,
                            descripcion = ticket.descripcion,
                            prioridadId = ticket.prioridadId,
                            ticketId = ticket.ticketId
                        )
                    }
                }
            }
        }
    }

    fun onClienteChange(cliente: String) {
        _uiState.update {
            it.copy(
                cliente = cliente
            )
        }
    }

    fun onAsuntoChange(asunto: String) {
        _uiState.update {
            it.copy(
                asunto = asunto
            )
        }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(
                descripcion = descripcion
            )
        }
    }

    fun onPrioridadChange(prioridadId: Int?) {
        _uiState.update {
            it.copy(
                prioridadId = prioridadId
            )
        }
    }

    fun onTecnicoChange(tecnico: TecnicoEntity?) {
        _uiState.update {
            it.copy(
                tecnicoId = tecnico?.tecnicoId,
                tecnicoSeleccionado = tecnico
            )
        }
    }

    fun onFechaChange(fecha: Date) {
        _uiState.update {
            it.copy(
                fecha = fecha,
                fechaSeleccionada = true
            )
        }
    }
}
fun TicketUiState.toEntity() = TicketEntity(
    ticketId = this.ticketId,
    fecha = this.fecha,
    prioridadId = this.prioridadId,
    cliente = this.cliente,
    asunto = this.asunto,
    descripcion = this.descripcion,
    tecnicoId = this.tecnicoId
)