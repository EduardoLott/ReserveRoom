package com.example.reserveroom

class Schedules(
    val time: String,
    val id: String
) {
    val cpf: String = ""
    val name: String = ""
    val email: String = ""
    var available: Boolean = true

    override fun toString(): String {
        return "ID: $id, Time: $time, Available: $available, CPF: $cpf, Name: $name, Email: $email"
    }
}
