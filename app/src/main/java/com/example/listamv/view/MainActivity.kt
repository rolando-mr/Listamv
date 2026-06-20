package com.example.listamv.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listamv.model.Contacto
import com.example.listamv.viewmodel.AgendaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                    Agendaview()
            }
        }
}


@Composable
fun Agendaview(viewModel: AgendaViewModel = viewModel()) {
    var nombre by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = numero,
            onValueChange = { numero = it },
            label = { Text("Número") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val num = numero.toIntOrNull()
                if (nombre.isNotBlank() && num != null) {
                    if (viewModel.indexEditar == null) {
                        viewModel.agregarContacto(nombre, num)
                    } else {
                        viewModel.actualizarContacto(nombre, num)
                    }
                    nombre = ""
                    numero = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (viewModel.indexEditar == null)
                    "Guardar contacto"
                else
                    "Actualizar contacto"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(viewModel.contactos) { contacto ->
                ContactoItem(
                    contacto = contacto,
                    onEditar = {
                        nombre = contacto.nombre
                        numero = contacto.numero.toString()
                        viewModel.seleccionarContacto(contacto)
                    },
                    onEliminar = {
                        viewModel.eliminarContacto(contacto)
                        nombre = ""
                        numero = ""
                    }
                )
            }
        }
    }
}

@Composable
fun ContactoItem(
    contacto: Contacto,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = contacto.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = contacto.numero.toString())
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onEditar) {
                    Text("Editar")
                }
                Button(onClick = onEliminar) {
                    Text("Eliminar")
                }
            }
        }
    }
}
