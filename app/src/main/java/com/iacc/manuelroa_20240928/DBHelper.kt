package com.iacc.manuelroa_20240928

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "trabajadores.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Trabajadores"
        private const val COLUMN_RUT = "RUT"
        private const val COLUMN_NOMBRE = "NOMBRE"
        private const val COLUMN_APELLIDOS = "APELLIDOS"
        private const val COLUMN_ESTADO = "ESTADO"
        private const val COLUMN_FECHA = "FECHA"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_RUT TEXT PRIMARY KEY,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_APELLIDOS TEXT,
                $COLUMN_ESTADO TEXT,
                $COLUMN_FECHA INTEGER
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
