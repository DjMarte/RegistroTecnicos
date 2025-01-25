package edu.ucne.registrotecnicos.presentation.tecnicos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.local.entity.TecnicoEntity
import edu.ucne.registrotecnicos.data.repository.TecnicoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TecnicoViewModel @Inject constructor(
    private val tecnicoRepository: TecnicoRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(TecnicoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTecnicos()
    }

    fun save() {
        viewModelScope.launch {
            val nombres = _uiState.value.nombres
            val sueldo = _uiState.value.sueldo

            if (nombres.isBlank() || sueldo.isBlank() || sueldo.toDoubleOrNull() == null) {
                _uiState.update {
                    it.copy(errorMessage = "Campos requeridos o sueldo no valido")
                }
                return@launch
            }
            else{
                tecnicoRepository.saveTecnico(_uiState.value.toEntity())
                new()
            }

        }
    }

    fun find(tecnicoId: Int) {
        viewModelScope.launch {
            if (tecnicoId > 0) {
                val tecnico = tecnicoRepository.find(tecnicoId)
                if (tecnico != null) {
                    _uiState.update {
                        it.copy(
                            tecnicoId = tecnico.tecnicoId,
                            nombres = tecnico.nombres,
                            sueldo = tecnico.sueldo.toString()
                        )
                    }
                }
            }
        }
    }

    fun new() {
        _uiState.value = TecnicoUiState()
    }

    fun delete() {
        viewModelScope.launch {
            tecnicoRepository.delete(uiState.value.toEntity())
        }
    }

    fun onNombresChange(nombres: String) {
        _uiState.update {
            it.copy(
                nombres = nombres
            )
        }
    }

    fun onSueldoChange(sueldo: String) {
        _uiState.update {
            it.copy(
                sueldo = sueldo
            )
        }
    }

    private fun getTecnicos() {
        viewModelScope.launch {
            tecnicoRepository.getAll().collect { listaTecnicos ->
                _uiState.update {
                    it.copy(listaTecnicos = listaTecnicos)
                }
            }
        }
    }
}
fun TecnicoUiState.toEntity() = TecnicoEntity(
    tecnicoId = this.tecnicoId,
    nombres = this.nombres,
    sueldo = this.sueldo.toDouble(),
)