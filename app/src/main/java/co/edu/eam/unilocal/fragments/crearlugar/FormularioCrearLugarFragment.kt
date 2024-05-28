package co.edu.eam.unilocal.fragments.crearlugar

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.FragmentFormularioCrearLugarBinding
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Ciudad
import co.edu.eam.unilocal.models.Estado
import co.edu.eam.unilocal.models.Lugar
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.Date

class FormularioCrearLugarFragment : Fragment() {

    lateinit var binding: FragmentFormularioCrearLugarBinding
    var posCiudad:Int = 0
    var posCategoria:Int = 0
    lateinit var ciudades:ArrayList<Ciudad>
    lateinit var categorias:ArrayList<Categoria>
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    var codigoArchivo = 0
    var imagenes: ArrayList<String> = ArrayList()
    lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setView(co.edu.eam.unilocal.R.layout.dialogo_progreso)
        dialog = builder.create()

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult() ) {
            onActivityResult(it.resultCode, it)
        }

    }

    private fun onActivityResult(resultCode:Int, result:ActivityResult) {
        if (resultCode == Activity.RESULT_OK) {

            setDialog(true)

            val fecha = Date()
            val storageRef = FirebaseStorage.getInstance()
                .reference
                .child("/p-${fecha.time}.jpg")
            if (codigoArchivo == 1) {
                val data = result.data?.extras

                if( data?.get("data") is Bitmap ){
                    val imageBitmap = data?.get("data") as Bitmap
                    val baos = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()
                    storageRef.putBytes(data).addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener {
                            dibujarImagen(it)
                        }
                    }.addOnFailureListener {
                        Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
                    }
                }
            } else if (codigoArchivo == 2) {
                val data = result.data
                if(data!=null){
                    val selectedImageUri: Uri? = data.data
                    if(selectedImageUri!=null){
                        storageRef.putFile(selectedImageUri).addOnSuccessListener {
                            storageRef.downloadUrl.addOnSuccessListener {
                                dibujarImagen(it)
                            }
                        }.addOnFailureListener {
                            Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

            }
        }
    }

    fun dibujarImagen(url: Uri){
        setDialog(false)
        imagenes.add(url.toString())

        var imagen = ImageView(requireContext())
        imagen.layoutParams = LinearLayout.LayoutParams(300, 310)
        binding.imagenesSel.addView(imagen)

        Glide.with( requireContext() )
            .load(url.toString())
            .into(imagen)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFormularioCrearLugarBinding.inflate(inflater, container, false)

        ciudades = ArrayList()
        categorias = ArrayList()

        Firebase.firestore.collection("categorias")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val categoria = document.toObject(Categoria::class.java)
                    categorias.add(categoria)
                    cargarCategorias()

                }
            }
        Firebase.firestore.collection("ciudades")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val ciudad = document.toObject(Ciudad::class.java)
                    ciudades.add(ciudad)
                    cargarCiudades()

                }
            }

        binding.btnTomarFoto.setOnClickListener { tomarFoto() }
        binding.btnSelArchivo.setOnClickListener { seleccionarFoto() }

        return binding.root

    }

    fun tomarFoto() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                resultLauncher.launch(takePictureIntent)
                codigoArchivo = 1
            }
        }

    }

    fun seleccionarFoto(){

        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        codigoArchivo = 2

        resultLauncher.launch(i)

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

            if (imagenes.isNotEmpty()){

                val user = FirebaseAuth.getInstance().currentUser

                if(user != null) {

                    nuevoLugar = Lugar(
                        nombre,
                        descripcion,
                        user.uid,
                        Estado.PENDIENTE,
                        idCategoria,
                        direccion,
                        idCiudad,
                        novedades
                    )

                    val telefonos: ArrayList<String> = ArrayList()
                    telefonos.add(telefono)
                    nuevoLugar.telefonos = telefonos
                    nuevoLugar.imagenes = imagenes
                }

            }else{
                return null
            }
        }

        return nuevoLugar
    }

    private fun setDialog(show: Boolean){
        if (show) dialog.show() else dialog.dismiss()
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