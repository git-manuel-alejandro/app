package com.iacc.manuelroa_20240928

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Trabajadores.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE Trabajadores ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "RUT TEXT, "
                + "NOMBRE TEXT, "
                + "APELLIDOS TEXT, "
                + "ESTADO TEXT, "
                + "FECHA INTEGER)") // Asegúrate de que FECHA sea INTEGER (Long)
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Trabajadores")
        onCreate(db)
    }

    fun insertarTrabajador(trabajador: Trabajador): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("RUT", trabajador.rut)
            put("NOMBRE", trabajador.nombre)
            put("APELLIDOS", trabajador.apellidos)
            put("ESTADO", trabajador.estado)
            put("FECHA", trabajador.fecha) // Asegúrate de que esto sea un Long
        }

        val id = db.insert("Trabajadores", null, contentValues)
        db.close()
        return id
    }
}
