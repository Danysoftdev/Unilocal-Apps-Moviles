package co.edu.eam.unilocal.activities
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.edu.eam.unilocal.databinding.ActivityRecuperarContraBinding
import com.google.firebase.auth.FirebaseAuth

class RecuperarContraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecuperarContraBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarContraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.btnEnviarCodigo.setOnClickListener { resetPassword() }
    }

    private fun resetPassword() {
        val email = binding.email.text.toString().trim()

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Debe ingresar un correo válido", Toast.LENGTH_LONG).show()
            return
        }

        mAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(this, "Se ha enviado el código de recuperación al correo", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Hubo un error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
