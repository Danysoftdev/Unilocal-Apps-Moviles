package co.edu.eam.unilocal.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.adapter.TopLugarAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityMisLugaresBinding
import co.edu.eam.unilocal.databinding.ActivityTopSemanalBinding
import co.edu.eam.unilocal.models.Lugar

class TopSemanalActivity : AppCompatActivity() {
    lateinit var binding: ActivityTopSemanalBinding
    lateinit var listaLugares: ArrayList<Lugar>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTopSemanalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaLugares = ArrayList()

        listaLugares = Lugares.ordenarPorCorazones()

        val adapter = TopLugarAdapter(listaLugares)

        binding.listaLugares.adapter = adapter
        binding.listaLugares.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

    }
}