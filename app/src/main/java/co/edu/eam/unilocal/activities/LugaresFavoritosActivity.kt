package co.edu.eam.unilocal.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityLugaresFavoritosBinding
import co.edu.eam.unilocal.databinding.ActivityMisLugaresBinding
import co.edu.eam.unilocal.models.Lugar

class LugaresFavoritosActivity : AppCompatActivity() {
    lateinit var binding: ActivityLugaresFavoritosBinding
    lateinit var listaLugaresFavoritos:ArrayList<Lugar>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLugaresFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaLugaresFavoritos = ArrayList()

        listaLugaresFavoritos = Usuarios.buscar(1).favoritos

        val adapter= LugarAdapter(listaLugaresFavoritos)

        binding.listaLugares.adapter = adapter
        binding.listaLugares.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }
}