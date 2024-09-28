package com.iacc.manuelroa_20240928

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var rvRegistros: RecyclerView
    private lateinit var adapter: TrabajadorAdapter
    private lateinit var etRUT: EditText
    private lateinit var etNombre: EditText
    private lateinit var etApellidos: EditText
    private lateinit var rgEstado: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)
        rvRegistros = findViewById(R.id.rvRegistros)
        etRUT = findViewById(R.id.etRUT)
        etNombre = findViewById(R.id.etNombre)
        etApellidos = findViewById(R.id.etApellidos)
        rgEstado = findViewById(R.id.rgEstado)

        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener { registrarTrabajador() }

        rvRegistros.layoutManager = LinearLayoutManager(this)
        cargarRegistros()
    }

    private fun registrarTrabajador() {
        val rut = etRUT.text.toString()
        val nombre = etNombre.text.toString()
        val apellidos = etApellidos.text.toString()
        val estado = if (findViewById<RadioButton>(rgEstado.checkedRadioButtonId).text == "Ingresando") "Ingresando" else "Saliendo"
        val fecha = System.currentTimeMillis()

        if (rut.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("RUT", rut)
            put("NOMBRE", nombre)
            put("APELLIDOS", apellidos)
            put("ESTADO", estado)
            put("FECHA", fecha)
        }

        val newRowId = db.insert("Trabajadores", null, values)
        if (newRowId != -1L) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            cargarRegistros()
        } else {
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarRegistros() {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.query("Trabajadores", null, null, null, null, null, "FECHA DESC")

        val trabajadores = mutableListOf<Trabajador>()
        while (cursor.moveToNext()) {
            val rut = cursor.getString(cursor.getColumnIndexOrThrow("RUT"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE"))
            val apellidos = cursor.getString(cursor.getColumnIndexOrThrow("APELLIDOS"))
            val estado = cursor.getString(cursor.getColumnIndexOrThrow("ESTADO"))
            val fecha = cursor.getLong(cursor.getColumnIndexOrThrow("FECHA"))

            trabajadores.add(Trabajador(rut, nombre, apellidos, estado, fecha))
        }
        cursor.close()

        adapter = TrabajadorAdapter(trabajadores)
        rvRegistros.adapter = adapter
    }
}
