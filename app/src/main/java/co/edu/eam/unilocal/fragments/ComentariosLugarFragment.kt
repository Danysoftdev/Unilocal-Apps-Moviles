package co.edu.eam.unilocal.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapters.ComentarioAdapter
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.databinding.FragmentComentariosLugarBinding
import co.edu.eam.unilocal.models.Comentario
import com.google.android.material.snackbar.Snackbar

class ComentariosLugarFragment : Fragment() {

    lateinit var binding: FragmentComentariosLugarBinding
    private var codigoLugar: Int = 0
    var listaComentarios: ArrayList<Comentario> = ArrayList()
    private  lateinit var adapter: ComentarioAdapter
    private var estrellas: Int = 0
    var codigoUsuario: Int = 1

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
        adapter = ComentarioAdapter(requireContext(), listaComentarios)
        binding.listaComentarios.adapter = adapter
        binding.listaComentarios.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        for (i in 0 until binding.listaEstrellas.childCount){
            (binding.listaEstrellas[i] as TextView).setOnClickListener {presionarEstrella(i)}
        }

        binding.btComentar.setOnClickListener { crearComentario() }

        return binding.root
    }

    private fun presionarEstrella(pos:Int){
        estrellas = pos + 1
        borrarSeleccionEstrellas()
        for (i in 0 .. pos){
            (binding.listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
        }

    }

    fun crearComentario(){

        val texto = binding.txtComentario.text.toString()

        if (texto.isNotEmpty() && estrellas > 0){
            val comentario = Comentarios.crear(Comentario(0, texto, codigoUsuario, codigoLugar, estrellas))

            limpiarFormulario()
            Snackbar.make(binding.root, "Se ha enviado el comentario", Snackbar.LENGTH_LONG).show()

            listaComentarios.add(comentario)
            adapter.notifyItemInserted(listaComentarios.size-1)


        }else{
            Snackbar.make(binding.root, "Debe escribir el comentario y seleccionar las estrellas", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun limpiarFormulario(){
        binding.txtComentario.setText("")
        borrarSeleccionEstrellas()
        estrellas = 0
    }

    private fun borrarSeleccionEstrellas(){
        for (i in 0 until binding.listaEstrellas.childCount){
            (binding.listaEstrellas[i] as TextView).setTextColor(Color.GRAY)
        }
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