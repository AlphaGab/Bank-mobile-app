package com.example.login

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION)  {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "BankDatabase"
        private val TABLE_NAME = "AccountsTable"
        private val KEY_ID = "id"
        private val KEY_FIRST = "First_Name"
        private val KEY_LAST = "Last_Name"
        private val KEY_EMAIL = "email"
        private val KEY_PASSWORD = "Password"
        private val KEY_ADDRESS = "Address"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,$KEY_FIRST TEXT,$KEY_LAST TEXT,$KEY_EMAIL TEXT,$KEY_PASSWORD TEXT,$KEY_ADDRESS TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
    fun addOne(accountModel : AccountModel):Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_FIRST,accountModel.firstname)
        cv.put(KEY_LAST,accountModel.lastname)
        cv.put(KEY_ADDRESS,accountModel.address)
        cv.put(KEY_EMAIL,accountModel.email)
        cv.put(KEY_PASSWORD,accountModel.password)
       val insert = db.insert(TABLE_NAME,null,cv)
        val errorValue:Long = -1
        db.close()

        if (insert == errorValue){
            return false
        }
            return true
    }
    fun isValidLoginDetails(email:String,password:String):Boolean{
        val db = this.readableDatabase
        var result = false
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $KEY_EMAIL = ? AND $KEY_PASSWORD= ? ",
            arrayOf(email,password))
        if(cursor.moveToFirst()) {
            result = true

        }
        cursor.close()
        db.close()
        return result;
    }
    fun doesEmailExist(email: String): Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $KEY_EMAIL = ? ", arrayOf(email))
        db.close()
        cursor.close()
        if(cursor.moveToFirst()){
            return true
        }
          return false
    }



}