package com.example.login

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class SendMoney : AppCompatActivity() {


    lateinit var sendButton : Button
    lateinit var cancelButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.sendmoneydialog)
         val idText = findViewById<EditText>(R.id.idEditText)
         val amountText = findViewById<EditText>(R.id.amountEditText)
         sendButton = findViewById<Button>(R.id.buttonSend)
        val databaseHelper = DatabaseHelper.getInstance(this)

        cancelButton = findViewById<Button>(R.id.cancel_button)
        sendButton.setOnClickListener {
            val id = idText.text.toString().trim().toIntOrNull()
            val amount = amountText.text.toString().trim().toDoubleOrNull()
            val email = intent.getStringExtra("Email")
            if (id == null) {
                Toast.makeText(this, "Invalid ID", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (amount == null) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!databaseHelper.isIdValid(id)) {
                Toast.makeText(this, "ID not found", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(!databaseHelper.isMoneyEnough(email,amount)){
                Toast.makeText(this,"Not Enough Money",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            databaseHelper.sendMoney(amount, id, email)
            finish()
        }
        cancelButton.setOnClickListener{
            finish()
        }

        }
    }





