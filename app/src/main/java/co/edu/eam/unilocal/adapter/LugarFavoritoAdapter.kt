package co.edu.eam.unilocal.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Comentario
import co.edu.eam.unilocal.models.Lugar
import com.bumptech.glide.Glide
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
        val imagen: ImageView = itemView.findViewById(R.id.img_mis_favoritos)
        val nombre: TextView = itemView.findViewById(R.id.nombre_lugar_fav)
        val categoria: TextView = itemView.findViewById(R.id.categoria_lugar_fav)
        val direccion:TextView= itemView.findViewById(R.id.direccion_lugar_fav)
        private val listaEstrellas: LinearLayout = itemView.findViewById(R.id.calificacion_lugar_fav)
        val btnEliminarLugar : Button = itemView.findViewById(R.id.btn_eliminar_favorito)
        private val comentario: TextView = itemView.findViewById(R.id.comentarios_fav)
        var codigoLugar : String =""
        val prom: TextView = itemView.findViewById(R.id.calificacion_promedio_fav)
        init{
            itemView.setOnClickListener(this)
            btnEliminarLugar.setOnClickListener(this)
        }
        fun bind(lugar: Lugar){
            Log.d("LugarFavoritoAdapter", "Binding lugar: ${lugar.nombre}, ${lugar.direccion}")
            nombre.text = lugar.nombre
            direccion.text = lugar.direccion
            codigoLugar = lugar.key

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
                    prom.text = total.toString()
                    comentario.text = "("+comentarios.size.toString()+")"
                }

            Glide.with( itemView )
                .load(lugar.imagenes[0])
                .into(imagen)


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