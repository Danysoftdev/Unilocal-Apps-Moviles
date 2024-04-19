package co.edu.eam.unilocal.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val correo = sp.getString("correo_usuario", "")

        val btnSesion: Button = binding.btnSesion

        if( correo!!.isEmpty() ){
            btnSesion.text = "Iniciar sesión"
            btnSesion.setOnClickListener { startActivity( Intent(this, LoginActivity::class.java) ) }
        }else{
            btnSesion.text ="Cerrar sesión"
            btnSesion.setOnClickListener { limpiarSesion() }
        }
    }

    fun limpiarSesion(){

        val sharedPreferences = getSharedPreferences("sesion", Context.MODE_PRIVATE ).edit()
        sharedPreferences.clear()

        startActivity( Intent(this, LoginActivity::class.java) )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menuInicio -> Toast.makeText(this, "Home", Toast.LENGTH_LONG).show()
            R.id.menuLugares -> Toast.makeText(this, "Mis lugares", Toast.LENGTH_LONG).show()
            R.id.menuFavoritos -> Toast.makeText(this, "Favoritos", Toast.LENGTH_LONG).show()
            R.id.menuTopSemanal -> Toast.makeText(this, "Top semanal", Toast.LENGTH_LONG).show()
        }

        return super.onOptionsItemSelected(item)
    }
}