package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity(), AddMoneyDialog.AddMoneyDialogListener {
    var finalAmount:Double = 0.0

    lateinit var balancePlaceholder: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bank_interface)
        val namePlaceholder = findViewById<TextView>(R.id.namePlaceHolder)
        balancePlaceholder = findViewById<TextView>(R.id.amountPlaceHolder)
        val databaseHelper = DatabaseHelper.getInstance(this)
        val addButton = findViewById<ImageView>(R.id.addMoneyButton)
        val logoutButton = findViewById<Button>(R.id.Logout)
        val sendMoney = findViewById<ImageView>(R.id.sendMoneyButton)
        val wholeName = databaseHelper.getWholeName(databaseHelper.getUserId(intent.getStringExtra("Email")))
        namePlaceholder.text=wholeName
        val actualBalance = databaseHelper.getBalance(intent.getStringExtra("Email"))

        val addMoneyDialog = AddMoneyDialog()
        finalAmount = actualBalance.toDouble()

        addButton.setOnClickListener{
            addMoneyDialog.show(supportFragmentManager,"Dialog")

        }
        sendMoney.setOnClickListener{
           showSendMoneyActivity()
        }
        logoutButton.setOnClickListener{
            finish()
        }

        balancePlaceholder.text = finalAmount.toString()
      
    }

    override fun onAddMoney(amount: Double?) {
        val databaseHelper = DatabaseHelper.getInstance(this)
        finalAmount += amount!!
        balancePlaceholder.text = finalAmount.toString()
        val userId = databaseHelper.getUserId(intent.getStringExtra("Email"))
        databaseHelper.addMoney(amount,userId)

    }
    fun showSendMoneyActivity(){
        val intents = Intent(this,SendMoney::class.java)
        intents.putExtra("Email",intent.getStringExtra("Email"))
        startActivity(intents)
    }
    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        val databaseHelper = DatabaseHelper.getInstance(this)
        val actualBalance = databaseHelper.getBalance(intent.getStringExtra("Email"))
        balancePlaceholder.text = actualBalance.toString()
    }


}