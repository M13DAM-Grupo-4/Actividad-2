package m13dam.grupo4.actividad2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

import m13dam.grupo4.actividad2.Database.DatabaseManager;
import m13dam.grupo4.actividad2.Types.Departamento;
import m13dam.grupo4.actividad2.Types.Empleado;
import m13dam.grupo4.actividad2.Types.Encargado;

public class MainActivity extends AppCompatActivity {

    private Button boton;
    private EditText usuario;
    private EditText contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Crear base de datos local
        DatabaseManager.GetLocalDB(MainActivity.this);

        usuario = findViewById(R.id.usuario_layout);
        contraseña = findViewById(R.id.contraseña_layout);

        boton = findViewById(R.id.boton_login);

        boton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                String usuarioIntroducido_JVM = usuario.getText().toString();
                String contraseñaIntroducida_JVM = contraseña.getText().toString();
                String pattern = "^[a-zA-Z0-9]*$";
                if (!usuarioIntroducido_JVM.isEmpty() && !contraseñaIntroducida_JVM.isEmpty()) {
                    if(usuarioIntroducido_JVM.matches(pattern)){

                        if(!(contraseñaIntroducida_JVM.length()<=4) ) {
                            if(!(contraseñaIntroducida_JVM.length()>=8)){



                            } else {
                                Toast.makeText(getApplicationContext(), "Por favor, la contraseña debe tener 8 digitos o menos", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Por favor, la contraseña debe tener 4 digitos o mas", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Por favor, el nombre de usuario solo puede tener numeros o letras", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor, complete ambos campos", Toast.LENGTH_LONG).show();
                }
            }
        });











        // TEST

        Thread test1 = new Thread(() -> {



        });
        test1.start();
    }

}
