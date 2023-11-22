package com.example.reserveroom

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        val db = Firebase.firestore
      
      val room = Rooms()

        // Criar agendamentos (substitua isso com sua lógica)
        val schedule1 = Schedules("10:00", "123456789", "John Doe", "john@example.com", true)
        val schedule2 = Schedules("14:00", "987654321", "Jane Doe", "jane@example.com", true)

        // Adicionar agendamentos à sala
        room.schedules = listOf(schedule1, schedule2)

        // Adicionar a sala ao Firestore
        room.addRoomToFirestore()

        // Adicionar os agendamentos à sala no Firestore
        room.addSchedulesToRoom()
        println("Agendamentos adicionados à sala com sucesso.")

    }
}