package cl.appescuelamarcelapaz.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.appescuelamarcelapaz.app.ui.home.HomeScreen
import cl.appescuelamarcelapaz.app.ui.login.LoginScreen

sealed class Route(val path: String) {
    object Login : Route("login")
    object Home : Route("home")
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
                onLoginSuccess = { rol ->
                    navController.navigate(Route.Home.path)
                }
            )
        }
        composable(Route.Home.path) {
            HomeScreen()
        }
    }
}