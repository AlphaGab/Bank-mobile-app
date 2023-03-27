package com.example.login

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class AddMoneyDialog : DialogFragment(){

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

                        if (amountInString.isEmpty()) {
                            // The amount is empty
                            Toast.makeText(requireContext(), "Please enter an amount", Toast.LENGTH_SHORT).show()
                            return@OnClickListener
                        }

                        val amount = amountInString.toDoubleOrNull()

                        if (amount == null) {
                            // The amount is not a valid number
                            Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                            return@OnClickListener
                        }else {
                            listener.onAddMoney(amount)
                            dialog.dismiss()
                        }
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    // this is callback method, provides a connection to the activity and a fragment
    interface AddMoneyDialogListener {
        fun onAddMoney(amount: Double?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as AddMoneyDialogListener
    }


}