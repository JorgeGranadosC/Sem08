package com.example.sem08.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sem08.model.Lugar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.DocumentReference

class LugarDao {
    //Firebase vars
    private var firestore: FirebaseFirestore
    private var codigoUsuario: String
    init {
        codigoUsuario = Firebase.auth.currentUser?.email.toString()
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    fun getLugares(): MutableLiveData<List<Lugar>> {
        val listaPropiedades = MutableLiveData<List<Lugar>>()
        firestore
            .collection("lugares")
            .document(codigoUsuario)
            .collection("misLugares")
            .addSnapshotListener{snapshot, e ->
                if(e != null){
                    return@addSnapshotListener
                }
                if(snapshot != null){
                    val lista = ArrayList<Lugar>()
                    val propiedades = snapshot.documents
                    propiedades.forEach{
                        val propiedad = it.toObject(Lugar::class.java)
                        if(propiedad != null){
                            lista.add(propiedad)
                        }
                    }
                    listaPropiedades.value = lista
                }
            }
        return listaPropiedades
    }
    fun guardarLugares(lugar : Lugar){
        val document: DocumentReference
        if(lugar.id.isEmpty()){
            //Agregar
            document = firestore
                .collection("lugares")
                .document(codigoUsuario)
                .collection("misLugares")
                .document()
            lugar.id = document.id
        }else{
            //Modificar
            document = firestore
                .collection("lugares")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
        }
        document.set(lugar)
            .addOnCompleteListener{
                Log.d("guardarLugar", "Guardado")
            }
            .addOnCompleteListener{
                Log.d("guardarLugar", "Error al guardar")
            }
    }

    fun eliminarLugares(lugar: Lugar){
        if(lugar.id.isNotEmpty()){
            firestore
                .collection("lugares")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
                .delete()
                .addOnCompleteListener {
                    Log.d("eliminarLugar", "Eliminada con exito")
                }
                .addOnCompleteListener {
                    Log.d("guardarLugar", "Error al eliminar")
                }
        }
    }
}