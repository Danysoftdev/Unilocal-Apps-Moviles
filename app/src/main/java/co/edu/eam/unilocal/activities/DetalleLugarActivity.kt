package co.edu.eam.unilocal.activities

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import co.edu.eam.unilocal.adapters.ViewPagerAdapter
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.unilocal.models.Lugar
import co.edu.eam.unilocal.models.Usuario
import com.google.android.material.tabs.TabLayoutMediator

class DetalleLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetalleLugarBinding
    private var lugar: Lugar? = null
    var codigoLugar: Int = 0
    private var usuario: Usuario? = null
    var codigoUsuario: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigo = sp.getString("id_usuario", "")
        codigoUsuario = codigo!!.toInt()


        codigoLugar = intent.extras!!.getInt("codigoLugar")
        lugar = Lugares.obtener(codigoLugar)
        cargarInformacionSuperior(lugar)
        cargarTabs()

        usuario = Usuarios.getById(codigoUsuario)

        binding.btnGuardarLugar.setOnClickListener { guardarLugarFavoritos() }
    }

    private fun cargarInformacionSuperior(lugar: Lugar?){

        if (lugar != null){

            val nombre: TextView = binding.nombreLugar
            val categoria: TextView = binding.categoriaLugar
            val estadoHorario: TextView = binding.estadoHorarioLugar
            val horario: TextView = binding.horarioLugar

            nombre.text = lugar.nombre

            val calificacion = lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))
            for (i in 0..calificacion){
                (binding.listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
            }

            val cantidadComentatios: TextView = binding.cantidadComentarios
            cantidadComentatios.text = "(${Comentarios.obtenerCantidadComentarios(lugar.id).toString()})"

            val categoryPlace = Categorias.obtener(lugar.idCategoria)
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

        if (codigoLugar != 0){

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
}