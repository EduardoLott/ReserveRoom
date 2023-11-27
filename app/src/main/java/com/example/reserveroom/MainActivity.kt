package com.example.reserveroom

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore
import java.time.LocalDate
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var mDisplayDate: TextView
    private lateinit var mDateSetListener: DatePickerDialog.OnDateSetListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        val db = Firebase.firestore

        mDisplayDate = findViewById<TextView>(R.id.textInsertDate)

        mDisplayDate.setOnClickListener { view ->
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(
                this@MainActivity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,
                month,
                day
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        mDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
                val formattedMonth = month + 1
                Log.d(TAG, "onDateSet: dd/mm/yyyy: $day/$formattedMonth/$year")

                val dateString = "$day/$formattedMonth/$year"
                mDisplayDate.text = dateString
            }
        }


        val buttonRoom1 = findViewById<Button>(R.id.Room1)
        val buttonRoom2 = findViewById<Button>(R.id.Room2)
        val buttonRoom3 = findViewById<Button>(R.id.Room3)
        val buttonRoom4 = findViewById<Button>(R.id.Room4)
        val buttonRoom5 = findViewById<Button>(R.id.Room5)
        val buttonRoom6 = findViewById<Button>(R.id.Room6)

        val room = Rooms()

        val today = LocalDate.now()
        val schedule1 = Schedules("9:00", "123456789", "John Doe", "john@example.com", true, today)

        val schedule2 = Schedules("14:00", "987654321", "Jane Doe", "jane@example.com", true, today)

        // Adicionar agendamentos à sala
        room.schedules = listOf(schedule1, schedule2)

        // Adicionar a sala ao Firestore
        room.addRoomToFirestore()

        // Adicionar os agendamentos à sala no Firestore
        room.addSchedulesToRoom()
        println("Agendamentos adicionados à sala com sucesso.")



        buttonRoom1.setOnClickListener() {
            val intent = Intent(this, ReserveActivity::class.java)
            Log.d("TEXT", "enttrou")
            startActivity(intent)
        }
        buttonRoom2.setOnClickListener() {
            val intent = Intent(this, ReserveActivity::class.java)
            Log.d("TEXT", "enttrou")
            startActivity(intent)
        }
        buttonRoom3.setOnClickListener() {
            val intent = Intent(this, ReserveActivity::class.java)
            Log.d("TEXT", "enttrou")
            startActivity(intent)
        }
        buttonRoom4.setOnClickListener() {
            val intent = Intent(this, ReserveActivity::class.java)
            Log.d("TEXT", "enttrou")
            startActivity(intent)
        }
        buttonRoom5.setOnClickListener() {
            val intent = Intent(this, ReserveActivity::class.java)
            Log.d("TEXT", "enttrou")
            startActivity(intent)
        }
        buttonRoom6.setOnClickListener() {
            val intent = Intent(this, ReserveActivity::class.java)
            Log.d("TEXT", "enttrou")
            startActivity(intent)
        }

    }


}