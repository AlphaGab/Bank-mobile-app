package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet


class MainActivity : AppCompatActivity() {
    lateinit var emailText : EditText;
    lateinit var passwordText: EditText
    lateinit var buttonAdd :Button
    lateinit var buttonCreateAccount : TextView
    lateinit var  actualEmail :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailText = findViewById(R.id.editTextEmail)
        passwordText = findViewById(R.id.editTextPassword)
        buttonAdd = findViewById(R.id.button)
        buttonCreateAccount = findViewById<TextView>(R.id.createLink)

        val DatabaseHelper = DatabaseHelper.getInstance(this)
        var accountModel:UserModel


        buttonAdd.setOnClickListener{
            actualEmail = emailText.text.toString().lowercase().trim()
            if(DatabaseHelper.isValidLoginDetails(actualEmail, passwordText.text.toString())){
                Toast.makeText(this,"Valid Login",Toast.LENGTH_LONG).show()
                showActivity2()


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

    private fun setConstraintSet(newConstraints: ConstraintSet) {

    }
    // This shows the registration form using Intent
    fun showRegistrationForms(){
        val intent = Intent(this,AccountCreate::class.java)
        startActivity(intent)
    }
    // This shows the activity 2
    fun showActivity2(){

        val intent = Intent(this,MainActivity2::class.java)
        intent.putExtra("Email",actualEmail)
        startActivity(intent)
    }


}