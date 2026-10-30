package cl.appescuelamarcelapaz.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Room
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.appescuelamarcelapaz.app.data.local.UserPreferences
import cl.appescuelamarcelapaz.app.data.repository.AuthRepository
import cl.appescuelamarcelapaz.app.domain.model.Rol

data class TabItem(
    val label: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(
    rol: Rol,
    onNavigateToReserva: () -> Unit,
    onLogout: () -> Unit, // <-- Nueva acción
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            AuthRepository(UserPreferences(LocalContext.current))
        )
    )
) {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (rol == Rol.INSPECTORIA) {
                when (selectedIndex) {
                    0 -> ContentInspectoriaSolicitudes()
                    1 -> PlaceholderContent("Gestión de Espacios")
                    2 -> PerfilContent(onLogoutClick = { viewModel.cerrarSesion(onLogout) })
                }
            } else {
                when (selectedIndex) {
                    0 -> ContentSolicitanteCatalogo(onNavigateToReserva)
                    1 -> PlaceholderContent("Mis Reservas")
                    2 -> PerfilContent(onLogoutClick = { viewModel.cerrarSesion(onLogout) })
                }
            }
        }
    }
}

@Composable
private fun PerfilContent(onLogoutClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Mi Perfil")
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onLogoutClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Cerrar Sesión", color = MaterialTheme.colorScheme.onError)
        }
    }
}

@Composable
private fun ContentSolicitanteCatalogo(onNavigateToReserva: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Catálogo de Espacios")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToReserva) {
            Text("Reservar un espacio")
        }
    }
}

@Composable
private fun ContentInspectoriaSolicitudes() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Solicitudes Pendientes")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Aquí aparecerán las reservas para aprobar/rechazar")
    }
}

@Composable
private fun PlaceholderContent(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}