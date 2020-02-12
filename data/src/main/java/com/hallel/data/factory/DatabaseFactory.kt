package com.hallel.data.factory

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hallel.data.BuildConfig.DATABASE_NAME

class DatabaseFactory(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    private val databasePath by lazy {
        context.getDatabasePath(DATABASE_NAME)
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
        return databasePath.exists()
    }

    override fun close() {
        db?.close()
        super.close()
    }

    private fun copyDatabase() {
        context.assets.open(DATABASE_NAME).copyTo(databasePath.outputStream())
    }
}