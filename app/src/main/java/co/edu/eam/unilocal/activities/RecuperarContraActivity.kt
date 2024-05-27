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
<<<<<<< HEAD

        val email: String = binding.email.text.toString().trim();
=======
/*
        val email: String = binding.email.text.toString()
>>>>>>> 3ddfbd1330826106d78565794b7bfa6111d184bc

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
            Toast.makeText(this, "Debe ingresar un correo válido", Toast.LENGTH_LONG).show();
            return;
        }else{

            if (!Usuarios.verificarCorreo(email)){
                Toast.makeText(this, "Debe ingresar el correo con el que se registró", Toast.LENGTH_LONG).show()
            }else{
                FireBaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task){

                    }
                })

                startActivity(Intent(this, IngresarCodigoActivity::class.java))
                finish()

            }
        }*/
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