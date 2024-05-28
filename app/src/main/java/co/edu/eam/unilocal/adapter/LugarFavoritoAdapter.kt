package co.edu.eam.unilocal.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Lugar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LugarFavoritoAdapter(var lista:ArrayList<Lugar>, private val fragment: Fragment): RecyclerView.Adapter<LugarFavoritoAdapter.ViewHolder>() {
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
        val nombre: TextView = itemView.findViewById(R.id.nombre_lugar_fav)
        val categoria: TextView = itemView.findViewById(R.id.categoria_lugar_fav)
        val direccion:TextView= itemView.findViewById(R.id.direccion_lugar_fav)
        //val calificacion: TextView = itemView.findViewById(R.id.calificacion_lugar)
//        val comentario: TextView = itemView.findViewById(R.id.comentarios_lugar)
        val btnEliminarLugar : Button = itemView.findViewById(R.id.btn_eliminar_favorito)
        var codigoLugar : String =""
        init{
            itemView.setOnClickListener(this)
            btnEliminarLugar.setOnClickListener(this)
        }
        fun bind(lugar: Lugar){
            Log.d("LugarFavoritoAdapter", "Binding lugar: ${lugar.nombre}, ${lugar.direccion}")
            //val cate : Categoria? = Categoria()//Categorias.obtener(lugar.idCategoria)
            //val comentarios : ArrayList<Comentario> = Comentarios.listar(lugar.id)
          //  val promedio = Comentarios.calcularPromedioCalificacion(lugar.id)
           /* val estrellas = "\uF005".repeat(promedio.toInt()) //
            val promedioFormateado = String.format("%.1f", promedio)*/
           // val listaEstrellas: LinearLayout = itemView.findViewById(R.id.calificacion_lugar)
            //val calificacion = 2//lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))

           /* for (i in 0..calificacion){
                (listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
            }*/

            nombre.text = lugar.nombre

            nombre.text = lugar.nombre.toUpperCase()

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
            /*Firebase.firestore.collection("comentarios")
                .whereEqualTo("idLugar", lugar.key)
                .get()
                .addOnSuccessListener {
                    comentario.text = it.size().toString() +" comentarios"
                }*/
            direccion.text = lugar.direccion

            //calificacion.text = "$promedioFormateado $estrellas"
            //comentario.text = comentarios.size.toString() +" comentarios"
            codigoLugar = lugar.key

        }
        private fun recargarFragmento() {
            val fragmentManager = fragment.parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.detach(fragment)
            fragmentTransaction.attach(fragment)
            fragmentTransaction.commit()
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.btn_eliminar_favorito -> {

                    val user = FirebaseAuth.getInstance().currentUser
                    var codigoUsuario :String = ""


                    if (user != null) {
                        codigoUsuario = user.uid

                        Firebase.firestore.collection("usuarios")
                            .document(codigoUsuario)
                            .collection("favoritos")
                            .document(codigoLugar)
                            .delete()

                            .addOnSuccessListener {
                                onLugarEliminadoListener?.onLugarEliminado()


                            .addOnSuccessListener {

                                Firebase.firestore
                                    .collection("lugares")
                                    .document(codigoLugar)
                                    .get()
                                    .addOnSuccessListener { document ->
                                        val lugar = document.toObject(Lugar::class.java)
                                        lugar?.let {
                                            lugar.corazones--
                                            Firebase.firestore
                                                .collection("lugares")
                                                .document(codigoLugar)
                                                .set(lugar)
                                        }
                                    }
                                onLugarEliminadoListener?.onLugarEliminado()
                                recargarFragmento()

                            }

                    }


                }
            }
        }
    }
}