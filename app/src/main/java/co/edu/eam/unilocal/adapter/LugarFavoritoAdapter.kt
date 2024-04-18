package co.edu.eam.unilocal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Comentario
import co.edu.eam.unilocal.models.Lugar

class LugarFavoritoAdapter(var lista:ArrayList<Lugar>): RecyclerView.Adapter<LugarFavoritoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lugar_favorito,parent,false)
        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }
    override fun getItemCount() = lista.size
    inner class ViewHolder(var itemView: View):RecyclerView.ViewHolder(itemView){
        val nombre: TextView = itemView.findViewById(R.id.nombre_lugar)
        val categoria: TextView = itemView.findViewById(R.id.categoria_lugar)
        val calificacion: TextView = itemView.findViewById(R.id.calificacion_lugar)
        val comentario: TextView = itemView.findViewById(R.id.comentarios_lugar)
        fun bind(lugar: Lugar){
            val cate : Categoria? = Categorias.obtener(lugar.idCategoria)
            val comentarios : ArrayList<Comentario> = Comentarios.listar(lugar.id)
            val promedio = Comentarios.calcularPromedioCalificacion(lugar.id)
            val estrellas = "\uF005".repeat(promedio.toInt()) //
            val promedioFormateado = String.format("%.1f", promedio)

            nombre.text = lugar.nombre
            if (cate != null) {
                categoria.text= cate.nombre
            }
            calificacion.text = "$promedioFormateado $estrellas"
            comentario.text = comentarios.size.toString() +" comentarios"

        }
    }
}