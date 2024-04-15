package co.edu.eam.unilocal.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityMisLugaresBinding
import co.edu.eam.unilocal.models.Lugar

class MisLugaresActivity : AppCompatActivity() {
    lateinit var binding : ActivityMisLugaresBinding
    lateinit var listaLugares:ArrayList<Lugar>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMisLugaresBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}