package com.example.reserveroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class ReserveActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextCpf: EditText
    private lateinit var editTextEmail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reserve_card)

        // Recupere os dados passados pela Intent
        val roomId = intent.getStringExtra("roomId")
        val chosenDate : String= intent.getStringExtra("chosenDate")!!


        // Agora você pode usar roomId e chosenDate conforme necessário
        Log.d("ReserveActivity", "RoomId: $roomId, Chosen Date: $chosenDate")

        FirebaseApp.initializeApp(this)
        val db = Firebase.firestore

        val textRoomTime = findViewById<TextView>(R.id.textRoomTime)
        textRoomTime.setText("Horário da Sala $roomId")

        val textReserveRoom = findViewById<TextView>(R.id.textReserveRoom)
        textReserveRoom.setText("Reserva Sala $roomId")



        // Inicialize os campos de entrada
        editTextName = findViewById(R.id.nameEditText)
        editTextCpf = findViewById(R.id.cpfEditText)
        editTextEmail = findViewById(R.id.emailEditText)

        val buttonReserve = findViewById<Button>(R.id.buttonReserve)
        buttonReserve.setOnClickListener {
            val time = "08:00 - 09:00"
            val name = editTextName.text.toString()
            val cpf = editTextCpf.text.toString()
            val email = editTextEmail.text.toString()

            if (name.isNotEmpty() && cpf.isNotEmpty() && email.isNotEmpty()) {
                val schedule = Schedules(time, name, cpf, email, false, chosenDate)

                // Adiciona a reserva ao Firestore
                db.collection("rooms")
                    .document(roomId.toString())
                    .update("schedules", FieldValue.arrayUnion(schedule.toMap()))
                    .addOnSuccessListener {
                        println("Reserva adicionada com sucesso.")
                    }
                    .addOnFailureListener { e ->
                        println("Erro ao adicionar reserva: $e")
                    }
            }
        }
    }
}
