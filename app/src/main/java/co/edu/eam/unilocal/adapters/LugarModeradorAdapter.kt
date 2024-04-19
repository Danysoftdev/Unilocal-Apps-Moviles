package co.edu.eam.unilocal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.models.Estado
import co.edu.eam.unilocal.models.Lugar

class LugarModeradorAdapter(var listaLugares: ArrayList<Lugar>, var codigoModerador: Int): RecyclerView.Adapter<LugarModeradorAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.item_lugar_revisar, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaLugares[position])
    }

    override fun getItemCount(): Int = listaLugares.size

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val nombreLugar: TextView = itemView.findViewById(R.id.nombre_lugar_revisar)
        val nombreModerador: TextView = itemView.findViewById(R.id.nombreModerador)
        val estado: TextView = itemView.findViewById(R.id.estadoSolicitud)
        val btnAprobar: ImageButton = itemView.findViewById(R.id.btnAprobar)
        val btnDesaprobar: ImageButton = itemView.findViewById(R.id.btnDesaprobar)
        var codigo: Int = 0

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(lugar: Lugar){
            nombreLugar.text = lugar.nombre

            val usuario = Usuarios.getById(codigoModerador)
            nombreModerador.text = usuario?.nombre
            estado.text = lugar.estado.name

            btnAprobar.setOnClickListener { lugar.estado = Estado.APROBADO }
            btnDesaprobar.setOnClickListener { lugar.estado = Estado.RECHAZADO }

            codigo = lugar.id
        }

        override fun onClick(v: View?) {
        }

    }
}