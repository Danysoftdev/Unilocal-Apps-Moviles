package co.edu.eam.unilocal.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapters.CrearLugarAdapter
import co.edu.eam.unilocal.databinding.ActivityCrearLugarBinding
import co.edu.eam.unilocal.fragments.crearlugar.FormularioCrearLugarFragment
import co.edu.eam.unilocal.fragments.crearlugar.HorariosCrearLugarFragment
import co.edu.eam.unilocal.fragments.crearlugar.MapaCrearLugarFragment
import co.edu.eam.unilocal.models.Lugar
import co.edu.eam.unilocal.models.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CrearLugarActivity : AppCompatActivity(){

    lateinit var binding: ActivityCrearLugarBinding
    var lugar:Lugar? = null
    var posicionActual:Int = 0
    private var user: FirebaseUser? = null

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

                //Lugares.crear(lugar!!)
                Firebase.firestore.collection("lugares")
                    .add(lugar!!)
                    .addOnSuccessListener {
                        user = FirebaseAuth.getInstance().currentUser
                        hacerRedireccion(user!!)
                        Snackbar.make(binding.root, getString(R.string.txt_creacion_exitosa), Snackbar.LENGTH_LONG).show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 4000)
                    }
                    .addOnFailureListener {
                        Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
                    }



            }

        }

    }
    fun hacerRedireccion(user: FirebaseUser){
        Firebase.firestore
            .collection("usuarios")
            .document(user.uid)
            .get()
            .addOnSuccessListener { u ->


                val rol = u.toObject(Usuario::class.java)?.tipo

                if(rol == "usuario") {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity( intent )
                    finish()
                }else if(rol == "moderador") {

                    val intent = Intent(this, ModeradorActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity( intent )
                    finish()

                }


            }
    }

}

