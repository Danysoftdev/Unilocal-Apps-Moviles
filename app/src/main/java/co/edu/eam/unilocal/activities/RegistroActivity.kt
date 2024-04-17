package co.edu.eam.unilocal.activities

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

    lateinit var binding:ActivityRegistroBinding
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val boton:Button = findViewById(R.id.btnRegistro)
        boton.setOnClickListener {
            registrar()
        }
    }




    fun registrar(): Boolean{
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

        id++
        return Usuarios.agregar( Usuario(id, nombre.toString(), nickname.toString(), email.toString(), password.toString()) )
    }
}