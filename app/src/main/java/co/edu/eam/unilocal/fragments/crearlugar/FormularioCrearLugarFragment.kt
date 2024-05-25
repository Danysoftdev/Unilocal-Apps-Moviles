package co.edu.eam.unilocal.fragments.crearlugar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.MainActivity
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Ciudades
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentFormularioCrearLugarBinding
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Ciudad
import co.edu.eam.unilocal.models.Estado
import co.edu.eam.unilocal.models.Lugar

class FormularioCrearLugarFragment : Fragment() {

    lateinit var binding: FragmentFormularioCrearLugarBinding
    var posCiudad:Int = 0
    var posCategoria:Int = 0
    lateinit var ciudades:ArrayList<Ciudad>
    lateinit var categorias:ArrayList<Categoria>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFormularioCrearLugarBinding.inflate(inflater, container, false)

        ciudades = Ciudades.listar()
        categorias = Categorias.listar()

        cargarCiudades()
        cargarCategorias()

        return binding.root

    }

    fun cargarCiudades(){
        val lista = mutableListOf(getString(R.string.default_ciudad))
        lista.addAll(ciudades.map { c -> c.nombre })
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ciudadLugar.adapter = adapter

        binding.ciudadLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    Toast.makeText(requireContext(), getString(R.string.validacion_ciudad), Toast.LENGTH_LONG).show()
                } else {

                    posCiudad = position-1
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun cargarCategorias() {
        val lista = mutableListOf(getString(R.string.default_categoria))
        lista.addAll(categorias.map { c -> c.nombre })
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaLugar.adapter = adapter

        binding.categoriaLugar.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    /*Toast.makeText(baseContext,"EL elemento seleccionado fue ${parent!!.getItemAtPosition(position).toString()}",Toast.LENGTH_LONG).show()*/
                    if (position == 0) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.validacion_categoria),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        posCategoria = position - 1
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
    }

    fun crearNuevoLugar():Lugar?{

        //CAPTURAMOS LOS VALORES DE TODOS LOS CAMPOS
        val nombre = binding.nombreLugar.text.toString()
        val descripcion = binding.descripcionLugar.text.toString()
        val direccion = binding.direccionLugar.text.toString()
        val telefono = binding.telefonoLugar.text.toString()
        val novedades = binding.novedadesLugar.text.toString()
        val idCiudad = ciudades[posCiudad].id
        val idCategoria = categorias[posCategoria].id
        var nuevoLugar:Lugar? = null

        //SE VALIDA QUE LOS CAMPOS ESTÉN COMPLETOS DE LO CONTRARIO MUESTRA UNA ALERTA
        if(nombre.isEmpty()){
            binding.nombreLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.nombreLayout.error = null
        }
        if(descripcion.isEmpty()){
            binding.descripcionLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.descripcionLayout.error = null
        }
        if(direccion.isEmpty()){
            binding.direccionLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.direccionLayout.error = null
        }
        if(telefono.isEmpty()){
            binding.telefonoLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.telefonoLayout.error = null
        }
        if(novedades.isEmpty()){
            binding.novedadesLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.novedadesLayout.error = null
        }


        if(nombre.isNotEmpty() && descripcion.isNotEmpty()&& telefono.isNotEmpty() && direccion.isNotEmpty() && idCiudad != 0 && idCategoria != 0 ){

            nuevoLugar = Lugar(7,nombre,descripcion,1, Estado.PENDIENTE,idCategoria,direccion, idCiudad, novedades)

            val telefonos:ArrayList<String> = ArrayList()
            telefonos.add(telefono)
            nuevoLugar.telefonos = telefonos
        }

        return nuevoLugar
    }

    companion object{

        fun newInstance():FormularioCrearLugarFragment{
            val args = Bundle()

            val fragmento = FormularioCrearLugarFragment()
            fragmento.arguments = args
            return fragmento
        }

    }

}