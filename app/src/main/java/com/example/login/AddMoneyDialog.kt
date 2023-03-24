package com.example.login

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class AddMoneyDialog : DialogFragment(){
    open var amount: Double = 0.0
    private lateinit var listener: AddMoneyDialogListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.addmoney, null)
            val addMoneyEditText = view.findViewById<EditText>(R.id.addMoneyEditText)


            builder.setView(view)

                .setPositiveButton("Add",
                    DialogInterface.OnClickListener { dialog, id ->
                      val amountInString = addMoneyEditText.text.toString().trim()
                        amount = amountInString.toDouble()
                        listener.onAddMoney(amount)
                        dialog.cancel()

                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    interface AddMoneyDialogListener {
        fun onAddMoney(amount: Double)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as AddMoneyDialogListener
    }


}