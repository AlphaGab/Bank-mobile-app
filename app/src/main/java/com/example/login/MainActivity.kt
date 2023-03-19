package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

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

        val DatabaseHelper = DatabaseHelper(this)
        var accountModel:AccountModel

        buttonAdd.setOnClickListener{
            if(DatabaseHelper.isValidLoginDetails(emailText.text.toString(), passwordText.text.toString())){
                Toast.makeText(this,"Valid Login",Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this,"Invalid Login",Toast.LENGTH_LONG).show()
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