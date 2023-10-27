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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import m13dam.grupo4.actividad2.Database.DatabaseManager;
import m13dam.grupo4.actividad2.Types.Departamento;

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
    private ArrayList <Departamento> arrayDepartamento;
    int posicionSeleccionada;
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


        ArrayList <String> arrayNombreDepartamento = new ArrayList<>();
        ArrayAdapter<String> adaptadorDos_JVM = new ArrayAdapter<>(FormularioEmpleado.this, android.R.layout.simple_spinner_item, arrayNombreDepartamento);


        Thread thread = new Thread(() -> {
            arrayDepartamento = DatabaseManager.GetDepartamentos();
            for(int i = 0; i<arrayDepartamento.toArray().length; i++){
                arrayNombreDepartamento.add(arrayDepartamento.get(i).getNombre());
            }

            Handler handler = new Handler(Looper.getMainLooper());

            handler.post(new Runnable() {
                @Override
                public void run() {
                    adaptadorDos_JVM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    departamento.setAdapter(adaptadorDos_JVM);

                    //Funcion que nos permite obtener la posicion seleccionada en el spinner
                    departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position_JVM, long id_JVM) {
                            posicionSeleccionada = position_JVM;
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                        }
                    });
                }
            });

        });

        thread.start();



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
