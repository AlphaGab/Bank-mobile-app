package com.example.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity


class AccountCreate : AppCompatActivity() {
    lateinit var firstNameText:EditText
    lateinit var lastNameText:EditText
    lateinit var addressText:EditText
    lateinit var emailText:EditText
    lateinit var passwordText:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creatingaccountdesign)
        firstNameText = findViewById<EditText>(R.id.firstNameInput)
        lastNameText = findViewById<EditText>(R.id.lastNameInput)
        addressText = findViewById<EditText>(R.id.addressInput)
        emailText = findViewById<EditText>(R.id.emailInput)
        passwordText = findViewById<EditText>(R.id.passwordInput)
        val registerButton = findViewById<Button>(R.id.RegisterButton)

        var accountModel: UserModel
        val DatabaseHelper = DatabaseHelper.getInstance(this)
        val db = DatabaseHelper.writableDatabase
        registerButton.setOnClickListener {
            accountModel = UserModel(
                firstNameText.text.toString(), lastNameText.text.toString(),
                addressText.text.toString(), emailText.text.toString(), passwordText.text.toString()
            )
            if(DatabaseHelper.doesEmailExist(emailText.text.toString())) {
                Toast.makeText(this,"Email Existing",Toast.LENGTH_SHORT).show()
            }
            else {
                val success = DatabaseHelper.addOne(accountModel)
                finish()
            }
        }
    }

}