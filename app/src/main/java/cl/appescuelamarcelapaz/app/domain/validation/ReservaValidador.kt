package cl.appescuelamarcelapaz.app.domain.validation

import java.time.DayOfWeek
import java.time.LocalDate

object ReservaValidator {

    // Valida que la reserva sea con al menos 1 día hábil de anticipación
    fun validarAnticipacion(fechaReserva: LocalDate, fechaActual: LocalDate = LocalDate.now()): String? {
        var diasHabiles = 0
        var fechaEvaluada = fechaActual


        while (fechaEvaluada.isBefore(fechaReserva)) {
            fechaEvaluada = fechaEvaluada.plusDays(1)
            if (fechaEvaluada.dayOfWeek != DayOfWeek.SATURDAY && fechaEvaluada.dayOfWeek != DayOfWeek.SUNDAY) {
                diasHabiles++
            }
        }

        return if (diasHabiles >= 1) null else "La reserva debe hacerse con al menos 1 día hábil de anticipación"
    }


    fun validarDuplicado(horaInicio: String, horaFin: String, reservasExistentes: List<Pair<String, String>>): String? {
        val inicio = horaInicio.replace(":", "").toIntOrNull() ?: return "Formato de hora inválido"
        val fin = horaFin.replace(":", "").toIntOrNull() ?: return "Formato de hora inválido"

        for ((resInicio, resFin) in reservasExistentes) {
            val rInicio = resInicio.replace(":", "").toIntOrNull() ?: continue
            val rFin = resFin.replace(":", "").toIntOrNull() ?: continue

            // Si el inicio de la nueva es antes del fin de la existente Y el fin de la nueva es después del inicio de la existente
            if (inicio < rFin && fin > rInicio) {
                return "El horario seleccionado se cruza con otra reserva existente"
            }
        }
        return null
    }
}