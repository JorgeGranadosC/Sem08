package com.example.sem08.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sem08.data.LugarDao
import com.example.sem08.model.Lugar

class LugarRepository(private val lugarDao : LugarDao) {

    fun agregarLugar(lugar:Lugar){
        lugarDao.guardarLugares(lugar)
    }
    fun eliminarLugar(lugar: Lugar){
        lugarDao.eliminarLugares(lugar)
    }
    val getLugares: MutableLiveData<List<Lugar>> = lugarDao.getLugares()

}