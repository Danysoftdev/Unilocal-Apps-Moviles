package co.edu.eam.unilocal.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityMainBinding
import co.edu.eam.unilocal.fragments.InicioFragment
import co.edu.eam.unilocal.fragments.MisFavoritosFragment
import co.edu.eam.unilocal.fragments.MisLugaresFragment
import co.edu.eam.unilocal.fragments.TopLugaresFragment
import co.edu.eam.unilocal.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var MENU_INICIO = "inicio"
    private var MENU_MIS_FAVORITOS = "favoritos"
    private var MENU_TOP_SEMANAL = "top_semanal"
    private var MENU_MIS_LUGARES = "mis_lugares"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = FirebaseAuth.getInstance().currentUser

        var correo = ""
        val tipo = ""
        if (user!=null){
            Firebase.firestore
                .collection("usuarios")
                .document(user.uid)
                .get()
                .addOnSuccessListener { u ->
                    val rol = u.toObject(Usuario::class.java)?.tipo
                    correo = u.toObject(Usuario::class.java)?.correo ?: ""
                    val btnSesion: Button = binding.btnSesion
                    if(rol == "usuario") {
                        btnSesion.setBackgroundResource(R.drawable.user_circle)
                        binding.barraInferior.visibility = View.VISIBLE
                        btnSesion.setOnClickListener { limpiarSesion() }
                    }else {
                        startActivity( Intent(this, ModeradorActivity::class.java) )
                    }


                }
        }



        val btnSesion: Button = binding.btnSesion

        if (correo!!.isEmpty()) {
            btnSesion.setBackgroundResource(R.drawable.user_plus)
            binding.barraInferior.visibility = View.GONE

            btnSesion.setOnClickListener { startActivity( Intent(this, LoginActivity::class.java) ) }
        }else{
            if (tipo == "usuario"){
                btnSesion.setBackgroundResource(R.drawable.user_circle)
                binding.barraInferior.visibility = View.VISIBLE
                btnSesion.setOnClickListener { limpiarSesion() }

            }else{
                startActivity( Intent(this, ModeradorActivity::class.java) )
            }

            btnSesion.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        }
        reemplazarFragmento(1, MENU_INICIO)
        binding.barraInferior.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuInicio -> reemplazarFragmento(1,MENU_INICIO)
                R.id.menuLugares -> reemplazarFragmento(2,MENU_MIS_LUGARES)
                R.id.menuFavoritos -> reemplazarFragmento(3,MENU_MIS_FAVORITOS)
                R.id.menuTopSemanal -> reemplazarFragmento(4,MENU_TOP_SEMANAL)
            }
            true
        }

    }
    fun reemplazarFragmento(valor:Int,nombre:String){
        var fragmento:Fragment = when(valor){
            1 -> InicioFragment()
            2 -> MisLugaresFragment()
            3 -> MisFavoritosFragment()
            else -> TopLugaresFragment()
        }

        supportFragmentManager.beginTransaction().replace(binding.contenidoPrincipal.id, fragmento).addToBackStack(nombre).commit()

    }


    fun limpiarSesion() {

            FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, MainActivity::class.java))


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val count = supportFragmentManager.backStackEntryCount
        if(count>0){
            val nombre = supportFragmentManager.getBackStackEntryAt(count-1).name
            when(nombre){
                MENU_INICIO -> binding.barraInferior.menu.getItem(0).isChecked = true
                MENU_MIS_LUGARES -> binding.barraInferior.menu.getItem(1).isChecked = true
                MENU_MIS_FAVORITOS -> binding.barraInferior.menu.getItem(2).isChecked = true
                else -> binding.barraInferior.menu.getItem(3).isChecked = true
            }
        }
    }
}

