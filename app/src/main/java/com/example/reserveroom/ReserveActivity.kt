package com.example.reserveroom

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
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

        val textTime1 = findViewById<TextView>(R.id.textTime1)
        val textTime2 = findViewById<TextView>(R.id.textTime2)
        val textTime3 = findViewById<TextView>(R.id.textTime3)
        val textTime4 = findViewById<TextView>(R.id.textTime4)
        val textTime5 = findViewById<TextView>(R.id.textTime5)
        val textTime6 = findViewById<TextView>(R.id.textTime6)
        val textTime7 = findViewById<TextView>(R.id.textTime7)
        val textTime8 = findViewById<TextView>(R.id.textTime8)
        val textTime9 = findViewById<TextView>(R.id.textTime9)
        val textTime10 = findViewById<TextView>(R.id.textTime10)

        var textViewSelecionado: TextView? = null

        val onClickListener = View.OnClickListener { view ->

            textViewSelecionado?.setTextColor(Color.parseColor("#009E1A"))
            textViewSelecionado?.setBackgroundColor(Color.WHITE)

            (view as TextView).setBackgroundColor(Color.parseColor("#674aa3"))

            textViewSelecionado = view

            when (view.id){
                R.id.textTime1 -> {
                    val textS = (view as TextView).text.toString()
                    Log.d("TIME", "tempo escolhido = $textS")
                }
                R.id.textTime2 -> {
                    val textS = (view as TextView).text.toString()
                    Log.d("TIME", "tempo escolhido = $textS")
                }
                R.id.textTime3 -> {
                    val textS = (view as TextView).text.toString()
                    Log.d("TIME", "tempo escolhido = $textS")
                }
                R.id.textTime4 -> {
                    val textS = (view as TextView).text.toString()
                    Log.d("TIME", "tempo escolhido = $textS")
                }
                R.id.textTime5 -> {
                    val textS = (view as TextView).text.toString()
                    Log.d("TIME", "tempo escolhido = $textS")
                }
                R.id.textTime6 -> {
                    val textS = (view as TextView).text.toString()
                    Log.d("TIME", "tempo escolhido = $textS")
                }
                R.id.textTime7 -> {
                    val textS = (view as TextView).text.toString()
                    Log.d("TIME", "tempo escolhido = $textS")
                }
                R.id.textTime8 -> {
                    val textS = (view as TextView).text.toString()
                    Log.d("TIME", "tempo escolhido = $textS")
                }
                R.id.textTime9 -> {
                    val textS = (view as TextView).text.toString()
                    Log.d("TIME", "tempo escolhido = $textS")
                }
                R.id.textTime10 -> {
                    val textS = (view as TextView).text.toString()
                    Log.d("TIME", "tempo escolhido = $textS")
                }
            }
        }

        textTime1.setOnClickListener(onClickListener)
        textTime2.setOnClickListener(onClickListener)
        textTime3.setOnClickListener(onClickListener)
        textTime4.setOnClickListener(onClickListener)
        textTime5.setOnClickListener(onClickListener)
        textTime6.setOnClickListener(onClickListener)
        textTime7.setOnClickListener(onClickListener)
        textTime8.setOnClickListener(onClickListener)
        textTime9.setOnClickListener(onClickListener)
        textTime10.setOnClickListener(onClickListener)
    }
}
