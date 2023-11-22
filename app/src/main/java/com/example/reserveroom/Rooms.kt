package com.example.reserveroom

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Rooms(
    var roomId: String? = null,
    var schedules: List<Schedules> = emptyList()
) {

    private var db = FirebaseFirestore.getInstance()

    constructor() : this(null, emptyList())

    // Método para converter para mapa antes de enviar para o Firestore
    @Exclude
    fun toMap(): Map<String, Any?> {
        val result = HashMap<String, Any?>()
        result["roomId"] = roomId
        result["schedules"] = schedules.map { it.toMap() }
        return result
    }

    fun addRoomToFirestore() {
        // Obtém a instância do Firestore
        val db = FirebaseFirestore.getInstance()

        // Adiciona o objeto Room ao Firestore
        db.collection("rooms").add(toMap())
            .addOnSuccessListener { documentReference ->
                // Atualiza o ID com o ID gerado pelo Firestore
                this.roomId = documentReference.id
            }
            .addOnFailureListener { e ->
                // Tratamento de falha, se necessário
                println("Erro ao adicionar sala: $e")
            }
    }

    fun addSchedulesToRoom() {
        // Certifique-se de que a sala tem um ID antes de adicionar agendamentos
        if (roomId == null) {
            println("Erro: A sala deve ter um ID antes de adicionar agendamentos.")
            return
        }

        // Obtém a referência da sala no Firestore
        val roomRef = db.collection("rooms").document(roomId!!)

        // Adiciona os agendamentos à sala
        roomRef.update("schedules", schedules.map { it.toMap() })
            .addOnSuccessListener {
                println("Agendamentos adicionados à sala com sucesso.")
            }
            .addOnFailureListener { e ->
                println("Erro ao adicionar agendamentos à sala: $e")
            }
    }
}
