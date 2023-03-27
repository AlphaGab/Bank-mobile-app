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
    lateinit var backButton :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creatingaccountdesign)
        firstNameText = findViewById<EditText>(R.id.firstNameInput)
        lastNameText = findViewById<EditText>(R.id.lastNameInput)
        addressText = findViewById<EditText>(R.id.addressInput)
        emailText = findViewById<EditText>(R.id.emailInput)
        passwordText = findViewById<EditText>(R.id.passwordInput)
        backButton = findViewById<Button>(R.id.backButton)
        val registerButton = findViewById<Button>(R.id.RegisterButton)

        var accountModel: UserModel
        val DatabaseHelper = DatabaseHelper.getInstance(this)
        val db = DatabaseHelper.writableDatabase
        registerButton.setOnClickListener {
            accountModel = UserModel(
                firstNameText.text.toString().trim(), lastNameText.text.toString().trim(),
                addressText.text.toString().trim(), emailText.text.toString().trim(), passwordText.text.toString().trim()
            )
            if(!checkAllFields()){
                return@setOnClickListener
            }
            if(DatabaseHelper.doesEmailExist(emailText.text.toString())) {
                Toast.makeText(this,"Email Existing",Toast.LENGTH_SHORT).show()
            }
            else {
                val success = DatabaseHelper.addOne(accountModel)
                Toast.makeText(this,"Succesfully Created an ACCOUNT",Toast.LENGTH_LONG).show()
                finish()
            }
        }
        backButton.setOnClickListener{
            finish()
        }

    }
    // check if fields are blank return true if not
    fun checkAllFields():Boolean{
        if(firstNameText.text.isBlank()){
            firstNameText.error = "This Field Is Required"
            return false
        }
        if(lastNameText.text.trim().isEmpty()){
            lastNameText.error = "This Field Is Required"
            return false
        }
        if(addressText.text.isBlank()){
            addressText.error = "This Field Is Required"
            return false
        }
        if(emailText.text.isBlank()) {
            emailText.error = "This Field Is Required"
            return false
        }
        if (passwordText.text.isBlank()){
            passwordText.error = "This Field Is Required"
            return false
        }
        return true
    }

}
