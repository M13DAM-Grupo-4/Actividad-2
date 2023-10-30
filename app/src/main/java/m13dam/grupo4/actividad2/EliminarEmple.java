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
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

import m13dam.grupo4.actividad2.Database.DatabaseManager;

import m13dam.grupo4.actividad2.Types.Empleado;

public class EliminarEmple extends AppCompatActivity {

    private Spinner empleados;
    private Button eliminar;
    private ArrayList<Empleado> arrayEmpleadosElim;
    private int posicionSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_emple);

        empleados = findViewById(R.id.spinnerEliminarEmpleado);
        eliminar = findViewById(R.id.buttonEliminarEmpleado);
        Toolbar toolbar = findViewById(R.id.toolbarElim);
        setSupportActionBar(toolbar);

        ArrayList<String> arrayDatosEmpleado = new ArrayList<>(); // Cambiamos el nombre para reflejar más datos
        ArrayAdapter<String> adaptadorDos = new ArrayAdapter<>(EliminarEmple.this, android.R.layout.simple_spinner_item, arrayDatosEmpleado);

        Thread thread = new Thread(() -> {
            arrayEmpleadosElim = DatabaseManager.GetEmpleados();
            for (int i = 0; i < arrayEmpleadosElim.size(); i++) {
                // Construimos una cadena con todos los datos que deseas mostrar
                String datosEmpleado = arrayEmpleadosElim.get(i).getNombre() + " "+
                        arrayEmpleadosElim.get(i).getPApellido() + " " +
                        arrayEmpleadosElim.get(i).getSApellido() + " - " +
                        arrayEmpleadosElim.get(i).getID_Departamento()
                        ;
                arrayDatosEmpleado.add(datosEmpleado);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adaptadorDos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    empleados.setAdapter(adaptadorDos);

                    // Función que nos permite obtener la posición seleccionada en el spinner
                    empleados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        eliminar.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                DatabaseManager dbmElim = new DatabaseManager();

                String pattern = "^[a-zA-Z0-9]*$";
                try {
                    Thread thread = new Thread(() -> {
                        if ( posicionSeleccionada>-1) {
                            arrayEmpleadosElim.get(posicionSeleccionada).getID();
                            Handler handler = new Handler(Looper.getMainLooper());

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "Por favor, selecciona un empleado a borrar", Toast.LENGTH_LONG).show();
                        }
                    });

                    thread.start();

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(EliminarEmple.this, ListasEmpleados.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(EliminarEmple.this, ListasEmpleados.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}