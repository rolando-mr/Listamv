package com.example.listamv.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.listamv.model.Contacto

class AgendaViewModel : ViewModel() {

    // Lista observable para Compose
    var contactos = mutableStateListOf<Contacto>()

    // Índice del contacto en edición
    var indexEditar by mutableStateOf<Int?>(null)

    fun agregarContacto(nombre: String, numero: Int) {
        contactos.add(Contacto(nombre, numero))
    }

    fun eliminarContacto(contacto: Contacto) {
        contactos.remove(contacto)
    }

    fun seleccionarContacto(contacto: Contacto) {
        indexEditar = contactos.indexOf(contacto)
    }

    fun actualizarContacto(nombre: String, numero: Int) {
        indexEditar?.let { index ->
            contactos[index] = Contacto(nombre, numero)
            indexEditar = null
        }
    }

    fun cancelarEdicion() {
        indexEditar = null
    }
}