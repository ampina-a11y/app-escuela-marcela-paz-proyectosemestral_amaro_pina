# App Escuela Escritora Marcela Paz

Aplicación móvil Android nativa desarrollada en **Kotlin + Jetpack Compose** para la gestión y reserva de espacios escolares de la Escuela Escritora Marcela Paz (San Bernardo). 

## 👥 Integrantes
* [Nombre completo de Integrante 1]
* [Nombre completo de Integrante 2] (si aplica)

## Contexto del Proyecto
La escuela cuenta con múltiples espacios compartidos (laboratorios, biblioteca, salas de innovación, etc.) que actualmente se reservan de forma verbal, generando duplicidad de reservas y falta de trazabilidad. Esta aplicación MVP (Producto Mínimo Viable) digitaliza este proceso mediante dos perfiles:
* **Solicitante (Docentes, UTP, PIE):** Consulta disponibilidad y solicita reservas.
* **Inspectoría (Administrador):** Revisa, aprueba o rechaza solicitudes (con justificación).

## Funcionalidades Implementadas
* **Login y Roles:** Autenticación con validaciones lógicas desacopladas y selector de perfil.
* **Persistencia Local (DataStore):** La sesión se mantiene activa al cerrar y volver a abrir la app. Incluye cierre de sesión.
* **Navegación Dinámica:** Uso de `Navigation Compose` y `BottomNavigationBar` que cambia según el rol del usuario.
* **Formulario de Reserva Validado:** 
  * Validación de anticipación mínima de 1 día hábil.
  * Validación de choque de horarios (evita reservas duplicadas).
* **Pantalla de Inspectoría:** Listado de solicitudes con diálogos de confirmación para aprobar o rechazar (exigiendo justificación escrita en caso de rechazo).
* **Recursos Nativos (2):**
  * **Notificaciones Locales:** Enviadas al registrar una nueva reserva.
  * **DatePickerDialog:** Selector de fecha nativo de Android integrado en el formulario.
* **Animaciones:** Transiciones suaves entre pantallas (fade) y retroalimentación visual animada (`AnimatedVisibility`) en los mensajes de error del formulario.

## Tecnologías Utilizadas
* **Lenguaje:** Kotlin
* **UI:** Jetpack Compose (Material Design 3)
* **Arquitectura:** MVVM (Model-View-ViewModel)
* **Navegación:** Navigation Compose
* **Persistencia:** DataStore Preferences
* **Asincronía:** Corrutinas y StateFlow
