package co.edu.eam.unilocal.activities

import android.content.Context
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
import co.edu.eam.unilocal.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val boton: Button = findViewById(R.id.btnIniciar)
        boton.setOnClickListener {
            login()
        }
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

                if( user != null ){
                    val sharedPreferences = getSharedPreferences("sesion", Context.MODE_PRIVATE ).edit()
                    sharedPreferences.putString("correo_usuario", user.correo)
                    sharedPreferences.commit()
                    startActivity( Intent(this, MainActivity::class.java) )
                }else{
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_LONG).show()
                }

            }catch(e:Exception){
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_LONG).show()
            }

        }else{
            Toast.makeText(this, "Los campos son obligatorios", Toast.LENGTH_LONG).show()
        }
    }
}