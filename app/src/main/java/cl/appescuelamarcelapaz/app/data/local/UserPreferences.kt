package cl.appescuelamarcelapaz.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import cl.appescuelamarcelapaz.app.domain.model.Rol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

class UserPreferences(private val context: Context) {

    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ROLE = stringPreferencesKey("user_role")
        private val USER_EMAIL = stringPreferencesKey("user_email")
    }

    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_LOGGED_IN] ?: false
    }

    val userRoleFlow: Flow<Rol> = context.dataStore.data.map { prefs ->
        val rolStr = prefs[USER_ROLE]
        if (rolStr == Rol.INSPECTORIA.name) Rol.INSPECTORIA else Rol.SOLICITANTE
    }

    suspend fun saveSession(email: String, rol: Rol) {
        context.dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = true
            prefs[USER_EMAIL] = email
            prefs[USER_ROLE] = rol.name
        }
    }

    // Cerrar sesión
    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.clear() // Borra todo
        }
    }
}
