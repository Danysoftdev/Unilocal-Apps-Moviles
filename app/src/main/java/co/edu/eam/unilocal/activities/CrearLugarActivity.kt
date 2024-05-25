package co.edu.eam.unilocal.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputBinding
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapters.CrearLugarAdapter
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Ciudades
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityCrearLugarBinding
import co.edu.eam.unilocal.fragments.crearlugar.FormularioCrearLugarFragment
import co.edu.eam.unilocal.fragments.crearlugar.HorariosCrearLugarFragment
import co.edu.eam.unilocal.fragments.crearlugar.MapaCrearLugarFragment
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Ciudad
import co.edu.eam.unilocal.models.Estado
import co.edu.eam.unilocal.models.Lugar
import co.edu.eam.unilocal.models.Posicion
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar


class CrearLugarActivity : AppCompatActivity(){

    lateinit var binding: ActivityCrearLugarBinding
    var lugar:Lugar? = null
    var posicionActual:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCrearLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lugar = Lugar()

        binding.itemsForm.adapter = CrearLugarAdapter(this)
        binding.itemsForm.isUserInputEnabled = false

        binding.btnSgte.setOnClickListener { pasarSiguienteFormulario() }

    }

    fun pasarSiguienteFormulario(){

        val myFragment = supportFragmentManager.findFragmentByTag("f" + binding.itemsForm.currentItem)

        if(posicionActual==0){
            lugar = (myFragment as FormularioCrearLugarFragment).crearNuevoLugar()

            if(lugar == null){
                Snackbar.make(binding.root, getString(R.string.txt_creacion_fallida), Snackbar.LENGTH_LONG).show()
            }else{
                binding.itemsForm.setCurrentItem(1, true)
                posicionActual++
                binding.barraProgreso.progress = 2
            }

        }else if(posicionActual==1){

            val horarios = (myFragment as HorariosCrearLugarFragment).horarios

            if( horarios.isEmpty() ){
                Snackbar.make(binding.root, getString(R.string.horario_validacion), Snackbar.LENGTH_LONG).show()
            }else{
                lugar!!.horarios = horarios
                binding.itemsForm.setCurrentItem(2, true)
                posicionActual++
                binding.barraProgreso.progress = 3
            }

        }else{

            val posicion = (myFragment as MapaCrearLugarFragment).posicion

            if(posicion == null){
                Snackbar.make(binding.root, getString(R.string.txt_seleccion_inconclusa), Snackbar.LENGTH_LONG).show()
            }else{
                lugar!!.posicion = posicion

                Lugares.crear(lugar!!)
                Snackbar.make(binding.root, getString(R.string.txt_creacion_exitosa), Snackbar.LENGTH_LONG).show()


            }

        }

    }

}

