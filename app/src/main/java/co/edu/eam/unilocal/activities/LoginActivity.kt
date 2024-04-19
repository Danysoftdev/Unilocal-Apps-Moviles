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

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRecuperarContra.setOnClickListener { irARecuperacion() }
        binding.btnIniciar.setOnClickListener { login() }
        binding.btnRegistrarse.setOnClickListener { registrarse() }
    }

    fun irARecuperacion(){
        val intent = Intent(this, RecuperarContraActivity::class.java)
        startActivity(intent)
    }

    fun login(){
        val correo = binding.correo.text
        val password = binding.password.text

        if ( correo.isNotEmpty() && password.isNotEmpty() ){
            try{
                val user = Usuarios.login(correo.toString(), password.toString())

                val sharedPreferences = getSharedPreferences("sesion", Context.MODE_PRIVATE ).edit()
                sharedPreferences.putString("id_usuario", user.id.toString())
                sharedPreferences.putString("correo_usuario", user.correo)
                sharedPreferences.putString("contra_usuario", user.password)
                sharedPreferences.apply()
                startActivity( Intent(this, MainActivity::class.java) )

            }catch(e:Exception){
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_LONG).show()
            }

        }else{
            Toast.makeText(this, "Los campos son obligatorios", Toast.LENGTH_LONG).show()
        }
    }

    fun registrarse(){

        val intentRegistro = Intent(this, RegistroActivity::class.java)
        startActivity(intentRegistro)

    }

}