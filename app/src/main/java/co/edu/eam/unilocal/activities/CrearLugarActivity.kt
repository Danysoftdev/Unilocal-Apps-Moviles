package co.edu.eam.unilocal.activities

import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityCrearLugarBinding


class CrearLugarActivity : AppCompatActivity() {
    lateinit var binding: ActivityCrearLugarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityCrearLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var lista = arrayOf("Armenia" +
                "Barcelona" +
                "Buenavista" +
                "Calarcá" +
                "Circasia" +
                "Córdoba" +
                "Filandia" +
                "Génova" +
                "La Tebaida" +
                "Montenegro" +
                "Pijao" +
                "Quimbaya" +
                "Salento")
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ciudadLugar.adapter = adapter

        binding.btnCrearLugar.setOnClickListener { crearNuevoLugar() }

        }
        fun crearNuevoLugar(){

        }


    }

