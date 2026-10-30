package cl.appescuelamarcelapaz.app.ui.Reserva

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.appescuelamarcelapaz.app.data.local.NotificationHelper
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaReservaScreen(
    onReservaCreada: () -> Unit,
    viewModel: NuevaReservaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val notificationHelper = remember { NotificationHelper(context) }

    // --- Lógica del DatePicker ---
    var mostrarDatePicker by remember { mutableStateOf(false) }
    val calendario = Calendar.getInstance()

    if (mostrarDatePicker) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Formateamos a YYYY-MM-DD (el mes en Calendar empieza en 0)
                val mesFormateado = String.format("%02d", month + 1)
                val diaFormateado = String.format("%02d", dayOfMonth)
                viewModel.onFechaChange("$year-$mesFormateado-$diaFormateado")
                mostrarDatePicker = false
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).apply {
            // Opcional: evitar que seleccionen fechas pasadas en el calendario
            datePicker.minDate = System.currentTimeMillis() - 1000
        }.show()
    }

    LaunchedEffect(uiState.reservaExitosa) {
        if (uiState.reservaExitosa) {
            notificationHelper.crearCanalNotificacion()
            notificationHelper.mostrarNotificacion(
                titulo = "Solicitud Enviada",
                mensaje = "Tu reserva ha sido registrada. Esperando aprobación de Inspectoría."
            )
            onReservaCreada()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Nueva Solicitud de Reserva",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = uiState.actividad,
            onValueChange = viewModel::onActividadChange,
            label = { Text("Actividad a realizar") },
            isError = uiState.actividadError != null,
            supportingText = { uiState.actividadError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.cursoGrupo,
            onValueChange = viewModel::onCursoChange,
            label = { Text("Curso o grupo") },
            isError = uiState.cursoError != null,
            supportingText = { uiState.cursoError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.cantidadParticipantes,
            onValueChange = viewModel::onCantidadChange,
            label = { Text("Cantidad de asistentes") },
            leadingIcon = { Icon(Icons.Default.People, contentDescription = null) },
            isError = uiState.cantidadError != null,
            supportingText = { uiState.cantidadError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // --- CAMPO DE FECHA MODIFICADO CON EL NATIVO ---
        OutlinedTextField(
            value = uiState.fecha,
            onValueChange = { },
            readOnly = true, // Evita que el teclado se abra
            label = { Text("Fecha (Seleccionar)") },
            leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
            isError = uiState.fechaError != null,
            supportingText = { uiState.fechaError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { mostrarDatePicker = true } // Abre el calendario al tocar
        )

        Spacer(Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = uiState.horaInicio,
                onValueChange = viewModel::onHoraInicioChange,
                label = { Text("Inicio (HH:mm)") },
                leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                isError = uiState.horaError != null,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = uiState.horaFin,
                onValueChange = viewModel::onHoraFinChange,
                label = { Text("Fin (HH:mm)") },
                isError = uiState.horaError != null,
                modifier = Modifier.weight(1f)
            )
        }
        uiState.horaError?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = viewModel::guardarReserva,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Enviar Solicitud")
            }
        }
    }
}