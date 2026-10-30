package cl.appescuelamarcelapaz.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.appescuelamarcelapaz.app.data.repository.AuthRepository

class LoginViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}