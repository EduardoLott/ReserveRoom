package com.example.reserveroom

import com.google.firebase.firestore.IgnoreExtraProperties
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@IgnoreExtraProperties
data class Schedules(
    var time: String = "",
    var cpf: String = "",
    var name: String = "",
    var email: String = "",
    var available: Boolean = true,
    var date: LocalDate
) {

    // Construtor vazio necessário para usar o Firebase
    constructor() : this("", "", "", "", true, LocalDate.now())

    // Método para converter para mapa antes de enviar para o Firebase
    fun toMap(): Map<String, Any?> {
        val result = HashMap<String, Any?>()
        result["time"] = time
        result["cpf"] = cpf
        result["name"] = name
        result["email"] = email
        result["available"] = available
        result["date"] = date.format(DateTimeFormatter.ISO_DATE)
        return result
    }
}