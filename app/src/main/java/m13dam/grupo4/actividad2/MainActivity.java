package m13dam.grupo4.actividad2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

import m13dam.grupo4.actividad2.Database.DatabaseManager;
import m13dam.grupo4.actividad2.Types.Empleado;

public class MainActivity extends AppCompatActivity {

    private Button boton;
    private EditText usuario;
    private EditText contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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

                        if(!(contraseñaIntroducida_JVM.length()<4) ) {

                            if(!(contraseñaIntroducida_JVM.length()>=8)){
                                DatabaseManager comprobacion = new DatabaseManager();
                                int validacion = comprobacion.Login(usuarioIntroducido_JVM,contraseñaIntroducida_JVM);
                                abrirNuevaActividad(validacion);

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

    }



    private void abrirNuevaActividad(int valor) {
        // Si el usuario es válido, abrir la nueva actividad
        if (valor>=-1) {
            Intent intent = new Intent(this, listasempleados.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

}
