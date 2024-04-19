package co.edu.eam.unilocal.adapters

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.models.Comentario
import com.google.android.material.textfield.TextInputEditText

class ComentarioAdapter(val context: Context, var listaComentarios: ArrayList<Comentario>): RecyclerView.Adapter<ComentarioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ComentarioAdapter.ViewHolder, position: Int) {
        holder.bind(listaComentarios[position])
    }

    override fun getItemCount(): Int = listaComentarios.size

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val nombrePersona: TextView = itemView.findViewById(R.id.nombre_persona_comentario)
        val fechaComentario: TextView = itemView.findViewById(R.id.fecha_comentario)
        val descripcion: TextView = itemView.findViewById(R.id.comentario)

        fun bind(comentario: Comentario){
            val nombre = Usuarios.getNameById(comentario.idUsuario)
            nombrePersona.text = nombre

            val layoutEstrellas: LinearLayout = itemView.findViewById(R.id.estrellas)

            for (i in 0 until  comentario.calificaicon){
                val viewText = TextView(context)
                viewText.text = "\uf005"
                viewText.typeface = ResourcesCompat.getFont(context, R.font.font_awesome_6_free_solid_900)
                viewText.textSize = 15f
                viewText.setTextColor(Color.YELLOW)

                layoutEstrellas.addView(viewText)

            }

            fechaComentario.text = comentario.fecha
            descripcion.text = comentario.texto
        }

        override fun onClick(v: View?) {
        }

    }


}