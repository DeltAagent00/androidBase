package com.homedev.weather.core.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.LongSparseArray
import androidx.core.util.set

/**
 * Created by Alexandr Zheleznyakov on 2019-11-14.
 */
object WeatherTable {
    const val TABLE_NAME = "Weather"
    const val COLUMN_ID = "_id"
    const val COLUMN_CITY = "city"
    const val COLUMN_DATA = "data"
    const val COLUMN_CREATE = "createDate"

    fun createTable(database: SQLiteDatabase) {
        database.execSQL( "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_CITY INTEGER, $COLUMN_DATA TEXT, $COLUMN_CREATE LONG);"
        )
    }

    fun onUpgrade(database: SQLiteDatabase) {
        database.execSQL(
            "TRUNCATE TABLE '$TABLE_NAME';"
        )
    }

    fun addData(city: String, data: String, db: SQLiteDatabase) {
        val values = ContentValues()
        values.put(COLUMN_CITY, city)
        values.put(COLUMN_DATA, data)
        values.put(COLUMN_CREATE, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, values)
    }

     fun editData(town: String, data: String, db: SQLiteDatabase) {
        val values = ContentValues()
        values.put(COLUMN_DATA, data)
        values.put(COLUMN_CREATE, System.currentTimeMillis())

        db.update(TABLE_NAME, values, "$COLUMN_CITY = '$town'", null)
    }

    fun getAllDataFromCity(city: String, database: SQLiteDatabase): LongSparseArray<String> {
        val cursor = database.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_CITY = '$city';", null)
        return getResultFromCursor(cursor)
    }

    private fun getResultFromCursor(cursor: Cursor): LongSparseArray<String> {
        val result = LongSparseArray<String>()

        if (cursor.moveToFirst()) {
            val dataIdx = cursor.getColumnIndex(COLUMN_DATA)
            val createIdx = cursor.getColumnIndex(COLUMN_CREATE)
            do {
                result[cursor.getLong(createIdx)] = cursor.getString(dataIdx)
            } while (cursor.moveToNext())
        }

        try {
            cursor.close()
        } catch (ignored: Exception) {
        }

        return result
    }
}