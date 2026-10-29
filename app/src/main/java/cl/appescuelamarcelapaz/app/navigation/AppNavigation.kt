package cl.appescuelamarcelapaz.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.appescuelamarcelapaz.app.ui.home.HomeScreen
import cl.appescuelamarcelapaz.app.ui.login.LoginScreen

sealed class Route(val path: String) {
    data object Login : Route("login")
    data object Home : Route("home")
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Login.path
    ) {
        composable(Route.Login.path) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                }
            )
        }
        composable(Route.Home.path) {
            HomeScreen()
        }
    }
}