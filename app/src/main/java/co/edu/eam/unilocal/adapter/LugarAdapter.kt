package co.edu.eam.unilocal.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.models.Lugar
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.ComentariosLugarActivity
import co.edu.eam.unilocal.activities.DetalleLugarActivity
import co.edu.eam.unilocal.models.Categoria
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LugarAdapter (var lista:ArrayList<Lugar>):RecyclerView.Adapter<LugarAdapter.ViewHolder>(){
    private var onLugarEliminadoListener: OnLugarEliminadoListener? = null

    fun setOnLugarEliminadoListener(listener: OnLugarEliminadoListener) {
        onLugarEliminadoListener = listener
    }
    interface OnLugarEliminadoListener {
        fun onLugarEliminado()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lugar,parent,false)
        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }
    override fun getItemCount() = lista.size
    inner class ViewHolder(var itemView: View):RecyclerView.ViewHolder(itemView),OnClickListener{
        val nombre:TextView= itemView.findViewById(R.id.nombre_lugar)
        val categoria:TextView= itemView.findViewById(R.id.categoria_lugar)
        val listaEstrellas: LinearLayout = itemView.findViewById(R.id.calificacion_lugar)
        val comentario:TextView= itemView.findViewById(R.id.comentarios_lugar)
        val irAComentariosButton: Button = itemView.findViewById(R.id.ir_comentarios_lugar)
        val btnEliminarLugar : Button = itemView.findViewById(R.id.btn_eliminar_lugar)
        var codigoLugar : String =""

        init{
            itemView.setOnClickListener(this)
            nombre.setOnClickListener(this)
            irAComentariosButton.setOnClickListener(this)
            btnEliminarLugar.setOnClickListener(this)
        }
        fun bind(lugar: Lugar){

            Firebase.firestore.collection("categorias")
                .whereEqualTo("id", lugar.idCategoria)
                .get()
                .addOnSuccessListener {
                    for (document in it) {
                        val categoria = document.toObject(Categoria::class.java)
                        categoria.nombre?.let { categoria ->
                            this.categoria.text = categoria
                        }
                    }
                }
           Firebase.firestore.collection("comentarios")
               .whereEqualTo("idLugar", lugar.key)
               .get()
               .addOnSuccessListener {
                   comentario.text = it.size().toString() +" comentarios"
               }
            //val comentarios : ArrayList<Comentario> = Comentarios.listar(lugar.id)
           // val promedio = Comentarios.calcularPromedioCalificacion(lugar.id)

           // val estrellas = "\uF005".repeat(promedio.toInt()) //
           // val promedioFormateado = String.format("%.1f", promedio)

            nombre.text = lugar.nombre

            val calificacion = lugar.obtenerCalificacionPromedio(ArrayList())
            for (i in 0..calificacion){
                (listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
            }
            /*calificacion.text = "$promedioFormateado $estrellas"
            calificacion.setTextColor(Color.YELLOW)*/
            //comentario.text = comentarios.size.toString() +" comentarios"

            codigoLugar = lugar.key
        }

        override fun onClick(v: View?) {

            when (v?.id) {
                R.id.nombre_lugar -> {
                    val intent = Intent(itemView.context, DetalleLugarActivity::class.java)
                    intent.putExtra("codigoLugar", codigoLugar)
                    itemView.context.startActivity(intent)
                }
                R.id.ir_comentarios_lugar -> {
                    val intent = Intent(itemView.context, ComentariosLugarActivity::class.java)
                    intent.putExtra("codigo", codigoLugar)
                    itemView.context.startActivity(intent)
                }
                R.id.btn_eliminar_lugar -> {
                    Firebase.firestore.collection("lugares")
                        .document(codigoLugar)
                        .delete()
                    onLugarEliminadoListener?.onLugarEliminado()

                }
            }
        }

    }

}

