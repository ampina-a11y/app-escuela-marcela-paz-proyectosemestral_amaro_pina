package cl.appescuelamarcelapaz.app.ui.Reserva

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.appescuelamarcelapaz.app.domain.validation.ReservaValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

data class NuevaReservaUiState(
    val actividad: String = "",
    val cursoGrupo: String = "",
    val cantidadParticipantes: String = "",
    val fecha: String = "", // Formato YYYY-MM-DD
    val horaInicio: String = "", // Formato HH:mm
    val horaFin: String = "",    // Formato HH:mm
    val observaciones: String = "",

    val actividadError: String? = null,
    val cursoError: String? = null,
    val cantidadError: String? = null,
    val fechaError: String? = null,
    val horaError: String? = null,

    val isLoading: Boolean = false,
    val reservaExitosa: Boolean = false
)

class NuevaReservaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NuevaReservaUiState())
    val uiState: StateFlow<NuevaReservaUiState> = _uiState.asStateFlow()

    fun onActividadChange(v: String) = _uiState.update { it.copy(actividad = v, actividadError = null) }
    fun onCursoChange(v: String) = _uiState.update { it.copy(cursoGrupo = v, cursoError = null) }
    fun onCantidadChange(v: String) = _uiState.update { it.copy(cantidadParticipantes = v, cantidadError = null) }
    fun onFechaChange(v: String) = _uiState.update { it.copy(fecha = v, fechaError = null) }
    fun onHoraInicioChange(v: String) = _uiState.update { it.copy(horaInicio = v, horaError = null) }
    fun onHoraFinChange(v: String) = _uiState.update { it.copy(horaFin = v, horaError = null) }
    fun onObservacionesChange(v: String) = _uiState.update { it.copy(observaciones = v) }

    fun guardarReserva() {
        val state = _uiState.value

        val actividadError = if (state.actividad.isBlank()) "La actividad es obligatoria" else null
        val cursoError = if (state.cursoGrupo.isBlank()) "El curso o grupo es obligatorio" else null
        val cantidadError = if (state.cantidadParticipantes.toIntOrNull() == null || state.cantidadParticipantes.toInt() <= 0) "Ingrese una cantidad válida" else null

        // Validar fecha
        var fechaError: String? = null
        var fechaParseada: LocalDate? = null
        try {
            fechaParseada = LocalDate.parse(state.fecha)
            fechaError = ReservaValidator.validarAnticipacion(fechaParseada)
        } catch (e: Exception) {
            fechaError = "Formato de fecha inválido (Use YYYY-MM-DD)"
        }

        // Validar horas
        val horaError = when {
            state.horaInicio.isBlank() || state.horaFin.isBlank() -> "Debe ingresar hora de inicio y fin"
            state.horaInicio >= state.horaFin -> "La hora de fin debe ser mayor a la de inicio"
            else -> ReservaValidator.validarDuplicado(state.horaInicio, state.horaFin, emptyList()) // Lista vacía por ahora
        }

        if (actividadError != null || cursoError != null || cantidadError != null || fechaError != null || horaError != null) {
            _uiState.update {
                it.copy(
                    actividadError = actividadError,
                    cursoError = cursoError,
                    cantidadError = cantidadError,
                    fechaError = fechaError,
                    horaError = horaError
                )
            }
            return
        }

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            // Aquí simulamos guardado y éxito
            _uiState.update { it.copy(isLoading = false, reservaExitosa = true) }
        }
    }
}