package com.modcom.medilabmemberapp.helpers

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.modcom.medilabmemberapp.MyCart
import com.modcom.medilabmemberapp.models.LabTest

class SQLiteCartHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object{
            private const val DATABASE_VERSION = 1
            private const val DATABASE_NAME = "cart.db"
            private const val DATABASE_TABLE = "cart"

            // columns
            private const val TEST_ID = "test_id"
            private const val TEST_NAME = "test_name"
            private const val TEST_COST = "test_cost"
            private const val LAB_ID = "lab_id"
            private const val TEST_DESCRIPTION = "test_description"

        }

        // Make context visible inside the class
    val context = context

    override fun onCreate(db: SQLiteDatabase?) {
        // create sql query
        val createTableQuery = """
            CREATE TABLE $DATABASE_TABLE (
                $TEST_ID INTEGER PRIMARY KEY,
                $TEST_NAME VARCHAR,
                $TEST_COST INTEGER,
                $LAB_ID INTEGER,
                $TEST_DESCRIPTION TEXT
            )
        """.trimIndent()

        // execute the sql query
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery = """
            DROP TABLE IF EXISTS $DATABASE_TABLE
        """.trimIndent()
        db?.execSQL(dropTableQuery)
    }

    // INSERT CART ITEM
    //insert cart item
    fun insert(test_id: Int?,test_name :String?,test_cost:Int?,
               test_description :String?,lab_id :Int?){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("test_id",test_id)
        values.put("test_name",test_name)
        values.put("test_cost",test_cost)
        values.put("test_description",test_description)
        values.put("lab_id",lab_id)

        //call the db and save the values
        val result :Long = db.insert(DATABASE_TABLE,null,values)

        if (result < 1){
            Toast.makeText(context, "Item Already in the cart", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Item added  to cart successfully", Toast.LENGTH_SHORT).show()
        }

//        Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT).show()

    }//end insert

    //get cart count: return Int(Number)
    fun getNumItems(): Int {
        val db = this.readableDatabase
        val result : Cursor = db.rawQuery("SELECT * FROM $DATABASE_TABLE",null)
        return result.count
    } // end getCount()


    // CLEAR ALL ITEMS
    fun clearCart(){
        val db = this.writableDatabase //edit
        db.delete(DATABASE_TABLE, null, null)
        Toast.makeText(context, "Cart Cleared", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, MyCart::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)

    }

    // CLEAR CART BY TEST ID

    fun clearCartByID(test_id: String){
        val db = this.writableDatabase
        // provide test_id while deleting
        db.delete(DATABASE_TABLE, "test_id=?", arrayOf(test_id))
        Toast.makeText(context, "Item ID $test_id Cleared!", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, MyCart::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }


    // GET TOTAL COST IN THE CART
    fun totalCost(): Double {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT SUM(test_cost) from $DATABASE_TABLE", null)
        var total: Double = 0.0
        while (cursor.moveToNext()){
            total += cursor.getDouble(0)
        }
        return total
    }


    // RETURN ALL THE LAB TEST IN THE CART

    fun getAllItems() : ArrayList<LabTest>{
        val db = this.readableDatabase
        val items = ArrayList<LabTest>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM $DATABASE_TABLE", null)

        while (cursor.moveToNext()){
            val model = LabTest()
            model.test_id = cursor.getInt(0) // index 0 -> test_id
            model.test_name = cursor.getString(1)
            model.test_cost = cursor.getInt(2)
            model.lab_id = cursor.getInt(3)
            model.test_description = cursor.getString(4)

            items.add(model)
        }
        return items
    }
//clear cart
//clearById
//total cost
//getALL ITEMS

}