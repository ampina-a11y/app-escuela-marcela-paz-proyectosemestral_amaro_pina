package cl.appescuelamarcelapaz.app.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Reserva(
    val id: Int,
    val espacioId: Int,
    val espacioNombre: String,
    val solicitanteId: String,
    val rolSolicitante: Rol,
    val actividad: String,
    val cursoGrupo: String,
    val cantidadParticipantes: Int,
    val observaciones: String?,
    val fecha: LocalDate,
    val horaInicio: String,
    val horaFin: String,
    val fechaCreacion: LocalDateTime,
    val estado: EstadoReserva,
    val justificacionRechazo: String? = null,
    val responsableInspectoria: String? = null,
    val fechaAprobacionRechazo: LocalDateTime? = null
)