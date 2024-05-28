package co.edu.eam.unilocal.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.models.Comentario
import co.edu.eam.unilocal.models.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
        val autor: TextView = itemView.findViewById(R.id.autor_comentario)
        //val estrellas: TextView = itemView.findViewById(R.id.estrellas_comentario)
        val fecha : TextView = itemView.findViewById(R.id.fecha_comentario)
        private val listaEstrellas: LinearLayout = itemView.findViewById(R.id.calificacion_comentario)


        fun bind(comentario: Comentario){

            Firebase.firestore.collection("usuarios").
            whereEqualTo("uid", comentario.idUsuario)
                .get()
                .addOnSuccessListener {
                    for (document in it){
                        val usuario = document.toObject(Usuario::class.java)
                        usuario.key = document.id
                        autor.text = usuario.nombre
                    }
                }
            val cal = "\uF005".repeat(comentario.calificaicon) //

            texto.text = comentario.texto
            val estrellas = comentario.calificaicon
            for (i in 0 until listaEstrellas.childCount) {
                (listaEstrellas[i] as TextView).setTextColor(
                    if (i < estrellas) Color.YELLOW else Color.GRAY
                )
            }
            // estrellas.text = cal
            //estrellas.setTextColor(Color.YELLOW)
            fecha.text = comentario.fecha.toString()


        }
    }

}