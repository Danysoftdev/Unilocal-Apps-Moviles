package co.edu.eam.unilocal.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.databinding.FragmentInfoLugarBinding
import co.edu.eam.unilocal.models.Lugar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InfoLugarFragment : Fragment() {

    private lateinit var binding: FragmentInfoLugarBinding
    private var codigoLugar: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            codigoLugar = it.getString("codigoLugar", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoLugarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarLugar()
    }

    private fun cargarLugar() {
        if (codigoLugar.isNotEmpty()) {
            Firebase.firestore.collection("lugares").document(codigoLugar)
                .get()
                .addOnSuccessListener { document ->
                    val lugar = document.toObject(Lugar::class.java)
                    lugar?.let {
                        binding.ubicacionLugar.text = it.direccion

                        val horariosBuilder = StringBuilder()
                        for (horario in it.horarios) {
                            for (dia in horario.diasSemana) {
                                horariosBuilder.append("$dia: ${horario.horaInicio}:00 - ${horario.horaCierre}:00\n")
                            }
                        }
                        binding.horariosLugar.text = horariosBuilder.toString()
                        binding.descripcionLugar.text = it.descripcion
                        val telefonosBuilder = StringBuilder()
                        for (tel in it.telefonos) {
                                telefonosBuilder.append("$tel, ")
                        }
                        binding.telefonoLugar.text = telefonosBuilder.toString()


                    } ?: run {
                        Toast.makeText(requireContext(), "Lugar no encontrado", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("InfoLugarFragment", "Error al obtener el lugar", exception)
                    Toast.makeText(requireContext(), "Error al cargar el lugar", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(requireContext(), "Código de lugar no proporcionado", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        fun newInstance(codigoLugar: String) = InfoLugarFragment().apply {
            arguments = Bundle().apply {
                putString("codigoLugar", codigoLugar)
            }
        }
    }
}
