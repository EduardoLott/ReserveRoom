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
    private var chosenDate: String = ""

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

                // Atualize a variável chosenDate aqui
                chosenDate = dateString
            }
        }

        val buttonRoom1 = findViewById<Button>(R.id.Room1)
        val buttonRoom2 = findViewById<Button>(R.id.Room2)
        val buttonRoom3 = findViewById<Button>(R.id.Room3)
        val buttonRoom4 = findViewById<Button>(R.id.Room4)
        val buttonRoom5 = findViewById<Button>(R.id.Room5)
        val buttonRoom6 = findViewById<Button>(R.id.Room6)


        buttonRoom1.setOnClickListener {
            navigateToReserveActivity("1")
        }
        buttonRoom2.setOnClickListener {
            navigateToReserveActivity("2")
        }
        buttonRoom3.setOnClickListener {
            navigateToReserveActivity("3")
        }
        buttonRoom4.setOnClickListener {
            navigateToReserveActivity("4")
        }
        buttonRoom5.setOnClickListener {
            navigateToReserveActivity("5")
        }
        buttonRoom6.setOnClickListener {
            navigateToReserveActivity("6")
        }
    }

    private fun navigateToReserveActivity(roomId: String) {
        Log.d(TAG, "Navigating to ReserveActivity with roomId: $roomId and chosenDate: $chosenDate")

        val intent = Intent(this, ReserveActivity::class.java)
        intent.putExtra("roomId", roomId)
        intent.putExtra("chosenDate", chosenDate)
        startActivity(intent)
    }

}