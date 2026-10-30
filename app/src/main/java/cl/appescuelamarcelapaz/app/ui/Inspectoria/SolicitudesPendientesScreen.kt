package cl.appescuelamarcelapaz.app.ui.inspectoria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.appescuelamarcelapaz.app.ui.components.ConfirmDialog

@Composable
fun SolicitudesPendientesScreen(viewModel: SolicitudesPendientesViewModel = viewModel()) {
    val solicitudes by viewModel.solicitudes.collectAsStateWithLifecycle()

    // Estados para manejar los diálogos
    var mostrarDialogoAprobar by remember { mutableStateOf(false) }
    var mostrarDialogoRechazar by remember { mutableStateOf(false) }
    var solicitudSeleccionada by remember { mutableStateOf<SolicitudMock?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Solicitudes de Reserva", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(solicitudes) { solicitud ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(solicitud.espacio, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("Solicitante: ${solicitud.solicitante}")
                        Text("Fecha: ${solicitud.fecha} | ${solicitud.hora}")
                        Text("Actividad: ${solicitud.actividad}")
                        Spacer(modifier = Modifier.height(8.dp))

                        // Chip visual del estado
                        Text(
                            text = "Estado: ${solicitud.estado}",
                            color = when (solicitud.estado) {
                                "Aceptada" -> MaterialTheme.colorScheme.primary
                                "Rechazada" -> MaterialTheme.colorScheme.error
                                else -> MaterialTheme.colorScheme.secondary
                            },
                            fontWeight = FontWeight.Bold
                        )

                        // Si está pendiente, mostramos los botones
                        if (solicitud.estado == "Pendiente") {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = {
                                    solicitudSeleccionada = solicitud
                                    mostrarDialogoAprobar = true
                                }) {
                                    Text("Aprobar")
                                }
                                Button(
                                    onClick = {
                                        solicitudSeleccionada = solicitud
                                        mostrarDialogoRechazar = true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text("Rechazar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo de Aprobar
    if (mostrarDialogoAprobar && solicitudSeleccionada != null) {
        ConfirmDialog(
            title = "Aprobar Reserva",
            message = "¿Estás seguro que deseas aprobar la reserva de ${solicitudSeleccionada?.espacio}?",
            onConfirm = {
                viewModel.actualizarEstado(solicitudSeleccionada!!.id, "Aceptada")
                mostrarDialogoAprobar = false
            },
            onDismiss = { mostrarDialogoAprobar = false }
        )
    }

    // Diálogo de Rechazar (con justificación)
    if (mostrarDialogoRechazar && solicitudSeleccionada != null) {
        var justificacion by remember { mutableStateOf("") }
        var errorJustificacion by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { mostrarDialogoRechazar = false },
            title = { Text("Rechazar Reserva") },
            text = {
                Column {
                    Text("Ingresa la justificación del rechazo:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = justificacion,
                        onValueChange = {
                            justificacion = it
                            errorJustificacion = false
                        },
                        isError = errorJustificacion,
                        supportingText = {
                            if (errorJustificacion) Text("La justificación es obligatoria", color = MaterialTheme.colorScheme.error)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (justificacion.isBlank()) {
                        errorJustificacion = true
                    } else {
                        viewModel.actualizarEstado(solicitudSeleccionada!!.id, "Rechazada")
                        mostrarDialogoRechazar = false
                    }
                }) {
                    Text("Rechazar")
                }
            },
            dismissButton = {
                Button(onClick = { mostrarDialogoRechazar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}