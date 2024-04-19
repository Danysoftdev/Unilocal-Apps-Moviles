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

        val registro: Boolean = Usuarios.agregar( Usuario(0, nombre.toString(), nickname.toString(), email.toString(), password.toString(), "usuario") )

        if (registro){
            Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
        }else{
            Toast.makeText(this, "El correo ingresado ya se encuentra registrado", Toast.LENGTH_LONG).show()
        }


    }
}