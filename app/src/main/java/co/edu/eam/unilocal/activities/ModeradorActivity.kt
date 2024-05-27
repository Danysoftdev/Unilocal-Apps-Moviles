package co.edu.eam.unilocal.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapters.LugarModeradorAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityModeradorBinding
import co.edu.eam.unilocal.models.Lugar

class ModeradorActivity : AppCompatActivity() {

    lateinit var binding: ActivityModeradorBinding
    lateinit var listaLugares: ArrayList<Lugar>
    var codigoModerador: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityModeradorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigo = sp.getInt("id_usuario", -1)

        if (codigo>0 ){
            codigoModerador = codigo
            Log.e("ModeradorActivity", codigoModerador.toString())
            listaLugares = ArrayList()
        }

        var vecesClick: Int = 0

        //listaLugares.addAll(Lugares.listar())

        binding.aceptados.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                vecesClick++
                if (vecesClick > 1){
                   // listaLugares.addAll(Lugares.listarAprobados())
                }else{
                    listaLugares.clear()
                    //listaLugares.addAll(Lugares.listarAprobados())
                }
            }else{
               // listaLugares.removeAll(Lugares.listarAprobados())
            }
            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
        }

        binding.pendientes.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                vecesClick++
                if (vecesClick > 1){
                   // listaLugares.addAll(Lugares.listarPendientes())
                }else{
                    listaLugares.clear()
                //listaLugares.addAll(Lugares.listarPendientes())
                }
            }else{
               //listaLugares.removeAll(Lugares.listarPendientes())
            }
            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
        }

        binding.rechazados.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                vecesClick++
                if (vecesClick > 1){
                  //  listaLugares.addAll(Lugares.listarRechazados())
                }else{
                    listaLugares.clear()
                  //  listaLugares.addAll(Lugares.listarRechazados())
                }
            }else{
                //listaLugares.removeAll(Lugares.listarRechazados())
            }
            binding.lugaresRevisar.adapter!!.notifyDataSetChanged()
        }
        binding.btnSesion.setOnClickListener {
            limpiarSesion()
        }

        Log.e("ModeradorActivity", listaLugares.toString())

        binding.lugaresRevisar.adapter = LugarModeradorAdapter(listaLugares, codigoModerador)
        binding.lugaresRevisar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
    fun limpiarSesion() {

        val sharedPreferences = getSharedPreferences("sesion", Context.MODE_PRIVATE).edit()
        sharedPreferences.clear()
        sharedPreferences.apply()

        startActivity(Intent(this, MainActivity::class.java))

    }

}