package co.edu.eam.unilocal.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityRecuperarContraBinding

class RecuperarContraActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecuperarContraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecuperarContraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnviarCodigo.setOnClickListener { irAlCodigo() }
    }

    fun irAlCodigo(){

        val email: String = binding.email.text.toString()

        if (email.isEmpty()){
            Toast.makeText(this, "Debe ingresar un correo", Toast.LENGTH_LONG).show()
        }else{

            if (!Usuarios.verificarCorreo(email)){
                Toast.makeText(this, "Debe ingresar el correo con el que se registró", Toast.LENGTH_LONG).show()
            }else{
                val codigo = (1000..9999).random()

                startActivity(Intent(this, IngresarCodigoActivity::class.java))
                finish()

            }
        }
    }
}