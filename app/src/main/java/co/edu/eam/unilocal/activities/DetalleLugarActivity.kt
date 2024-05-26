package co.edu.eam.unilocal.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import co.edu.eam.unilocal.adapters.ViewPagerAdapter
import co.edu.eam.unilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Lugar
import co.edu.eam.unilocal.models.Usuario
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetalleLugarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleLugarBinding
    private var lugar: Lugar? = null
    private var codigoLugar: String = ""
    private var usuario: Usuario? = null
    private var codigo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (codigo > 0) {
            usuario = Usuario() //Usuarios.getById(codigoUsuario)
            binding.btnGuardarLugar.visibility = View.VISIBLE
            binding.btnGuardarLugar.setOnClickListener { guardarLugarFavoritos() }
        } else {
            binding.btnGuardarLugar.visibility = View.GONE
        }

        codigoLugar = intent.extras?.getString("codigoLugar").orEmpty()

        if (codigoLugar.isNotEmpty()) {
            Firebase.firestore.collection("lugares").document(codigoLugar)
                .get()
                .addOnSuccessListener { document ->
                    lugar = document.toObject(Lugar::class.java)
                    lugar?.let {
                        cargarInformacionSuperior(it)
                        cargarTabs()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al cargar el lugar", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun cargarInformacionSuperior(lugar: Lugar) {
        binding.nombreLugar.text = lugar.nombre

        val calificacion = 2 //lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))
        for (i in 0..calificacion) {
            (binding.listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
        }

        binding.cantidadComentarios.text = "2" // "(${Comentarios.obtenerCantidadComentarios(lugar.id).toString()})"
        val categoryPlace = Categoria() //Categorias.obtener(lugar.idCategoria)
        binding.categoriaLugar.text = categoryPlace?.nombre
        binding.estadoHorarioLugar.text = lugar.verificarEstadoHorario()

        if (lugar.verificarEstadoHorario() == "Abierto") {
            binding.estadoHorarioLugar.setTextColor(Color.GREEN)
            binding.horarioLugar.text = lugar.obtenerHoraCierre()
        } else {
            binding.estadoHorarioLugar.setTextColor(Color.RED)
            binding.horarioLugar.text = lugar.obtenerHoraApertura()
        }
    }

    private fun cargarTabs() {
        binding.viewPager.adapter = ViewPagerAdapter(this, codigoLugar)
        TabLayoutMediator(binding.tabsLugar, binding.viewPager) { tab, pos ->
            when (pos) {
                0 -> tab.text = "Información"
                1 -> tab.text = "Comentario"
                2 -> tab.text = "Novedades"
            }
        }.attach()
    }

    private fun guardarLugarFavoritos() {
        usuario?.let {
            it.favoritos.add(lugar!!)
            Toast.makeText(this, "Añadido a los lugares favoritos", Toast.LENGTH_LONG).show()
        }
    }
}

/*package co.edu.eam.unilocal.activities

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import co.edu.eam.unilocal.adapters.ViewPagerAdapter
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Lugar
import co.edu.eam.unilocal.models.Usuario
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetalleLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetalleLugarBinding
    private var lugar: Lugar? = null
    var codigoLugar: String = ""
    private var usuario: Usuario? = null
    var codigo : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (codigo > 0){
            usuario = Usuario() //Usuarios.getById(codigoUsuario)
            binding.btnGuardarLugar.visibility = View.VISIBLE
            binding.btnGuardarLugar.setOnClickListener { guardarLugarFavoritos() }
        }else{
            binding.btnGuardarLugar.visibility = View.GONE
        }


        codigoLugar = intent.extras!!.getString("codigoLugar").toString()

        Firebase.firestore.collection("lugares").document(codigoLugar)
            .get()
            .addOnSuccessListener {
                var lugar = it.toObject(Lugar::class.java)
                cargarInformacionSuperior(lugar)
                cargarTabs()}


    }

    private fun cargarInformacionSuperior(lugar: Lugar?){

        if (lugar != null){

            val nombre: TextView = binding.nombreLugar
            val categoria: TextView = binding.categoriaLugar
            val estadoHorario: TextView = binding.estadoHorarioLugar
            val horario: TextView = binding.horarioLugar

            nombre.text = lugar.nombre

            val calificacion = 2 //lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))
            for (i in 0..calificacion){
                (binding.listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
            }

            val cantidadComentatios: TextView = binding.cantidadComentarios
            cantidadComentatios.text = "2"//"(${Comentarios.obtenerCantidadComentarios(lugar.id).toString()})"

            val categoryPlace = Categoria()//Categorias.obtener(lugar.idCategoria)
            categoria.text = categoryPlace?.nombre
            estadoHorario.text = lugar.verificarEstadoHorario()
            if (lugar.verificarEstadoHorario() == "Abierto"){
                estadoHorario.setTextColor(Color.GREEN)
                horario.text = lugar.obtenerHoraCierre()
            }else{
                estadoHorario.setTextColor(Color.RED)
                horario.text = lugar.obtenerHoraApertura()
            }
        }


    }

    private fun cargarTabs(){

        if (codigoLugar != null){

            binding.viewPager.adapter = ViewPagerAdapter(this, codigoLugar)
            TabLayoutMediator(binding.tabsLugar, binding.viewPager){tab, pos ->
                when(pos){
                    0 -> tab.text = "Información"
                    1 -> tab.text = "Comentario"
                    2 -> tab.text = "Novedades"
                }
            }.attach()
        }

    }

    private fun guardarLugarFavoritos(){

        if (usuario != null){

            usuario!!.favoritos.add(lugar!!)

            Toast.makeText(this, "Añadido a los lugares favoritos", Toast.LENGTH_LONG).show()
        }

    }
}*/