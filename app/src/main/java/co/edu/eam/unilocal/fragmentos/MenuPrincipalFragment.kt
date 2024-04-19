package co.edu.eam.unilocal.fragmentos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.FragmentMenuPrincipalBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuPrincipalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuPrincipalFragment : Fragment() {

    lateinit var binding:FragmentMenuPrincipalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuPrincipalBinding.inflate(inflater, container, false)

        binding.txtBusqueda.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){

                val busqueda = binding.txtBusqueda.text.toString()

                if (busqueda.isNotEmpty()){
                    val intent = Intent(activity, null /*ResultadoBusquedaActivity::class.java*/)
                    intent.putExtra("textoBusqueda", busqueda)
                    startActivity(intent)
                }
            }
            true
        }

        return binding.root
    }
}