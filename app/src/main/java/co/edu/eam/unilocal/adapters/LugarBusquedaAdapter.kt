package co.edu.eam.unilocal.adapters

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.models.Lugar
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.DetalleLugarActivity
import com.bumptech.glide.Glide

class LugarBusquedaAdapter(var listaLugares: ArrayList<Lugar>): RecyclerView.Adapter<LugarBusquedaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.item_lugar_busqueda, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaLugares[position])
    }

    override fun getItemCount() = listaLugares.size

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val nombre: TextView = itemView.findViewById(R.id.nombre_lugar)
        val listaEstrellas: LinearLayout =  itemView.findViewById(R.id.calificacionPromedio)
        val cantidadComentarios: TextView = itemView.findViewById(R.id.cantidadCom)
        val direccion: TextView = itemView.findViewById(R.id.direccion_lugar)
        val estado: TextView = itemView.findViewById(R.id.estado_lugar)
        val horario: TextView = itemView.findViewById(R.id.horario_lugar)
        val imagen: ImageView = itemView.findViewById(R.id.imagen_lugar)
        var codigo: String = ""

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(lugar: Lugar){

            nombre.text = lugar.nombre

            val calificacion = 2//lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))
            for (i in 0..calificacion){
                (listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
            }
            //cantidadComentarios.text = "(${Comentarios.obtenerCantidadComentarios(lugar.id)})"

            direccion.text = lugar.direccion

            estado.text = lugar.verificarEstadoHorario()
            if (lugar.verificarEstadoHorario() == "Abierto"){
                estado.setTextColor(Color.GREEN)
                horario.text = lugar.obtenerHoraCierre()
            }else{
                estado.setTextColor(Color.RED)
                horario.text = lugar.obtenerHoraApertura()
            }
            codigo = lugar.key

            if (lugar.imagenes.size > 0){
                Glide.with( itemView )
                    .load(lugar.imagenes[0])
                    .into(imagen)
            }
        }

        override fun onClick(v: View?) {
            val intent = Intent(nombre.context, DetalleLugarActivity::class.java)
            intent.putExtra("codigoLugar", codigo)
            nombre.context.startActivity(intent)
        }

    }
}