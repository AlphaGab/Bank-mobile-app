package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var emailText : EditText;
    lateinit var passwordText: EditText
    lateinit var buttonAdd :Button
    lateinit var buttonCreateAccount : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailText = findViewById(R.id.editTextEmail)
        passwordText = findViewById(R.id.editTextPassword)
        buttonAdd = findViewById(R.id.button)
        buttonCreateAccount = findViewById<TextView>(R.id.createLink)
        val DatabaseHelper = DatabaseHelper.getInstance(this)
        Toast.makeText(this,"test",Toast.LENGTH_LONG).show()
        var accountModel:UserModel

        buttonAdd.setOnClickListener{
            if(DatabaseHelper.isValidLoginDetails(emailText.text.toString(), passwordText.text.toString())){
                Toast.makeText(this,"Valid Login",Toast.LENGTH_LONG).show()
                Log.d("logged In" , "MADRID")
            }
            else {
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_LONG).show()
                Log.d("Invalid","Not logged")
            }
        }
        buttonCreateAccount.setOnClickListener{
            showRegistrationForms()
        }


    }
    fun showRegistrationForms(){
        val intent = Intent(this,AccountCreate::class.java)
        startActivity(intent)
    }
}