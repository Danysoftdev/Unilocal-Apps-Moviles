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
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Ciudades
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Comentario

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
        var codigoLugar : Int =0

        init{
            itemView.setOnClickListener(this)
            irAComentariosButton.setOnClickListener(this)
            btnEliminarLugar.setOnClickListener(this)
        }
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
            val calificacion = lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))
            for (i in 0..calificacion){
                (listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
            }
            /*calificacion.text = "$promedioFormateado $estrellas"
            calificacion.setTextColor(Color.YELLOW)*/
            comentario.text = comentarios.size.toString() +" comentarios"

            codigoLugar = lugar.id
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.nombre_lugar -> {
                    // Aquí puedes definir la acción para hacer clic en el nombre del lugar si lo necesitas
                }
                R.id.ir_comentarios_lugar -> {
                    val intent = Intent(itemView.context, ComentariosLugarActivity::class.java)
                    intent.putExtra("codigo", codigoLugar)
                    itemView.context.startActivity(intent)
                }
                R.id.btn_eliminar_lugar -> {
                    Lugares.eliminar(codigoLugar)
                    onLugarEliminadoListener?.onLugarEliminado()

                }
            }
        }

    }

}

