package co.edu.eam.unilocal.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Lugar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LugarFavoritoAdapter(var lista:ArrayList<Lugar>): RecyclerView.Adapter<LugarFavoritoAdapter.ViewHolder>() {
    private var onLugarEliminadoListener: OnLugarEliminadoListener? = null

    fun setOnLugarEliminadoListener(listener: OnLugarEliminadoListener) {
        onLugarEliminadoListener = listener
    }
    interface OnLugarEliminadoListener {
        fun onLugarEliminado()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lugar_favorito,parent,false)
        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }
    override fun getItemCount() = lista.size
    inner class ViewHolder(var itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nombre: TextView = itemView.findViewById(R.id.nombre_lugar)
        val categoria: TextView = itemView.findViewById(R.id.categoria_lugar)
        val direccion:TextView= itemView.findViewById(R.id.direccion_lugar)
        //val calificacion: TextView = itemView.findViewById(R.id.calificacion_lugar)
        //val comentario: TextView = itemView.findViewById(R.id.comentarios_lugar)
        val btnEliminarLugar : Button = itemView.findViewById(R.id.btn_eliminar_favorito)
        var codigoLugar : String =""
        init{
            itemView.setOnClickListener(this)

            btnEliminarLugar.setOnClickListener(this)
        }
        fun bind(lugar: Lugar){
            val cate : Categoria? = Categoria()//Categorias.obtener(lugar.idCategoria)
            //val comentarios : ArrayList<Comentario> = Comentarios.listar(lugar.id)
          //  val promedio = Comentarios.calcularPromedioCalificacion(lugar.id)
           /* val estrellas = "\uF005".repeat(promedio.toInt()) //
            val promedioFormateado = String.format("%.1f", promedio)*/
            val listaEstrellas: LinearLayout = itemView.findViewById(R.id.calificacion_lugar)
            val calificacion = 2//lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))

            for (i in 0..calificacion){
                (listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
            }
            nombre.text = lugar.nombre
            if (cate != null) {
                categoria.text= cate.nombre
            }
            direccion.text = lugar.direccion

            //calificacion.text = "$promedioFormateado $estrellas"
            //comentario.text = comentarios.size.toString() +" comentarios"
            codigoLugar = lugar.key

        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.btn_eliminar_favorito -> {
                    val contexto = v.context
                    val user = FirebaseAuth.getInstance().currentUser
                    var codigoUsuario :String = ""


                    if (user != null) {
                        codigoUsuario = user.uid

                        Firebase.firestore.collection("usuarios")
                            .document(codigoUsuario)
                            .collection("favoritos")
                            .document(codigoLugar)
                            .delete()
                        onLugarEliminadoListener?.onLugarEliminado()
                    }
                    println("Eliminar lugar favorito" + codigoUsuario + "ID" + codigoLugar)


                }
            }
        }
    }
}