package co.edu.eam.unilocal.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Lugar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TopLugarAdapter (var lista:ArrayList<Lugar>): RecyclerView.Adapter<TopLugarAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lugar_top_semanal,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }



    inner class ViewHolder(var itemView: View):RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.nombre_lugar)
        private val categoria: TextView = itemView.findViewById(R.id.categoria_lugar)
        private val listaEstrellas: LinearLayout = itemView.findViewById(R.id.calificacion_lugar)
        private val comentario: TextView = itemView.findViewById(R.id.comentarios)
        private val direccion: TextView = itemView.findViewById(R.id.direccion_lugar)
        private val corazones: TextView = itemView.findViewById(R.id.corazones)

        fun bind(lugar: Lugar) {
            nombre.text = lugar.nombre
            direccion.text = lugar.direccion
            corazones.text = lugar.corazones.toString()

            Firebase.firestore.collection("categorias")
                .whereEqualTo("id", lugar.idCategoria)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val categoria = document.toObject(Categoria::class.java)
                        categoria.nombre?.let {
                            this.categoria.text = it
                        }
                    }
                }

            Firebase.firestore.collection("lugares")
                .document(lugar.key)
                .collection("comentarios")
                .get()
                .addOnSuccessListener { result ->
                    val comentarios = ArrayList<Comentario>()
                    var promedio = 0.0
                    var cantidad = 0
                    for (document in result) {
                        val comentario = document.toObject(Comentario::class.java)
                        comentarios.add(comentario)
                        promedio += comentario.calificaicon
                        cantidad++
                    }

                    val total = if (cantidad > 0) promedio / cantidad else 0.0
                    Log.e("total", total.toString())
                    val estrellas = total.toInt()
                    for (i in 0 until listaEstrellas.childCount) {
                        (listaEstrellas[i] as TextView).setTextColor(
                            if (i < estrellas) Color.YELLOW else Color.GRAY
                        )
                    }
                    comentario.text = comentarios.size.toString()
                }

        }
    }
}
