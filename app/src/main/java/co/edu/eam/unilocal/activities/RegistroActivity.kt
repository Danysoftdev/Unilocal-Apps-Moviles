package co.edu.eam.unilocal.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityRegistroBinding
import co.edu.eam.unilocal.models.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistro.setOnClickListener { registrar() }
    }

    fun registrar() {
        val nombre = binding.txtNombre.text
        val nickname = binding.txtNickname.text
        val email = binding.txtEmail.text
        val password = binding.txtPassword.text
        val confirmarPass = binding.txtConfirmarPassword.text

        if( nombre.isEmpty() || nickname.isEmpty() || email.isEmpty()
            || password.isEmpty() || confirmarPass.isEmpty() ){
            Toast.makeText(this, "Los campos son obligatorios", Toast.LENGTH_LONG).show()
        }

        if( password.toString() != confirmarPass.toString() ){
            Toast.makeText(this, "Las contraseñas no son iguales", Toast.LENGTH_LONG).show()
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString(), password.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val user = FirebaseAuth.getInstance().currentUser
                    if(user != null){
                        verificarEmail(user)
                        val usuarioRegistro = Usuario(nombre.toString(), nickname.toString(),email.toString(), "usuario")
                        usuarioRegistro.uid = user.uid
                       Firebase.firestore.collection("usuarios")
                           .document(user.uid)
                           .set(usuarioRegistro)
                       .addOnCompleteListener {
                               Snackbar.make(binding.root, "Usuario registrado", Snackbar.LENGTH_LONG).show()
                           val intent = Intent(this, MainActivity::class.java)
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                           startActivity( intent )
                           finish()

                       }
                    }

                }else{
                    Toast.makeText(this, "El correo ingresado ya se encuentra registrado", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {
                Snackbar.make(binding.root, "Error al registrar el usuario", Snackbar.LENGTH_LONG).show()
            }

    }
    private fun verificarEmail(user: FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener{
            if(it.isSuccessful){
                Snackbar.make(binding.root, "Email enviado", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}