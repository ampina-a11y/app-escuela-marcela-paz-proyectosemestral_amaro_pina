package cl.appescuelamarcelapaz.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.appescuelamarcelapaz.app.data.local.UserPreferences
import cl.appescuelamarcelapaz.app.data.repository.AuthRepository
import cl.appescuelamarcelapaz.app.domain.model.Rol
import cl.appescuelamarcelapaz.app.ui.Reserva.NuevaReservaScreen
import cl.appescuelamarcelapaz.app.ui.home.HomeScreen
import cl.appescuelamarcelapaz.app.ui.login.LoginScreen
import kotlinx.coroutines.flow.first

sealed class Route(val path: String) {
    object Login : Route("login")
    object Home : Route("home")
    object NuevaReserva : Route("nueva_reserva")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val authRepository = remember { AuthRepository(UserPreferences(context)) }

    var rolSeleccionado by remember { mutableStateOf(Rol.SOLICITANTE) }
    var isLoading by remember { mutableStateOf(true) }
    var startRoute by remember { mutableStateOf(Route.Login.path) }

    LaunchedEffect(Unit) {
        val isLoggedIn = authRepository.isLoggedInFlow.first()
        if (isLoggedIn) {
            rolSeleccionado = authRepository.userRoleFlow.first()
            startRoute = Route.Home.path
        }
        isLoading = false
    }

    if (!isLoading) {
        NavHost(
            navController = navController,
            startDestination = startRoute
        ) {
            composable(Route.Login.path) {
                LoginScreen(
                    onLoginSuccess = { rol ->
                        rolSeleccionado = rol
                        navController.navigate(Route.Home.path) {
                            popUpTo(Route.Login.path) { inclusive = true }
                        }
                    }
                )
            }

            composable(Route.Home.path) {
                HomeScreen(
                    rol = rolSeleccionado,
                    onNavigateToReserva = { navController.navigate(Route.NuevaReserva.path) },
                    onLogout = {
                        navController.navigate(Route.Login.path) {
                            // Borra todo el historial para que no pueda volver atrás
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            composable(Route.NuevaReserva.path) {
                NuevaReservaScreen(
                    onReservaCreada = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}