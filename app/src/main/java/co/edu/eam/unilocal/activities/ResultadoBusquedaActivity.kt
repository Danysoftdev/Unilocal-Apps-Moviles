package co.edu.eam.unilocal.activities

import android.os.Bundle
import android.util.Log
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
            listaLugares = Lugares.buscarNombre(textoBusqueda)
            Log.e("ResultadoBusquedaActivity", listaLugares.toString())
        }

        actualizarRecyclerView(listaLugares)
        binding.lugares.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    fun actualizarRecyclerView(listLugares: ArrayList<Lugar>) {
        binding.lugares.adapter = LugarBusquedaAdapter(listLugares)
    }

}