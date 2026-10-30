package cl.appescuelamarcelapaz.app.ui.MisReservas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Un modelo simple para el MVP (sin Room por ahora)
data class ReservaMock(
    val id: Int,
    val espacio: String,
    val fecha: String,
    val estado: String // "Pendiente", "Aceptada", "Rechazada"
)

class MisReservasViewModel : ViewModel() {
    // Simulamos que ya hay una reserva hecha
    private val _reservas = MutableStateFlow(
        listOf(
            ReservaMock(1, "Biblioteca", "2024-06-15", "Pendiente"),
            ReservaMock(2, "Laboratorio de Ciencias", "2024-06-10", "Aceptada")
        )
    )
    val reservas: StateFlow<List<ReservaMock>> = _reservas.asStateFlow()
}