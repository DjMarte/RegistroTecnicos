package edu.ucne.registrotecnicos.presentation.articulos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.remote.Resource
import edu.ucne.registrotecnicos.data.remote.dto.ArticuloDto
import edu.ucne.registrotecnicos.data.repository.ArticuloRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ArticuloViewModel @Inject constructor(
    private val articuloRepository: ArticuloRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArticuloUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllArticulos()
    }

    fun save(){
        viewModelScope.launch{
            if(isValidate()) {
                articuloRepository.save(_uiState.value.toEntity())
                _uiState.update { it.copy(errorMessage = null) }
                new()  
            }
        }
    }

    fun delete(id: Int){
        viewModelScope.launch {
            articuloRepository.delete(id)
        }
    }

    fun update(){
        viewModelScope.launch {
            articuloRepository.update(
                _uiState.value.articuloId, ArticuloDto(
                    articuloId = _uiState.value.articuloId,
                    descripcion = _uiState.value.descripcion,
                    costo = _uiState.value.costo,
                    ganancia = _uiState.value.ganancia,
                    precio = _uiState.value.precio
                )
            )
        }
    }

    fun find(articuloId: Int){
        viewModelScope.launch {
            if(articuloId > 0){
                val articulo = articuloRepository.find(articuloId)
                if(articulo.articuloId != 0){
                    _uiState.update {
                        it.copy(
                            articuloId = articulo.articuloId,
                            descripcion = articulo.descripcion,
                            costo = articulo.costo,
                            ganancia = articulo.ganancia,
                            precio = articulo.precio
                        )
                    }
                }
            }
        }
    }

    fun new(){
        _uiState.value = ArticuloUiState()
    }

    fun onDescripcionChange(descripcion: String){
        val descripcionRegularExpression = "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9\\s'-]+$".toRegex()
        _uiState.update {
            it.copy(
                descripcion = descripcion,
                errorMessage = if (descripcion.isBlank()) "La descripción no puede estar vacía"
                else if (!descripcion.matches(descripcionRegularExpression)) "La descripción solo puede contener letras y números"
                else null
            )
        }
    }

    private fun calcularPrecio(costo: Double, ganancia: Double): Double {
        val precio = if(costo > 0 && ganancia > 0)
            costo + (costo * (ganancia / 100))
        else
            0.0

        val precioFormateado = String.format(Locale.US, "%.2f", precio)
        return precioFormateado.toDouble()
    }

    fun onCostoChange(costo: Double) {
        val ganancia = _uiState.value.ganancia
        val precioCalculado = calcularPrecio(costo, ganancia)
        _uiState.update {
            it.copy(
                costo = costo, precio = precioCalculado,
                errorMessage = if (costo <= 0) "El costo debe ser mayor a 0"
                else null
            )
        }
    }

    fun onGananciaChange(ganancia: Double) {
        val costo = _uiState.value.costo
        val precioCalculado = calcularPrecio(costo, ganancia)
        _uiState.update {
            it.copy(
                ganancia = ganancia, precio = precioCalculado,
                errorMessage = if (ganancia <= 0 || ganancia > 100) "La ganancia debe ser mayor a 0 o menor a 100"
                else null
            )
        }
    }

    fun getAllArticulos(){
        viewModelScope.launch {
            articuloRepository.getAllArticulos().collectLatest { result ->
                when(result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                listaArticulos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun isValidate(): Boolean {
        val state = uiState.value

        if (state.descripcion.isBlank() || state.costo <= 0 || state.ganancia <= 0) {
            _uiState.update { it.copy(errorMessage = "Todos los campos son requeridos.") }
            return false
        }
        return true
    }
}
fun ArticuloUiState.toEntity() = ArticuloDto(
    articuloId = this.articuloId,
    descripcion = this.descripcion,
    costo = this.costo,
    ganancia = this.ganancia,
    precio = this.precio
)