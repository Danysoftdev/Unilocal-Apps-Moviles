package co.edu.eam.unilocal.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputBinding
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Ciudades
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityCrearLugarBinding
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Ciudad
import co.edu.eam.unilocal.models.Lugar


class CrearLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCrearLugarBinding
    var posCiudad:Int = 0
    var posCategoria:Int =0
    lateinit var ciudades :ArrayList<Ciudad>
    lateinit var categorias :ArrayList<Categoria>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCrearLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ciudades = Ciudades.listar()
        categorias = Categorias.listar()

        cargarCiudades()
        cargarCategorias()

        binding.btnLugares.setOnClickListener {

                val intent = Intent(baseContext,MisLugaresActivity::class.java)
                startActivity(intent)

        }

        binding.btnCrearLugar.setOnClickListener { crearNuevoLugar() }

        }

        fun cargarCiudades(){
            var lista = ciudades.map { c -> c.nombre }
            val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,lista)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.ciudadLugar.adapter = adapter

            binding.ciudadLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    /*Toast.makeText(baseContext,"EL elemento seleccionado fue ${parent!!.getItemAtPosition(position).toString()}",Toast.LENGTH_LONG).show()*/
                    posCiudad = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

        }
    fun cargarCategorias(){
        var lista = categorias.map { c -> c.nombre }
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaLugar.adapter = adapter

        binding.ciudadLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*Toast.makeText(baseContext,"EL elemento seleccionado fue ${parent!!.getItemAtPosition(position).toString()}",Toast.LENGTH_LONG).show()*/
                posCategoria = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }
        fun crearNuevoLugar(){
            val nombre = binding.nombreLugar.text.toString()
            val descripcion = binding.descripcionLugar.text.toString()
            val direccion = binding.direccionLugar.text.toString()
            val telefono = binding.telefonoLugar.text.toString()
            val novedades = binding.novedadesLugar.text.toString()
            val idCiudad = ciudades[posCiudad].id
            val idCategoria = categorias[posCategoria].id

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


            if(nombre.isNotEmpty() && descripcion.isNotEmpty()&& telefono.isNotEmpty() && direccion.isNotEmpty() && idCiudad != -1 && idCategoria != -1){
                val nuevoLugar = Lugar(9,nombre,descripcion,1,false,idCategoria,direccion, 0f, 0f,idCiudad, novedades)

                val telefonos:ArrayList<String> = ArrayList()
                telefonos.add(telefono)

                nuevoLugar.telefonos = telefonos

                Lugares.crear(nuevoLugar)

                Log.e("CrearLugarActivity", Lugares.listarRechazados().toString())
            }




        }


    }

