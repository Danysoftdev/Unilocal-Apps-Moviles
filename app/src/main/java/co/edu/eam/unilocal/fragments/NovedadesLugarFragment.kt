package co.edu.eam.unilocal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentNovedadesLugarBinding
import co.edu.eam.unilocal.models.Lugar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NovedadesLugarFragment : Fragment() {

    lateinit var binding: FragmentNovedadesLugarBinding
    private var lugar: Lugar? = null
    private var codigoLugar: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null){
            codigoLugar = requireArguments().getString("codigoLugar","")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNovedadesLugarBinding.inflate(inflater, container, false)


        Firebase.firestore.collection("lugares").document(codigoLugar)
            .get()
            .addOnSuccessListener {
                lugar = it.toObject(Lugar::class.java)
                binding.txtNovedades.text = lugar?.novedades
            }



        return binding.root
    }

    companion object{
        fun newInstance(codigoLugar: String): NovedadesLugarFragment{
            val args = Bundle()
            args.putString("codigoLugar", codigoLugar)
            val fragmento = NovedadesLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}