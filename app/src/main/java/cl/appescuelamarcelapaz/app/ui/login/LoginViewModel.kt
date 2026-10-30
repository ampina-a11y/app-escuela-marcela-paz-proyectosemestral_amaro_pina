package cl.appescuelamarcelapaz.app.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import cl.appescuelamarcelapaz.app.domain.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val rol: Rol? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val rolError: String? = null,
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

    fun onRolChange(rol: Rol) {
        _uiState.update { it.copy(rol = rol, rolError = null) }
    }

    fun login() {
        val state = _uiState.value
        val emailError = validarEmail(state.email)
        val passwordError = validarPassword(state.password)
        val rolError = validarRol(state.rol)

        if (emailError != null || passwordError != null || rolError != null) {
            _uiState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError,
                    rolError = rolError
                )
            }
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

    private fun validarRol(rol: Rol?): String? =
        if (rol == null) "Debes seleccionar un rol" else null
}