package cl.appescuelamarcelapaz.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.appescuelamarcelapaz.app.navigation.AppNavigation
import cl.appescuelamarcelapaz.app.ui.theme.AppEscuelaMarcelaPazTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppEscuelaMarcelaPazTheme {
                AppNavigation()
            }
        }
    }
}