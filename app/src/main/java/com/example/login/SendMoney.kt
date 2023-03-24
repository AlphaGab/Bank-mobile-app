package com.example.login

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
        sendButton.setOnClickListener{
            if(!areEmpty(amountText,idText)){
               val amountDouble = amountText.text.toString().toDouble()
                val id = idText.text.toString().toInt()
                val intent = intent.getStringExtra("Email")
                databaseHelper.sendMoney(amountDouble,id,intent)


                val idDatabase = databaseHelper.getUserId(intent)
               Log.d("Intent",intent!!)
                Log.d("ID OF CURRENT LOGIN",idDatabase.toString())
                Log.d("ID OF RECIPIENT",id.toString())
                finish()
            }
        }
        cancelButton.setOnClickListener{
            finish()
        }

        }
    }

fun areEmpty(editText1: EditText, editText2: EditText):Boolean{
    val amountText = editText1.text.trim()
    val idText = editText2.text.trim()
    return amountText.isEmpty() || idText.isEmpty()
}



