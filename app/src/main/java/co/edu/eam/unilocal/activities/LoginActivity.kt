package co.edu.eam.unilocal.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityLoginBinding
import co.edu.eam.unilocal.models.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val user = FirebaseAuth.getInstance().currentUser



            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnRecuperarContra.setOnClickListener { irARecuperacion() }
            binding.btnIniciar.setOnClickListener { login() }
            binding.btnRegistrarse.setOnClickListener { registrarse() }

       /* binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)*/


    }

    fun irARecuperacion(){
        val intent = Intent(this, RecuperarContraActivity::class.java)
        startActivity(intent)
    }
    fun cerrarSesion(){
        FirebaseAuth.getInstance().signOut()

    }
    fun login(){
        val correo = binding.correo.text
        val password = binding.password.text

        if ( correo.isNotEmpty() && password.isNotEmpty() ){
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword( correo.toString(), password.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        val user = FirebaseAuth.getInstance().currentUser
                        if( user!= null) {
                            hacerRedireccion(user)
                        }
                    }else{
                        Snackbar.make(binding.root, getText(R.string.txt_login_incorrecto), Snackbar.LENGTH_LONG).show()
                    }

                }
                .addOnFailureListener {
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }


        }else{
            Toast.makeText(this, getText(R.string.txt_campos_obligatorios), Toast.LENGTH_LONG).show()
        }
    }

    fun registrarse(){

        val intentRegistro = Intent(this, RegistroActivity::class.java)
        startActivity(intentRegistro)

    }
    fun hacerRedireccion(user:FirebaseUser){
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