package com.example.image_database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class dbHelperClass(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_IMAGE BLOB)"
        db?.execSQL(CREATE_TABLE)
        val INSERT_ROW = "INSERT INTO $TABLE_NAME ($COLUMN_ID, $COLUMN_IMAGE) VALUES (1, '')"
        db?.execSQL(INSERT_ROW)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MyDatabase"
        private const val TABLE_NAME = "MyTable"
        private const val COLUMN_ID = "id"
        private const val COLUMN_IMAGE = "data"

    }

    fun updateData(imageData: ByteArray?): Boolean {
        val db = this.writableDatabase
        val UPDATE_ROW = "UPDATE $TABLE_NAME SET $COLUMN_IMAGE=? WHERE $COLUMN_ID=1"
        val stmt = db.compileStatement(UPDATE_ROW)
        stmt.bindBlob(1, imageData)
        stmt.executeUpdateDelete()
        db.close()
        return true
    }

    @SuppressLint("Range")
    fun getData(): ByteArray? {
        val db = this.readableDatabase
        val SELECT_ROW = "SELECT $COLUMN_IMAGE FROM $TABLE_NAME WHERE $COLUMN_ID=1"
        val cursor = db.rawQuery(SELECT_ROW, null)
        cursor.moveToFirst()
        val imageData = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
        cursor.close()
        db.close()
        return imageData
    }

}