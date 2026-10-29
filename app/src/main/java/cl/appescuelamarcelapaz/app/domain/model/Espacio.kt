package cl.appescuelamarcelapaz.app.domain.model

data class Espacio(
    val id: Int,
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val ubicacion: String,
    val capacidadMaxima: Int,
    val horarioApertura: String,
    val horarioCierre: String,
    val estado: EstadoEspacio
)