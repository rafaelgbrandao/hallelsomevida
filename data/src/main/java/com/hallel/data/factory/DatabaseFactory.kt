package com.hallel.data.factory

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream
import java.lang.Exception
import com.hallel.data.BuildConfig.DATABASE_NAME

class DatabaseFactory(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    private val databasePath by lazy {
        context.getDatabasePath(DATABASE_NAME).toString()
    }

    private var db: SQLiteDatabase? = null

    override fun onCreate(p0: SQLiteDatabase?) {}

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun createDatabase() {
        if (!isDatabaseAlreadyCreated()) {
            copyDatabase()
        }
    }

    private fun isDatabaseAlreadyCreated(): Boolean {
        return context.getDatabasePath(DATABASE_NAME).exists()
    }

    private fun openDatabase(filePath: String) {
        try {
            SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE)
        } catch (error: Exception) {
            copyDatabase()
        }
    }

    override fun close() {
        db?.close()
    }

    private fun copyDatabase() {
        val inputStream = context.assets.open(DATABASE_NAME)
        val outputStream = FileOutputStream(databasePath)

        val buffer = ByteArray(1024)
        while (inputStream.read(buffer) > 0) {
            outputStream.write(buffer, 0, inputStream.read(buffer))
        }
        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }
}