package co.edu.eam.unilocal.activities

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.unilocal.models.Lugar

class DetalleLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetalleLugarBinding
    var codigoLugar: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lugar: Lugar? = Lugares.obtener(codigoLugar)
        cargarInformacion(lugar)
    }

    private fun cargarInformacion(lugar: Lugar?){

        if (lugar != null){

            val nombre: TextView = binding.nombreLugar
            val calificacion: TextView = binding.calificacionLugar
            val categoria: TextView = binding.categoriaLugar
            val estadoHorario: TextView = binding.estadoHorarioLugar
            val horario: TextView = binding.horarioLugar

            nombre.text = lugar.nombre
            calificacion.text = "5"
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
}