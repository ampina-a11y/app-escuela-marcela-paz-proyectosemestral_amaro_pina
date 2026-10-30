package cl.appescuelamarcelapaz.app.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Room
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import cl.appescuelamarcelapaz.app.domain.model.Rol

// Estructura para definir las pestañas dinámicamente
data class TabItem(
    val label: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(rol: Rol) {
    // Definimos las pestañas según el rol
    val tabs = if (rol == Rol.INSPECTORIA) {
        listOf(
            TabItem("Solicitudes", Icons.Default.List),
            TabItem("Espacios", Icons.Default.Room),
            TabItem("Perfil", Icons.Default.Person)
        )
    } else {
        listOf(
            TabItem("Catálogo", Icons.Default.Home),
            TabItem("Mis Reservas", Icons.Default.CalendarMonth),
            TabItem("Perfil", Icons.Default.Person)
        )
    }

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Aquí iremos conectando las pantallas reales
            if (rol == Rol.INSPECTORIA) {
                when (selectedIndex) {
                    0 -> PlaceholderContent("Solicitudes Pendientes (Inspectoría)")
                    1 -> PlaceholderContent("Gestión de Espacios")
                    2 -> PlaceholderContent("Perfil de Inspectoría")
                }
            } else {
                when (selectedIndex) {
                    0 -> PlaceholderContent("Catálogo de Espacios")
                    1 -> PlaceholderContent("Mis Reservas")
                    2 -> PlaceholderContent("Perfil de Solicitante")
                }
            }
        }
    }
}

// Un componente temporal para no dejar la pantalla en blanco
@Composable
private fun PlaceholderContent(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text(text = text)
    }
}
