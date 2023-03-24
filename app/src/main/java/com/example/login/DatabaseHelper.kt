package com.example.login

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.database.getStringOrNull
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION)  {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "BankDatabase"
        private val TABLE_USERS = "Users"
        private val KEY_ID = "id"
        private val KEY_FIRST = "First_Name"
        private val KEY_LAST = "Last_Name"
        private val KEY_EMAIL = "email"
        private val KEY_PASSWORD = "Password"
        private val KEY_ADDRESS = "Address"
        const val KEY_ACCOUNT_ID = "account_id"
        const val KEY_BALANCE = "Balance"
        const val KEY_DATE = "date"
        private  const val TABLE_TRANSACTIONS = "Account"

        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = DatabaseHelper(context)
                    INSTANCE = instance
                }
                return INSTANCE!!


            }
        }
    }



    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_USERS ($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,$KEY_FIRST TEXT,$KEY_LAST TEXT,$KEY_EMAIL TEXT,$KEY_PASSWORD TEXT,$KEY_ADDRESS TEXT)"
        db?.execSQL(createTable)

        db?.execSQL("""
        CREATE TABLE $TABLE_TRANSACTIONS (
            $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $KEY_ACCOUNT_ID INTEGER,
            $KEY_BALANCE Double,
            $KEY_DATE TEXT,
            FOREIGN KEY($KEY_ACCOUNT_ID) REFERENCES $TABLE_USERS($KEY_ID)
        )
    """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
    fun addOne(accountModel : UserModel):Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()
        val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        cv.put(KEY_FIRST,accountModel.firstname)
        cv.put(KEY_LAST,accountModel.lastname)
        cv.put(KEY_ADDRESS,accountModel.address)
        cv.put(KEY_EMAIL,accountModel.email)
        cv.put(KEY_PASSWORD,accountModel.password)
       val insert = db.insert(TABLE_USERS,null,cv)
        val errorValue:Long = -1

        // insert a new account for the user
    val accountCv = ContentValues().apply {
            put(KEY_ACCOUNT_ID, getUserId(accountModel.email))
            put(KEY_BALANCE, 100)
            put(KEY_DATE, date)
        }
        val insertAccount = db.insert(TABLE_TRANSACTIONS,null,accountCv)

        if (insert == errorValue){
            return false
        }
            return true
    }
    fun isValidLoginDetails(email:String,password:String):Boolean{
        val db = this.readableDatabase
        var result = false
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $KEY_EMAIL = ? AND $KEY_PASSWORD= ? ",
            arrayOf(email,password))
        if(cursor.moveToFirst()) {
            result = true
        }

        return result;
    }
    fun doesEmailExist(email: String): Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE LOWER($KEY_EMAIL) = LOWER(?) ", arrayOf(email))

        if(cursor.moveToFirst()){
            return true
        }
          return false
    }
    fun getUserId(email: String?):Int{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $KEY_ID FROM $TABLE_USERS WHERE $KEY_EMAIL = ? ", arrayOf(email))
        var userId = 0;
        if(cursor.moveToFirst()){
            Log.d("GETUSERID", "FUNCTION")
            userId = cursor.getInt(0)
        }
        return userId
    }

    fun getWholeName(userId : Int):String{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $KEY_FIRST, $KEY_LAST FROM $TABLE_USERS WHERE $KEY_ID = ? ", arrayOf(userId.toString()))
        var wholeName = ""
        if(cursor.moveToFirst()){
            val firstName = cursor.getStringOrNull(cursor.getColumnIndex(KEY_FIRST))
            val lastName  = cursor.getStringOrNull(cursor.getColumnIndex(KEY_LAST))
            wholeName = "$firstName $lastName"
        }
        return wholeName
    }
    fun getBalance(email: String?): Int{
        val userId = getUserId(email)
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $KEY_BALANCE FROM $TABLE_TRANSACTIONS WHERE $KEY_ACCOUNT_ID = ? ",
            arrayOf(userId.toString()))
        if(cursor.moveToFirst()){
            var balance = cursor.getInt(0)
            return balance
        }
        return 0

    }
    fun addMoney(amount:Double,id:Int){
        val db = this.writableDatabase

        val updateMoneyQuery = "UPDATE $TABLE_TRANSACTIONS SET $KEY_BALANCE = $KEY_BALANCE + ${amount} WHERE $KEY_ACCOUNT_ID = ${id};"
        db.execSQL(updateMoneyQuery)


        Log.d("Added money","ADDED")

    }
    fun sendMoney(amount: Double,id : Int,email: String?):Unit{
        val isIdValid = isIdValid(id)
        if(isIdValid){
           Log.d("Valid", "VALID ID")
              addMoney(amount,id)
              subtractMoney(amount,email)

        }
    }
    fun isIdValid(id: Int): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT $KEY_ACCOUNT_ID FROM $TABLE_TRANSACTIONS WHERE $KEY_ACCOUNT_ID = ? ",
            arrayOf(id.toString())
        )
        return cursor.moveToFirst()
    }
    fun isMoneyEnough(email: String,amount: Double):Boolean{
        Log.d("ismoneyEnoug", "FUNCTION")
        val balance = getBalance(email)
         if(balance >= amount){
             return true
             Log.d("ismoneyEnoug", "True")

        }
            return false
    }
    fun getUserEmail(id:Int):String{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $KEY_EMAIL FROM $TABLE_USERS WHERE $KEY_ID = ? ", arrayOf(id.toString()))
        if(cursor.moveToFirst()){
            val email = cursor.getString(0)
            Log.d("EMAIL ","EMAIL GOT")
            return email
        }
        return "Email Unidentified"


    }
    fun subtractMoney(amount: Double,email: String?){

        val db = this.writableDatabase
        val id = getUserId(email)
        val updateMoneyQuery = "UPDATE $TABLE_TRANSACTIONS SET $KEY_BALANCE = $KEY_BALANCE - $amount WHERE $KEY_ACCOUNT_ID = $id;"
        db.execSQL(updateMoneyQuery)
        Log.d("Subtracted", "Money Subtracted")
        db.close()
    }





}