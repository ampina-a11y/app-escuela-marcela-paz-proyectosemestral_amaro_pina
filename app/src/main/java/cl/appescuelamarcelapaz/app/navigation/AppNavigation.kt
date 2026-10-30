package cl.appescuelamarcelapaz.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.appescuelamarcelapaz.app.domain.model.Rol
import cl.appescuelamarcelapaz.app.ui.home.HomeScreen
import cl.appescuelamarcelapaz.app.ui.login.LoginScreen

sealed class Route(val path: String) {
    object Login : Route("login")
    object Home : Route("home")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Guardamos el rol seleccionado en memoria para pasarlo al Home
    var rolSeleccionado by remember { mutableStateOf(Rol.SOLICITANTE) }

    NavHost(
        navController = navController,
        startDestination = Route.Login.path
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
            HomeScreen(rol = rolSeleccionado)
        }
    }
}