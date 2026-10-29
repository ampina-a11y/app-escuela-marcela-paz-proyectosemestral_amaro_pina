package cl.appescuelamarcelapaz.app.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val items = listOf(
        "Inicio" to Icons.Default.Home,
        "Calendario" to Icons.Default.CalendarMonth,
        "Justificativos" to Icons.Default.Description,
        "Perfil" to Icons.Default.Person
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, (label, icon) ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (selectedIndex) {
                0 -> InicioContent()
                1 -> CalendarioContent()
                2 -> JustificativosContent()
                3 -> PerfilContent()
            }
        }
    }
}

@Composable
private fun InicioContent() {
    Text("Comunicados recientes", modifier = Modifier.padding(16.dp))
}

@Composable
private fun CalendarioContent() {
    Text("Calendario escolar", modifier = Modifier.padding(16.dp))
}

@Composable
private fun JustificativosContent() {
    Text("Enviar justificativo", modifier = Modifier.padding(16.dp))
}

@Composable
private fun PerfilContent() {
    Text("Mi perfil", modifier = Modifier.padding(16.dp))
}

