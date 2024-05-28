package co.edu.eam.unilocal.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityRecuperarContraBinding

import com.google.firebase.auth.FireBaseAuth;

class RecuperarContraActivity : AppCompatActivity() {

    Button btn;
    EditText txtEmail
    FireBaseAuth mAuth;
    String email;


    lateinit var binding: ActivityRecuperarContraBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecuperarContraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btn = findViewById(R.id.btnEnviarCodigo)
        txtEmail = findViewById(R.id.email)
        btn = findViewById(R.id.btnEnviarCodigo)

        mAuth = FirebaseAuth.getInstance()

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                email = txtEmail.getText().toString().trim();
                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
                    Toast.makeText(this, "Debe ingresar un correo válido", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    ResetPassword();
                }
            }
        })


        binding.btnEnviarCodigo.setOnClickListener { irAlCodigo() }
    }

    fun irAlCodigo(){


        val email: String = binding.email.text.toString().trim();


    }

    fun ResetPassowrd(){

        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccesListener<Void>(){
            @Override
            public void onSuccess(Void unused){
                Toast.makeText(this, "Se ha enviado el codigo de recuperacion al correo", Toast.LENGTH_LONG).show()
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailre(@NonNull Exception e){
                Toast.makeText(this, "hubo un error", Toast.LENGTH_LONG).show()
            }
        })

    }

}