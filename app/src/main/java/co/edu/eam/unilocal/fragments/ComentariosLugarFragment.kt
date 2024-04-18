package co.edu.eam.unilocal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.databinding.FragmentComentariosLugarBinding
import co.edu.eam.unilocal.models.Comentario

class ComentariosLugarFragment : Fragment() {

    lateinit var binding: FragmentComentariosLugarBinding
    private var codigoLugar: Int = 0
    private var listaComentarios: ArrayList<Comentario> = ArrayList()

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

        binding = FragmentComentariosLugarBinding.inflate(inflater, container, false)

        listaComentarios = Comentarios.listar(codigoLugar)
        binding.listaComentarios.text = listaComentarios.toString()

        return binding.root
    }

    companion object{
        fun newInstance(codigoLugar: Int): ComentariosLugarFragment{
            val args = Bundle()
            args.putInt("codigoLugar", codigoLugar)
            val fragmento = ComentariosLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}