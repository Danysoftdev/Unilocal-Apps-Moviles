package co.edu.eam.unilocal.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.CrearLugarActivity
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.bd.Lugares

import co.edu.eam.unilocal.databinding.FragmentMisLugaresBinding
import co.edu.eam.unilocal.models.Lugar


class MisLugaresFragment : Fragment(),LugarAdapter.OnLugarEliminadoListener {
    var listaLugares:ArrayList<Lugar> = ArrayList()
    lateinit var binding: FragmentMisLugaresBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisLugaresBinding.inflate(inflater,container,false)
        binding.btnCrearLugares.setOnClickListener { irCrearLugar() }

        val sp = requireActivity().getSharedPreferences("sesion",Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("id_usuario",-1)
        listaLugares = Lugares.buscarXUsuario(codigoUsuario)

        if(listaLugares.isEmpty()){
            binding.mensajeVacioLugares.visibility = View.VISIBLE
        }else {
            binding.mensajeVacioLugares.visibility = View.GONE
            val adapter = LugarAdapter(listaLugares)
            adapter.setOnLugarEliminadoListener(this)
            binding.listaMisLugares.adapter = adapter
            binding.listaMisLugares.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        }


        return binding.root
    }
    fun actualizarListaLugares() {
        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("id_usuario", -1)
        listaLugares.clear() // Borra la lista existente
        listaLugares.addAll(Lugares.buscarXUsuario(codigoUsuario)) // Vuelve a cargar la lista
        if (listaLugares.isEmpty()) {
            binding.mensajeVacioLugares.visibility = View.VISIBLE
        } else {
            binding.mensajeVacioLugares.visibility = View.GONE
            (binding.listaMisLugares.adapter as LugarAdapter).notifyDataSetChanged() // Notifica al Adapter que los datos han cambiado
        }
    }
    fun irCrearLugar(){
        startActivity(Intent(activity,CrearLugarActivity::class.java))
    }
    override fun onLugarEliminado() {

        actualizarListaLugares()
        Toast.makeText(requireActivity(),"SE ELIMINÓ CORRECTAMENTE", Toast.LENGTH_LONG).show()
    }


}