package com.iacc.manuelroa_20240928

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrabajadorAdapter(private val trabajadores: List<Trabajador>) : RecyclerView.Adapter<TrabajadorAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRUT: TextView = itemView.findViewById(R.id.tvRUT)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvApellidos: TextView = itemView.findViewById(R.id.tvApellidos)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        val btnCompartir: Button = itemView.findViewById(R.id.btnCompartir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_registro, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trabajador = trabajadores[position]

        holder.tvRUT.text = trabajador.rut
        holder.tvNombre.text = trabajador.nombre
        holder.tvApellidos.text = trabajador.apellidos
        holder.tvEstado.text = trabajador.estado
        

        holder.btnCompartir.setOnClickListener {
            val compartirIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Registro: ${trabajador.rut}, ${trabajador.nombre} ${trabajador.apellidos}, Estado: ${trabajador.estado}")
                type = "text/plain"
            }
            holder.itemView.context.startActivity(Intent.createChooser(compartirIntent, "Compartir registro"))
        }
    }

    override fun getItemCount(): Int {
        return trabajadores.size
    }
}
