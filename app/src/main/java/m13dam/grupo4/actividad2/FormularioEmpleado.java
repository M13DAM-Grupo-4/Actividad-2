package m13dam.grupo4.actividad2;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import m13dam.grupo4.actividad2.Database.DatabaseManager;

public class FormularioEmpleado extends AppCompatActivity {


    private EditText nombre;
    private EditText p_apellido;
    private EditText s_apellido;
    private EditText hora_entra;
    private EditText hora_sale;
    private Spinner departamento;
    private EditText salario;
    private EditText puesto;
    private Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nombre = findViewById(R.id.insertarNombre);
        p_apellido = findViewById(R.id.insertarPapellido);
        s_apellido = findViewById(R.id.insertarSapellido);
        hora_entra = findViewById(R.id.horario_entrada);
        hora_sale = findViewById(R.id.horario_salida);
        departamento = findViewById(R.id.spinner_IdDepartamento);
        salario = findViewById(R.id.insertar_salario);
        puesto = findViewById(R.id.insertar_puesto);
        enviar = findViewById(R.id.enviar_formulario);

        enviar.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                String nombreIntroducido = nombre.getText().toString();
                String primerApellidoIntroducido = p_apellido.getText().toString();
                String segundoApellidoIntroducido = s_apellido.getText().toString();
                String horaEntraIntroducido = hora_entra.getText().toString();
                String horaSaleIntroducido = hora_sale.getText().toString();
                String salarioIntroducido = salario.getText().toString();
                String puestoIntroducido = puesto.getText().toString();


                String pattern = "^[a-zA-Z0-9]*$";
                if (!nombreIntroducido.isEmpty() && !primerApellidoIntroducido.isEmpty() && !segundoApellidoIntroducido.isEmpty() && !horaEntraIntroducido.isEmpty() && !horaSaleIntroducido.isEmpty() && !salarioIntroducido.isEmpty() && !puestoIntroducido.isEmpty()) {


                } else {
                    Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });












        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(FormularioEmpleado.this, ListasEmpleados.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(FormularioEmpleado.this, ListasEmpleados.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
    }
