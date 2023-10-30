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

import java.math.BigDecimal;
import java.util.ArrayList;

import m13dam.grupo4.actividad2.Database.DatabaseManager;
import m13dam.grupo4.actividad2.Types.Departamento;
import m13dam.grupo4.actividad2.Types.Empleado;

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
    private int DepartElegido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        Toolbar toolbar = findViewById(R.id.toolbarForm);
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
            for(int i = 0;i<arrayDepartamento.toArray().length;i++ ){
                if(arrayNombreDepartamento.get(posicionSeleccionada).equals(arrayDepartamento.get(i).getNombre())){
                    DepartElegido = arrayDepartamento.get(i).getID();
                }
            }

        });

        thread.start();



        enviar.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                DatabaseManager dbm = new DatabaseManager();
                String nombreIntroducido = nombre.getText().toString();
                String primerApellidoIntroducido = p_apellido.getText().toString();
                String segundoApellidoIntroducido = s_apellido.getText().toString();
                String horaEntraIntroducido = hora_entra.getText().toString();
                String horaSaleIntroducido = hora_sale.getText().toString();
                BigDecimal salarioIntroducido = BigDecimal.valueOf(Long.parseLong(salario.getText().toString()));
                String puestoIntroducido = puesto.getText().toString();


                String pattern = "^[a-zA-Z0-9]*$";
                try {
                    Thread thread = new Thread(() -> {
                        if (!nombreIntroducido.isEmpty() && !primerApellidoIntroducido.isEmpty() && !segundoApellidoIntroducido.isEmpty() && !horaEntraIntroducido.isEmpty() && !horaSaleIntroducido.isEmpty() && !salarioIntroducido.equals(null) && !puestoIntroducido.isEmpty()) {
                            int id = dbm.AddEmpleado(new Empleado(-1, DepartElegido, salarioIntroducido, nombreIntroducido, primerApellidoIntroducido, segundoApellidoIntroducido, puestoIntroducido, horaEntraIntroducido, horaSaleIntroducido), "pATO", "PASTO");
                            Handler handler = new Handler(Looper.getMainLooper());

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (id>0){
                                        Toast.makeText(getApplicationContext(), "Empleado Añadido", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Algo paso", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
                        }
                        });

                    thread.start();

                }catch (Exception e) {
                    e.printStackTrace();
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
