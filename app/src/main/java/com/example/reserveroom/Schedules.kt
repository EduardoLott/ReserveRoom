package com.example.reserveroom

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Schedules(
    var time: String = "",
    var cpf: String = "",
    var name: String = "",
    var email: String = "",
    var available: Boolean = true
) {

    // Construtor vazio necessário para usar o Firebase
    constructor() : this("", "", "", "", true)

    // Método para converter para mapa antes de enviar para o Firebase
    fun toMap(): Map<String, Any?> {
        val result = HashMap<String, Any?>()
        result["time"] = time
        result["cpf"] = cpf
        result["name"] = name
        result["email"] = email
        result["available"] = available
        return result
    }
}