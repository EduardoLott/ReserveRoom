package com.example.reserveroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

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
        val db2 = FirebaseFirestore.getInstance()
        val collectionReference = db2.collection("rooms")

        val textRoomTime = findViewById<TextView>(R.id.textRoomTime)
        textRoomTime.setText("Horário da Sala $roomId")

        val textReserveRoom = findViewById<TextView>(R.id.textReserveRoom)
        textReserveRoom.setText("Reserva Sala $roomId")

        //Armazenar os horarios em string para enviar

        val textTime1 = findViewById<TextView>(R.id.textTime1)
        val textTime1String: String = textTime1.text.toString()

        val textTime2 = findViewById<TextView>(R.id.textTime2)
        val textTime2String: String = textTime2.text.toString()

        val textTime3 = findViewById<TextView>(R.id.textTime3)
        val textTime3String: String = textTime3.text.toString()

        val textTime4 = findViewById<TextView>(R.id.textTime4)
        val textTime4String: String = textTime4.text.toString()

        val textTime5 = findViewById<TextView>(R.id.textTime5)
        val textTime5String: String = textTime5.text.toString()

        val textTime6 = findViewById<TextView>(R.id.textTime6)
        val textTime6String: String = textTime6.text.toString()

        val textTime7 = findViewById<TextView>(R.id.textTime7)
        val textTime7String: String = textTime7.text.toString()

        val textTime8 = findViewById<TextView>(R.id.textTime8)
        val textTime8String: String = textTime8.text.toString()

        val textTime9 = findViewById<TextView>(R.id.textTime9)
        val textTime9String: String = textTime9.text.toString()

        val textTime10 = findViewById<TextView>(R.id.textTime10)
        val textTime10String: String = textTime10.text.toString()

        val times = listOf(
            textTime1String,
            textTime2String,
            textTime3String,
            textTime4String,
            textTime5String,
            textTime6String,
            textTime7String,
            textTime8String,
            textTime9String,
            textTime10String
        )

        //val query = reference.get()
        //Log.d("vamo ver", "eh pra voltar isso : $query")

        runBlocking {
            launch(Dispatchers.IO){
                buscando()
            }
        }

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
    suspend fun buscando() {
        val db2 = FirebaseFirestore.getInstance()
        val collectionReference = db2.collection("rooms")
        val roomId = intent.getStringExtra("roomId")
        val chosenDate : String= intent.getStringExtra("chosenDate")!!

        try{
            val querySnapshotTask: Task<QuerySnapshot> = collectionReference.get()
            val querySnapshot = Tasks.await(querySnapshotTask)
            for(document in querySnapshot.documents){
                val schedules = document["schedules"] as List<Map<String, String>>?
                Log.d("schedules", "schedules printando $schedules")
                Log.d("documento", "documento printando $document")
                schedules?.let {
                    for(schedule in it){
                        val time = schedule["time"]
                        Log.d("printa ai", "printa o time $time")
                    }
                }
            }
        } catch (e: Exception){
            Log.d("ERRO", "deu erro $e")
        }
    }
}


