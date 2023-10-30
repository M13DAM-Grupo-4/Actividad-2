package m13dam.grupo4.actividad2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import m13dam.grupo4.actividad2.Database.DatabaseManager;
import m13dam.grupo4.actividad2.Types.CurrentSession;

public class MainActivity extends AppCompatActivity {

    private Button boton;
    private EditText usuario;
    private EditText contraseña;
    private CheckBox rememberme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usuario = findViewById(R.id.usuario_layout);
        contraseña = findViewById(R.id.contraseña_layout);

        boton = findViewById(R.id.boton_login);

        rememberme = findViewById(R.id.checkBox);

        Thread thread = new Thread(() -> {
            int RememberedID = DatabaseManager.LoginRemember(MainActivity.this);
            if(RememberedID > 0){
                CurrentSession.setUserID(RememberedID);
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        abrirNuevaActividad(false);
                    }
                });
            }
        });
        thread.start();

        boton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                String usuarioIntroducido_JVM = usuario.getText().toString();
                String contraseñaIntroducida_JVM = contraseña.getText().toString();
                String pattern = "^[a-zA-Z0-9]*$";
                if (!usuarioIntroducido_JVM.isEmpty() && !contraseñaIntroducida_JVM.isEmpty()) {

                    if(usuarioIntroducido_JVM.matches(pattern)){

                        if(!(contraseñaIntroducida_JVM.length()<4) ) {

                            if(!(contraseñaIntroducida_JVM.length()>=8)){

                                Thread thread = new Thread(() -> {
                                    int RememberedID = DatabaseManager.LoginRemember(MainActivity.this);
                                    if(RememberedID > 0){
                                        CurrentSession.setUserID(RememberedID);
                                    } else {
                                        int LoginID = DatabaseManager.Login(usuarioIntroducido_JVM,contraseñaIntroducida_JVM);
                                        CurrentSession.setUserID(LoginID);
                                    }
                                    Handler handler = new Handler(Looper.getMainLooper());

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            abrirNuevaActividad(rememberme.isChecked());
                                        }
                                    });

                                });
                                thread.start();

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



    private void abrirNuevaActividad(boolean remember) {

        if (CurrentSession.getUserID() > 0) {
            Intent intent = new Intent(MainActivity.this, ListasEmpleados.class);
            startActivity(intent);
            if (remember){
                DatabaseManager.SaveLoginRemember(CurrentSession.getUserID(), MainActivity.this);
            }
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

}
