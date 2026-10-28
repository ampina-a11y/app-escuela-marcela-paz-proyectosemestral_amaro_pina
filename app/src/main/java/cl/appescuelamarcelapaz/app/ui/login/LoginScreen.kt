package cl.appescuelamarcelapaz.app.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val loginExitoso: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null) }
    }

    fun login() {
        val state = _uiState.value
        val emailError = validarEmail(state.email)
        val passwordError = validarPassword(state.password)

        if (emailError != null || passwordError != null) {
            _uiState.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return
        }

        _uiState.update { it.copy(isLoading = true, loginExitoso = true) }
    }

    private fun validarEmail(email: String): String? = when {
        email.isBlank() -> "El correo es obligatorio"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Formato de correo inválido"
        else -> null
    }

    private fun validarPassword(password: String): String? = when {
        password.isBlank() -> "La contraseña es obligatoria"
        password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
        else -> null
    }
}