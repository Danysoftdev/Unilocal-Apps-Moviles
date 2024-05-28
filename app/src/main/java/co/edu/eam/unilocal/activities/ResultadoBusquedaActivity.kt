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
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ResultadoBusquedaActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultadoBusquedaBinding
    var textoBusqueda: String = ""
    lateinit var listaLugares: ArrayList<Lugar>
    lateinit var categoriasMap: MutableMap<Int, String>
    lateinit var selectedCategories: HashSet<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultadoBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textoBusqueda = intent.extras!!.getString("textoBusqueda", "")
        listaLugares = ArrayList()
        categoriasMap = mutableMapOf()
        selectedCategories = HashSet()

        loadCategories()
        loadLugares()

    }

    private fun loadCategories() {
        Firebase.firestore.collection("categorias")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val categoriaId = document.getLong("id")?.toInt()
                    val categoriaNombre = document.getString("nombre")
                    if (categoriaId != null && categoriaNombre != null) {
                        categoriasMap[categoriaId] = categoriaNombre
                        addChipToGroup(categoriaId, categoriaNombre)
                    }
                }
            }
            .addOnFailureListener {
                Log.e("ResultadoBusquedaActivity", "Error al cargar categorías")
                Toast.makeText(this, "Error al cargar categorías", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadLugares (){
        if (textoBusqueda.isNotEmpty()){
            Firebase.firestore
                .collection("lugares")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents){
                        val lugar = document.toObject(Lugar::class.java)
                        lugar.key = document.id
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

    private fun addChipToGroup(categoriaId: Int, categoriaNombre: String) {
        val chip = Chip(this)
        chip.text = categoriaNombre
        chip.isCheckable = true
        chip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedCategories.add(categoriaId)
            } else {
                selectedCategories.remove(categoriaId)
            }
            filterLugares()
        }
        binding.categoriaChips.addView(chip)
    }

    private fun filterLugares() {
        var listaFiltrada: ArrayList<Lugar> = ArrayList()
        val filteredLugares = listaLugares.filter { lugar ->
            selectedCategories.isEmpty() || selectedCategories.contains(lugar.idCategoria)
        }

        listaFiltrada = filteredLugares as ArrayList<Lugar>
        actualizarRecyclerView(listaFiltrada)
    }


    fun actualizarRecyclerView(listaLugares: ArrayList<Lugar>) {
        binding.lugares.adapter = LugarBusquedaAdapter(listaLugares)
    }

}