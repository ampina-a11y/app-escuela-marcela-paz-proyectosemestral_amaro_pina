package cl.appescuelamarcelapaz.app.ui.inspectoria

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SolicitudMock(
    val id: Int,
    val solicitante: String,
    val espacio: String,
    val fecha: String,
    val hora: String,
    val actividad: String,
    var estado: String // "Pendiente", "Aceptada", "Rechazada"
)

class SolicitudesPendientesViewModel : ViewModel() {
    private val _solicitudes = MutableStateFlow(
        listOf(
            SolicitudMock(1, "Prof. Juan Pérez", "Laboratorio de Computación", "2024-06-20", "10:00 - 11:30", "Clase de Programación", "Pendiente"),
            SolicitudMock(2, "UTP María Silva", "Biblioteca", "2024-06-21", "14:00 - 15:00", "Reunión de Apoderados", "Pendiente")
        )
    )
    val solicitudes: StateFlow<List<SolicitudMock>> = _solicitudes.asStateFlow()

    fun actualizarEstado(id: Int, nuevoEstado: String) {
        _solicitudes.update { lista ->
            lista.map { solicitud ->
                if (solicitud.id == id) solicitud.copy(estado = nuevoEstado) else solicitud
            }
        }
    }
}