package cl.appescuelamarcelapaz.app.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.appescuelamarcelapaz.app.data.local.UserPreferences
import cl.appescuelamarcelapaz.app.data.repository.AuthRepository
import cl.appescuelamarcelapaz.app.domain.model.Rol

@Composable
fun LoginScreen(
    onLoginSuccess: (Rol) -> Unit,
    // Inyectamos el repositorio y la fábrica aquí
    viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(
            AuthRepository(UserPreferences(LocalContext.current))
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.loginExitoso) {
        if (uiState.loginExitoso && uiState.rol != null) {
            onLoginSuccess(uiState.rol!!)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Escuela Marcela Paz",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Reserva de Espacios",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            isError = uiState.emailError != null,
            supportingText = {
                uiState.emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            isError = uiState.passwordError != null,
            supportingText = {
                uiState.passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        // --- Selector de rol ---
        Text(
            text = "Selecciona tu rol",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = uiState.rol == Rol.INSPECTORIA,
                        onClick = { viewModel.onRolChange(Rol.INSPECTORIA) }
                    )
                    .padding(end = 16.dp)
            ) {
                RadioButton(
                    selected = uiState.rol == Rol.INSPECTORIA,
                    onClick = { viewModel.onRolChange(Rol.INSPECTORIA) }
                )
                Text("Inspectoría")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = uiState.rol == Rol.SOLICITANTE,
                        onClick = { viewModel.onRolChange(Rol.SOLICITANTE) }
                    )
            ) {
                RadioButton(
                    selected = uiState.rol == Rol.SOLICITANTE,
                    onClick = { viewModel.onRolChange(Rol.SOLICITANTE) }
                )
                Text("Solicitante")
            }
        }
        uiState.rolError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = viewModel::login,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Ingresar")
            }
        }
    }
}