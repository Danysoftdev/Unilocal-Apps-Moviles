package co.edu.eam.unilocal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.models.Comentario

class ComentarioLugarAdapter(var lista:ArrayList<Comentario>):RecyclerView.Adapter<ComentarioLugarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_comentarios_lugar,parent,false)
        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }
    override fun getItemCount() = lista.size
    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView){

        val texto: TextView = itemView.findViewById(R.id.comentario)
        val usuario: TextView = itemView.findViewById(R.id.autor_comentario)
        val estrellas: TextView = itemView.findViewById(R.id.estrellas_comentario)


        fun bind(comentario: Comentario){
                val cal = "\uF005".repeat(comentario.calificaicon) //
                usuario.text = "DAVID" //Usuarios.buscar(comentario.idUsuario).nombre
                texto.text = comentario.texto
                estrellas.text = cal


        }
    }

}