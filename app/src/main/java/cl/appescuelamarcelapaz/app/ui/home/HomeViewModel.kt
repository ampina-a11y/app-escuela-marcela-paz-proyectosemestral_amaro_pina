package cl.appescuelamarcelapaz.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.appescuelamarcelapaz.app.data.repository.AuthRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun cerrarSesion(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLogoutSuccess()
        }
    }
}