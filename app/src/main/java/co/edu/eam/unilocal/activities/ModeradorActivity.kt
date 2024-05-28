package co.edu.eam.unilocal.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapters.LugarModeradorAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityModeradorBinding
import co.edu.eam.unilocal.models.Estado
import co.edu.eam.unilocal.models.Lugar
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ModeradorActivity : AppCompatActivity() {

    lateinit var binding: ActivityModeradorBinding
    lateinit var listaLugares: ArrayList<Lugar>
    var codigoModerador: String = ""
    var vecesClick: Int = 0
    private var user: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityModeradorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = FirebaseAuth.getInstance().currentUser


        if (user!= null){
            codigoModerador = user!!.uid
            Log.e("ModeradorActivity", codigoModerador)
            listaLugares = ArrayList()
        }
        binding.lugaresRevisar.adapter = LugarModeradorAdapter(listaLugares, this)
        binding.lugaresRevisar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        Firebase.firestore.collection("lugares").get().addOnSuccessListener { result ->
            for (document in result) {
                val lugar = document.toObject(Lugar::class.java)
                lugar.key = document.id
                listaLugares.add(lugar)
            }
            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
        }

        //listaLugares.addAll(Lugares.listar())

        binding.aceptados.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                vecesClick++
                if (vecesClick >= 1){
                    listaLugares.clear()
                    Firebase.firestore.
                    collection("lugares").
                    whereEqualTo("estado", Estado.APROBADO).
                    get().addOnSuccessListener {documents ->
                        for(doc in documents){
                            val lugar = doc.toObject(Lugar::class.java)
                            lugar.key = doc.id
                            listaLugares.add(lugar)

                        }
                        binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
                    }.addOnFailureListener {
                        Log.e("Error", it.message.toString())
                    }
                    // listaLugares.addAll(Lugares.listarAprobados())
                }else{
                    listaLugares.clear()
                    Firebase.firestore.
                    collection("lugares").
                    whereEqualTo("estado", Estado.APROBADO).
                    get().addOnSuccessListener {
                        for(doc in it){
                            var lugar = doc.toObject(Lugar::class.java)
                            lugar.key = doc.id
                            listaLugares.add(lugar)
                            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
                        }
                    }.addOnFailureListener {
                        Log.e("Error", it.message.toString())
                    }
                    //listaLugares.addAll(Lugares.listarAprobados())
                }
            }else{
                Firebase.firestore.
                collection("lugares").
                whereEqualTo("estado", Estado.APROBADO).
                get().addOnSuccessListener {
                    for(doc in it){
                        var lugar = doc.toObject(Lugar::class.java)
                        listaLugares.remove(lugar)
                        binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
                    }
                }.addOnFailureListener {
                    Log.e("Error", it.message.toString())
                }
                // listaLugares.removeAll(Lugares.listarAprobados())
            }
            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
        }

        binding.pendientes.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                vecesClick++
                if (vecesClick >=1){
                    listaLugares.clear()
                    Firebase.firestore.
                    collection("lugares").
                    whereEqualTo("estado", Estado.PENDIENTE).
                    get().addOnSuccessListener {
                        for(doc in it){
                            var lugar = doc.toObject(Lugar::class.java)
                            lugar.key = doc.id
                            listaLugares.add(lugar)
                            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
                        }

                    }.addOnFailureListener {
                        Log.e("Error", it.message.toString())
                    }
                }else{

                    listaLugares.clear()
                    Firebase.firestore.
                    collection("lugares").
                    whereEqualTo("estado", Estado.PENDIENTE).
                    get().addOnSuccessListener {

                        for(doc in it){
                            var lugar = doc.toObject(Lugar::class.java)
                            lugar.key = doc.id
                            listaLugares.add(lugar)
                            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
                        }

                    }.addOnFailureListener {
                        Log.e("Error", it.message.toString())
                    }
                    //listaLugares.addAll(Lugares.listarPendientes())
                }
            }else{
                Firebase.firestore.
                collection("lugares").
                whereEqualTo("estado", Estado.PENDIENTE).
                get().addOnSuccessListener {

                    for(doc in it){
                        var lugar = doc.toObject(Lugar::class.java)
                        listaLugares.remove(lugar)
                        binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
                    }

                }.addOnFailureListener {
                    Log.e("Error", it.message.toString())
                }
                //listaLugares.removeAll(Lugares.listarPendientes())
            }
            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
        }

        binding.rechazados.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                vecesClick++
                if (vecesClick >= 1){
                    listaLugares.clear()
                    Firebase.firestore.
                    collection("lugares").
                    whereEqualTo("estado", Estado.RECHAZADO).
                    get().addOnSuccessListener {

                        for(doc in it){
                            var lugar = doc.toObject(Lugar::class.java)
                            lugar.key = doc.id
                            listaLugares.add(lugar)
                            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
                        }

                    }.addOnFailureListener {
                        Log.e("Error", it.message.toString())
                    }
                    //  listaLugares.addAll(Lugares.listarRechazados())
                }else{
                    listaLugares.clear()
                    Firebase.firestore.
                    collection("lugares").
                    whereEqualTo("estado", Estado.RECHAZADO).
                    get().addOnSuccessListener {

                        for(doc in it){
                            var lugar = doc.toObject(Lugar::class.java)
                            lugar.key = doc.id
                            listaLugares.add(lugar)
                        }
                    }.addOnFailureListener {
                        Log.e("Error", it.message.toString())
                    }
                    //  listaLugares.addAll(Lugares.listarRechazados())
                }
            }else{
                Firebase.firestore.
                collection("lugares").
                whereEqualTo("estado", Estado.RECHAZADO).
                get().addOnSuccessListener {

                    for(doc in it){
                        var lugar = doc.toObject(Lugar::class.java)
                        listaLugares.remove(lugar)
                    }

                }.addOnFailureListener {
                    Log.e("Error", it.message.toString())
                }
                //listaLugares.removeAll(Lugares.listarRechazados())
            }
            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
        }
        binding.btnSesion.setOnClickListener {
            limpiarSesion()
        }

        Log.e("ModeradorActivity", listaLugares.toString())



    }
    fun limpiarSesion() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, MainActivity::class.java))

    }

}