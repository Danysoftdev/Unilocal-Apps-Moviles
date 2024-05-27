package co.edu.eam.unilocal.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.adapter.LugarFavoritoAdapter
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityLugaresFavoritosBinding
import co.edu.eam.unilocal.models.Lugar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
/*
class LugaresFavoritosActivity : AppCompatActivity(), LugarFavoritoAdapter.OnLugarEliminadoListener {
    /*lateinit var binding: ActivityLugaresFavoritosBinding
    lateinit var listaLugaresFavoritos:ArrayList<Lugar>
    private var user: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLugaresFavoritosBinding.inflate(layoutInflater)

        user = FirebaseAuth.getInstance().currentUser
        setContentView(binding.root)

            Firebase.firestore.collection("usuarios")
                .document(user!!.uid)
                .collection("favoritos")
                .get()
                . addOnSuccessListener { result ->
                    listaLugaresFavoritos.clear()
                    for (document in result) {
                        val lugar = document.toObject(Lugar::class.java)
                        listaLugaresFavoritos.add(lugar)
                    }
                    if (listaLugaresFavoritos.isEmpty()) {
                        binding.mensajeVacioFavoritos.visibility = View.VISIBLE
                    } else {
                        binding.mensajeVacioFavoritos.visibility = View.GONE

                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("MisFavoritosFragment", "Error obteniendo lugares favoritos", exception)
                }


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
    }*/
*/