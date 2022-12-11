package com.example.sem08.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.sem08.data.LugarDao
import com.example.sem08.repository.LugarRepository
import com.example.sem08.model.Lugar
import kotlinx.coroutines.launch


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val obtenerLugares: MutableLiveData<List<Lugar>>
    private val repository: LugarRepository = LugarRepository(LugarDao())

    init {
        obtenerLugares = repository.getLugares
    }

    fun guardarLugar(lugar: Lugar){
        viewModelScope.launch { repository.agregarLugar(lugar)}
    }

    fun eliminarLugar(lugar: Lugar){
    viewModelScope.launch { repository.eliminarLugar(lugar)}
    }
}