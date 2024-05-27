package co.edu.eam.unilocal.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapters.LugarBusquedaAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityResultadoBusquedaBinding
import co.edu.eam.unilocal.models.Lugar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ResultadoBusquedaActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultadoBusquedaBinding
    var textoBusqueda: String = ""
    lateinit var listaLugares: ArrayList<Lugar>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultadoBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textoBusqueda = intent.extras!!.getString("textoBusqueda", "")
        listaLugares = ArrayList()

        if (textoBusqueda.isNotEmpty()){
            Firebase.firestore
                .collection("lugares")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents){
                        val lugar = document.toObject(Lugar::class.java)
                        if (lugar.nombre.lowercase().contains(textoBusqueda.lowercase())){
                            listaLugares.add(lugar)
                        }
                    }

                    Log.e("ResultadoBusquedaActivity", listaLugares.toString())

                    actualizarRecyclerView(listaLugares)
                    binding.lugares.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                }
                .addOnFailureListener {
                    Log.e("ResultadoLugares", "Problemas")
                    Toast.makeText(this, getString(R.string.no_coincidencias_lugares), Toast.LENGTH_LONG).show()
                }
        }

    }

    fun actualizarRecyclerView(listLugares: ArrayList<Lugar>) {
        binding.lugares.adapter = LugarBusquedaAdapter(listLugares)
    }

}