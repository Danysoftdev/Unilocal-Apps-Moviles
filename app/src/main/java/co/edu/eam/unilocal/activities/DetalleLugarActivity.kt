package co.edu.eam.unilocal.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import co.edu.eam.unilocal.adapters.ViewPagerAdapter
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.unilocal.models.Lugar
import com.google.android.material.tabs.TabLayoutMediator

class DetalleLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetalleLugarBinding
    private var lugar: Lugar? = null
    //Recordar cambiar a = 0, cuando ya se tenga la parte de los resultados de búsqueda
    var codigoLugar: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //codigoLugar = intent.extras!!.getInt("codigoLugar")
        lugar = Lugares.obtener(codigoLugar)
        cargarInformacionSuperior(lugar)
        cargarTabs()
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
                horario.text = "Cierra a las ${lugar.obtenerHoraCierre()}:00"
            }else{
                estadoHorario.setTextColor(Color.RED)
                horario.text = "Abre a las ${lugar.obtenerHoraApertura()}:00"
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
}