package com.example.login

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

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
        val wholeName = databaseHelper.getWholeName(databaseHelper.getUserId(intent.getStringExtra("Email")))
        namePlaceholder.text=wholeName
        val actualBalance = databaseHelper.getBalance(intent.getStringExtra("Email"))

        val addMoneyDialog = AddMoneyDialog()
        finalAmount = actualBalance.toDouble()

        addButton.setOnClickListener{
            addMoneyDialog.show(supportFragmentManager,"Dialog")



        }
        balancePlaceholder.text = finalAmount.toString()
      
    }

    override fun onAddMoney(amount: Double) {
        val databaseHelper = DatabaseHelper.getInstance(this)
        finalAmount += amount
        balancePlaceholder.text = finalAmount.toString()
        val userId = databaseHelper.getUserId(intent.getStringExtra("Email"))
        databaseHelper.addMoney(amount,userId)
    }


}