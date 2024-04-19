package co.edu.eam.unilocal.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityIngresarCodigoBinding

class IngresarCodigoActivity : AppCompatActivity() {

    lateinit var binding: ActivityIngresarCodigoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIngresarCodigoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnResetPassword.setOnClickListener { irALogin() }
    }

    fun irALogin(){
        startActivity( Intent(this, LoginActivity::class.java) )
    }
}