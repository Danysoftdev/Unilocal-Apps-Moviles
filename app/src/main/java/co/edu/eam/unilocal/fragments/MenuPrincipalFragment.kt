package co.edu.eam.unilocal.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.ResultadoBusquedaActivity
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentMenuPrincipalBinding
import co.edu.eam.unilocal.models.Lugar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MenuPrincipalFragment : Fragment() {

    lateinit var binding:FragmentMenuPrincipalBinding
    lateinit var listaLugares: ArrayList<Lugar>
    private var resultadoBusquedaActivity: ResultadoBusquedaActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuPrincipalBinding.inflate(inflater, container, false)

        listaLugares = ArrayList()

        binding.txtBusqueda.setOnEditorActionListener { textView, actionId, keyEvent ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){

                val busqueda = binding.txtBusqueda.text.toString()

                if (busqueda.isNotEmpty()){


                    if (resultadoBusquedaActivity != null) {

                        Firebase.firestore
                            .collection("lugares")
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents){
                                    val lugar = document.toObject(Lugar::class.java)
                                    if (lugar.nombre.lowercase().contains(busqueda.lowercase())){
                                        lugar.key = document.id
                                        listaLugares.add(lugar)
                                    }
                                }

                                Log.e("ResultadoMenu", listaLugares.toString())

                                resultadoBusquedaActivity?.actualizarRecyclerView(listaLugares)
                            }

                    } else {

                        val intent = Intent(activity, ResultadoBusquedaActivity::class.java)
                        intent.putExtra("textoBusqueda", busqueda)
                        startActivity(intent)
                    }

                }
            }
            true
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ResultadoBusquedaActivity) {
            resultadoBusquedaActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        resultadoBusquedaActivity = null
    }
}