package com.example.reserveroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore

class ReserveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reserve_card)

        // Recupere os dados passados pela Intent
        val roomId = intent.getStringExtra("roomId")
        val chosenDate = intent.getStringExtra("chosenDate")

        // Agora você pode usar roomId e chosenDate conforme necessário
        Log.d("ReserveActivity", "RoomId: $roomId, Chosen Date: $chosenDate")

        FirebaseApp.initializeApp(this)
        val db = Firebase.firestore

        val buttonReserve = findViewById<Button>(R.id.buttonReserve)
        buttonReserve.setOnClickListener {
            // Você pode adicionar lógica para reservar a sala usando roomId e chosenDate aqui
            // Exemplo: db.collection("rooms").document(roomId).collection("schedules").add(...)

            // Neste exemplo, estou voltando para a MainActivity, mas você pode ajustar conforme necessário
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
