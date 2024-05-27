package co.edu.eam.unilocal.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarFavoritoAdapter
import co.edu.eam.unilocal.databinding.FragmentMisFavoritosBinding
import co.edu.eam.unilocal.models.Lugar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MisFavoritosFragment : Fragment(), LugarFavoritoAdapter.OnLugarEliminadoListener {

    private var listaLugaresFavoritos: ArrayList<Lugar> = ArrayList()
    private lateinit var binding: FragmentMisFavoritosBinding
    private var user: FirebaseUser? = null
    private lateinit var adapter: LugarFavoritoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisFavoritosBinding.inflate(inflater, container, false)

        adapter = LugarFavoritoAdapter(listaLugaresFavoritos)
        adapter.setOnLugarEliminadoListener(this)

        binding.listaFavoritos.layoutManager = LinearLayoutManager(requireActivity())
        binding.listaFavoritos.adapter = adapter

        user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            actualizarListaLugares()
        }
        return binding.root
    }

    private fun actualizarListaLugares() {
        Firebase.firestore.collection("usuarios")
            .document(user!!.uid)
            .collection("favoritos")
            .get()
            . addOnSuccessListener {
                listaLugaresFavoritos.clear() // Limpiar la lista antes de agregar nuevos datos
                for( doc in it ){
                    Firebase.firestore
                        .collection("lugares")
                        .document( doc.id )
                        .get()
                        .addOnSuccessListener { l ->
                            val lugar = l.toObject(Lugar::class.java)
                            if (lugar != null) {
                                lugar.key = l.id
                                listaLugaresFavoritos.add(lugar)
                                adapter.notifyDataSetChanged()
                            }
                        }



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


    }

    override fun onLugarEliminado() {
        actualizarListaLugares()
        Toast.makeText(requireActivity(), "Se eliminó correctamente", Toast.LENGTH_LONG).show()

    }
}
