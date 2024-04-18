package co.edu.eam.unilocal.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.models.Comentario

class ComentarioAdapter(var listaComentarios: ArrayList<Comentario>): RecyclerView.Adapter<ComentarioAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ComentarioAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{





        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

    }


}