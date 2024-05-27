package co.edu.eam.unilocal.adapters

import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.ModeradorActivity
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.models.Estado
import co.edu.eam.unilocal.models.Lugar
import co.edu.eam.unilocal.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class LugarModeradorAdapter(var listaLugares: ArrayList<Lugar>, val activity: AppCompatActivity): RecyclerView.Adapter<LugarModeradorAdapter.ViewHolder>() {
    private var user: FirebaseUser? = null

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
        var codigo: String = ""

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(lugar: Lugar){
            nombreLugar.text = lugar.nombre
            user =  FirebaseAuth.getInstance().currentUser
            Firebase.firestore.collection("usuarios").document(user!!.uid).get().addOnSuccessListener {
                val usuario = it.toObject(Usuario::class.java)
                nombreModerador.text = usuario?.nombre
            }
            //val usuario = Usuario()// Usuarios.getById(codigoModerador)
           // nombreModerador.text = usuario?.nombre
            estado.text = lugar.estado.name
            codigo = lugar.key
            btnAprobar.setOnClickListener {
                lugar.estado = Estado.APROBADO
                Firebase.firestore.collection("lugares").document(codigo).set(lugar)
                    .addOnSuccessListener {
                        activity.recreate()
                    }

            }
            btnDesaprobar.setOnClickListener {
                lugar.estado = Estado.RECHAZADO
                Firebase.firestore.collection("lugares").document(codigo).set(lugar)
                    .addOnSuccessListener {
                        activity.recreate()

                    }
            }

        }

        override fun onClick(v: View?) {


        }

    }
}