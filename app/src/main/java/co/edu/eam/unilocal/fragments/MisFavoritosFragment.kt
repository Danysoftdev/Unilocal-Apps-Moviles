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
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.adapter.LugarFavoritoAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.FragmentMisFavoritosBinding
import co.edu.eam.unilocal.databinding.FragmentMisLugaresBinding
import co.edu.eam.unilocal.models.Lugar


class MisFavoritosFragment : Fragment() , LugarFavoritoAdapter.OnLugarEliminadoListener{
   var listaLugaresFavoritos:ArrayList<Lugar> = ArrayList()
    lateinit var binding: FragmentMisFavoritosBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisFavoritosBinding.inflate(inflater,container,false)
        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("id_usuario",-1)
        Log.e("MisFavoritosFragment", codigoUsuario.toString())
        listaLugaresFavoritos = Usuarios.buscar(codigoUsuario).favoritos
        Log.e("MisFavoritosFragment", listaLugaresFavoritos.toString())
        if(listaLugaresFavoritos.isEmpty()){
            binding.mensajeVacioFavoritos.visibility = View.VISIBLE
        }else {
            binding.mensajeVacioFavoritos.visibility = View.GONE
            val adapter = LugarFavoritoAdapter(listaLugaresFavoritos)
            adapter.setOnLugarEliminadoListener(this)
            binding.listaFavoritos.adapter = adapter
            binding.listaFavoritos.layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL, false)
        }
        return binding.root
    }
    fun actualizarListaLugares() {
        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("id_usuario", -1)
        listaLugaresFavoritos.clear() // Borra la lista existente
        listaLugaresFavoritos.addAll(Usuarios.buscar(codigoUsuario).favoritos) // Vuelve a cargar la lista
        if (listaLugaresFavoritos.isEmpty()) {
            binding.mensajeVacioFavoritos.visibility = View.VISIBLE
        } else {
            binding.mensajeVacioFavoritos.visibility = View.GONE
            (binding.listaFavoritos.adapter as LugarFavoritoAdapter).notifyDataSetChanged() // Notifica al Adapter que los datos han cambiado
        }
    }
    override fun onLugarEliminado() {
        actualizarListaLugares()
        Toast.makeText(requireActivity(),"SE ELIMINÓ CORRECTAMENTE", Toast.LENGTH_LONG).show()
    }

}