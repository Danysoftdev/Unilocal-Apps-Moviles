package co.edu.eam.unilocal.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.adapter.LugarFavoritoAdapter
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityLugaresFavoritosBinding
import co.edu.eam.unilocal.models.Lugar

class LugaresFavoritosActivity : AppCompatActivity(), LugarFavoritoAdapter.OnLugarEliminadoListener {
    lateinit var binding: ActivityLugaresFavoritosBinding
    lateinit var listaLugaresFavoritos:ArrayList<Lugar>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLugaresFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaLugaresFavoritos = ArrayList()

        listaLugaresFavoritos = Usuarios.buscar(1).favoritos

        if(listaLugaresFavoritos.isEmpty()){
            binding.mensajeVacioFavoritos.visibility = View.VISIBLE
        }else {
            binding.mensajeVacioFavoritos.visibility = View.GONE

            val adapter = LugarFavoritoAdapter(listaLugaresFavoritos)
            adapter.setOnLugarEliminadoListener(this)
            binding.listaLugares.adapter = adapter
            binding.listaLugares.layoutManager = LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onLugarEliminado() {
        recreate()
        Toast.makeText(this,"SE ELIMINÓ CORRECTAMENTE", Toast.LENGTH_LONG).show()
    }
    }
