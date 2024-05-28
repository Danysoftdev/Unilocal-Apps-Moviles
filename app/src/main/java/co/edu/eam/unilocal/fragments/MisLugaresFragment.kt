package co.edu.eam.unilocal.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.CrearLugarActivity
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.databinding.FragmentMisLugaresBinding
import co.edu.eam.unilocal.models.Lugar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MisLugaresFragment : Fragment(), LugarAdapter.OnLugarEliminadoListener {
    private var listaLugares: ArrayList<Lugar> = ArrayList()
    private lateinit var binding: FragmentMisLugaresBinding
    private lateinit var adapter: LugarAdapter
    private var user: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisLugaresBinding.inflate(inflater, container, false)

        // Inicializar RecyclerView y Adapter
        adapter = LugarAdapter(listaLugares)
        adapter.setOnLugarEliminadoListener(this)

        binding.listaMisLugares.layoutManager = LinearLayoutManager(requireActivity())
        binding.listaMisLugares.adapter = adapter

        // Botón para crear lugar
        binding.btnCrearLugares.setOnClickListener { irCrearLugar() }

        user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            cargarLugares()
        } else {
            binding.mensajeVacioLugares.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (user != null) {
            cargarLugares()
        }
    }

    private fun cargarLugares() {
        Firebase.firestore
            .collection("lugares")
            .whereEqualTo("idCreador", user?.uid)
            .get()
            .addOnSuccessListener { documents ->
                listaLugares.clear() // Limpiar la lista antes de agregar nuevos datos
                for (doc in documents) {
                    val lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id
                    listaLugares.add(lugar)
                }
                adapter.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
                if (listaLugares.isEmpty()) {
                    binding.mensajeVacioLugares.visibility = View.VISIBLE
                } else {
                    binding.mensajeVacioLugares.visibility = View.GONE
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MisLugaresFragment", "Error al cargar lugares: ", exception)
                Toast.makeText(requireActivity(), "Error al cargar lugares", Toast.LENGTH_LONG).show()
            }
    }

    private fun irCrearLugar() {
        startActivity(Intent(activity, CrearLugarActivity::class.java))
    }

    override fun onLugarEliminado() {
        cargarLugares() // Recargar la lista de lugares después de eliminar
        Toast.makeText(requireActivity(), getText(R.string.txt_lugar_eliminado), Toast.LENGTH_LONG).show()
    }
}
