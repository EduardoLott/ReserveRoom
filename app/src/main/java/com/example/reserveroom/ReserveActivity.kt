package com.example.reserveroom

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    private val statusMap = mutableMapOf<String, Boolean>()

    private lateinit var editTextName: EditText
    private lateinit var editTextCpf: EditText
    private lateinit var editTextEmail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reserve_card)

        adicionarHorariosAoMapa()

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


        runBlocking {
            launch(Dispatchers.IO) {
                val horariosExistentes = buscando(roomId.toString(), chosenDate)
                atualizarCoresDosHorarios(horariosExistentes)
            }
        }

        // Inicialize os campos de entrada
        editTextName = findViewById(R.id.nameEditText)
        editTextCpf = findViewById(R.id.cpfEditText)
        editTextEmail = findViewById(R.id.emailEditText)

        
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

        var textS: String = ""

        val onClickListener = View.OnClickListener { view ->
            textViewSelecionado?.setTextColor(Color.parseColor("#009E1A"))
            textViewSelecionado?.setBackgroundColor(Color.WHITE)

            (view as TextView).setBackgroundColor(Color.parseColor("#674aa3"))

            textViewSelecionado = view

            // Atribuir o valor a textS dentro do when
            when (view.id) {
                R.id.textTime1, R.id.textTime2, R.id.textTime3, R.id.textTime4, R.id.textTime5,
                R.id.textTime6, R.id.textTime7, R.id.textTime8, R.id.textTime9, R.id.textTime10 -> {
                    textS = (view as TextView).text.toString()
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

        val buttonReserve = findViewById<Button>(R.id.buttonReserve)
        buttonReserve.setOnClickListener {
            val time = textS
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

                        val intent = Intent(this, ReserveActivity::class.java)
                        intent.putExtra("roomId", roomId)
                        intent.putExtra("chosenDate", chosenDate)
                        startActivity(intent)
                        finish() // Isso encerra a atividade atual
                    }
                    .addOnFailureListener { e ->
                        println("Erro ao adicionar reserva: $e")
                    }
            }
        }
    }
    

    suspend fun buscando(roomId: String, chosenDate: String): List<String> {
        val db2 = FirebaseFirestore.getInstance()
        val collectionReference = db2.collection("rooms")

        try {
            val horariosExistentes = mutableListOf<String>()

            val querySnapshotTask: Task<QuerySnapshot> = collectionReference.get()
            val querySnapshot = Tasks.await(querySnapshotTask)

            for (document in querySnapshot.documents) {
                val schedules = document["schedules"] as List<Map<String, String>>?
                schedules?.let {
                    for (schedule in it) {
                        val time = schedule["time"]
                        // Verificar se a data e a sala correspondem
                        if (schedule["date"] == chosenDate && document.id == roomId) {
                            horariosExistentes.add(time ?: "")
                        }
                    }
                }
            }

            // Agora, a lista horariosExistentes contém os horários para a sala e data específicas
            Log.d("horariosExistentes", "Horários existentes para a sala $roomId na data $chosenDate: $horariosExistentes")
            return horariosExistentes
        } catch (e: Exception) {
            Log.d("ERRO", "deu erro $e")
            return emptyList()
        }
    }

    private fun adicionarHorariosAoMapa() {
        // Adicione todos os horários iniciais como disponíveis
        statusMap["08:00 - 09:00"] = true
        statusMap["09:00 - 10:00"] = true
        statusMap["10:00 - 11:00"] = true
        statusMap["11:00 - 12:00"] = true
        statusMap["12:00 - 13:00"] = true
        statusMap["13:00 - 14:00"] = true
        statusMap["14:00 - 15:00"] = true
        statusMap["15:00 - 16:00"] = true
        statusMap["16:00 - 17:00"] = true
        statusMap["17:00 - 18:00"] = true

        // Adicione os demais horários conforme necessário
    }

    private fun atualizarCoresDosHorarios(horariosExistentes: List<String>) {
        // Atualize as cores dos TextViews com base nos horários existentes
        // Exemplo: percorra todos os TextViews e defina a cor com base na existência na lista
        for (textViewId in listOf(R.id.textTime1, R.id.textTime2, R.id.textTime3, R.id.textTime4, R.id.textTime5, R.id.textTime6, R.id.textTime7, R.id.textTime8, R.id.textTime9, R.id.textTime10)) {
            val textView = findViewById<TextView>(textViewId)
            val time = textView.text.toString()
            val isExisting = horariosExistentes.contains(time)

            // Se o horário já existir, marque como vermelho
            val cor = if (isExisting) R.color.red else R.color.green
            textView.setTextColor(ContextCompat.getColor(this, cor))
        }
    }
}



