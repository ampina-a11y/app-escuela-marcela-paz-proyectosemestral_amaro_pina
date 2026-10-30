package cl.appescuelamarcelapaz.app.data.repository

import cl.appescuelamarcelapaz.app.data.local.UserPreferences
import cl.appescuelamarcelapaz.app.domain.model.Rol

class AuthRepository(private val userPreferences: UserPreferences) {

    val isLoggedInFlow = userPreferences.isLoggedInFlow
    val userRoleFlow = userPreferences.userRoleFlow

    suspend fun login(email: String, rol: Rol) {

        userPreferences.saveSession(email, rol)
    }

    suspend fun logout() {
        userPreferences.clearSession()
    }
}