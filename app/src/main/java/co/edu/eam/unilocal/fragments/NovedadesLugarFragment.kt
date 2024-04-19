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

class NovedadesLugarFragment : Fragment() {

    lateinit var binding: FragmentNovedadesLugarBinding
    private var lugar: Lugar? = null
    private var codigoLugar: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null){
            codigoLugar = requireArguments().getInt("codigoLugar")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNovedadesLugarBinding.inflate(inflater, container, false)

        lugar = Lugares.obtener(codigoLugar)

        //binding.txtNovedades.text = lugar.novedades

        return binding.root
    }

    companion object{
        fun newInstance(codigoLugar: Int): NovedadesLugarFragment{
            val args = Bundle()
            args.putInt("codigoLugar", codigoLugar)
            val fragmento = NovedadesLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}