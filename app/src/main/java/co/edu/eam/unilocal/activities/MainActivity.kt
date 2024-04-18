package co.edu.eam.unilocal.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.unilocal.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menuInicio -> Toast.makeText(this, "Home", Toast.LENGTH_LONG).show()
            R.id.menuLugares -> Toast.makeText(this, "Mis lugares", Toast.LENGTH_LONG).show()
            R.id.menuFavoritos -> Toast.makeText(this, "Favoritos", Toast.LENGTH_LONG).show()
            R.id.menuTopSemanal -> Toast.makeText(this, "Top semanal", Toast.LENGTH_LONG).show()
        }

        return super.onOptionsItemSelected(item)
    }
}