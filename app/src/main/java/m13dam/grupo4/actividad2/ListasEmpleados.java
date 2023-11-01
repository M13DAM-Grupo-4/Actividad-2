package m13dam.grupo4.actividad2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import m13dam.grupo4.actividad2.Database.DatabaseManager;
import m13dam.grupo4.actividad2.Types.CurrentSession;
import m13dam.grupo4.actividad2.Types.Empleado;
import m13dam.grupo4.actividad2.Types.Encargado;

public class ListasEmpleados extends AppCompatActivity  {

    private Button añadirEmple;
    private Button eliminarEmple;
    private TextView nombreEncargado;
    private TextView apellidosEncargado;
    private RecyclerView listaNombres;
    private RecyclerView listaPApellido;
    private RecyclerView listaSApellido;
    private RecyclerView listaSalario;
    private RecyclerView listaPuesto;

    private ArrayList<Empleado> ListaEmpleados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        añadirEmple = findViewById(R.id.añadir_empleado);
        nombreEncargado = findViewById(R.id.nombreEncargado);
        apellidosEncargado = findViewById(R.id.apellidosEncargado);

        listaNombres = findViewById(R.id.listaEmpleadosNombre);
        listaPApellido = findViewById(R.id.listaEmpleadosPrimerApellido);
        listaSApellido = findViewById(R.id.listaEmpleadosSegundoApellido);
        listaSalario = findViewById(R.id.listaEmpleadosSalario);
        listaPuesto = findViewById(R.id.listaEmpleadosPuesto);

        Thread thread = new Thread(() -> {
            try {
                System.out.println(CurrentSession.getUserID());
                Encargado enc = DatabaseManager.GetEncargadoById(CurrentSession.getUserID());

                ArrayList<Empleado> emps = DatabaseManager.GetEmpleadosById(DatabaseManager.Direccion.ASC);
                System.out.println(emps);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        nombreEncargado.setText(enc.getNombre());
                        apellidosEncargado.setText(enc.getPApellido() + " " + enc.getSApellido());
                        UpdateListaEmpleados(emps);
                    }
                });

            } catch (Exception e){
                e.printStackTrace();
            }
        });
        thread.start();

        añadirEmple.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                Intent intent = new Intent(ListasEmpleados.this, FormularioEmpleado.class);
                startActivity(intent);
                finish();
            }
        });

        eliminarEmple = findViewById(R.id.eliminar_empleado);
        eliminarEmple.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                Intent intent = new Intent(ListasEmpleados.this, EliminarEmple.class);
                startActivity(intent);
                finish();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DatabaseManager.RemoveLoginRemember(ListasEmpleados.this);
                Intent intent = new Intent(ListasEmpleados.this, MainActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);


    }

    private void UpdateListaEmpleados(ArrayList<Empleado> emps) {

        List<String> data_Nombres = new ArrayList<>();
        List<String> data_PApellido = new ArrayList<>();
        List<String> data_SApellido = new ArrayList<>();
        List<String> data_Salario = new ArrayList<>();
        List<String> data_Puesto = new ArrayList<>();
        List<String> data_Hora = new ArrayList<>();

        for (Empleado e : emps){
            data_Nombres.add(e.getNombre());
            data_PApellido.add(e.getPApellido());
            data_SApellido.add(e.getSApellido());
            data_Salario.add(e.getSalario().toString() + "€");
            data_Puesto.add(e.getPuestoTrabajo());
            data_Hora.add(e.getHorario_Entrada() + "-" + e.getHorario_Salida());
        }

        MyAdapter adapterNombres = new MyAdapter(data_Nombres);
        MyAdapter adapterPApellido = new MyAdapter(data_PApellido);
        MyAdapter adapterSApellido = new MyAdapter(data_SApellido);
        MyAdapter adapterSalario = new MyAdapter(data_Salario);
        MyAdapter adapterPuesto = new MyAdapter(data_Puesto);

        listaNombres.setAdapter(adapterNombres);
        listaNombres.setLayoutManager(new LinearLayoutManager(this));

        listaPApellido.setAdapter(adapterPApellido);
        listaPApellido.setLayoutManager(new LinearLayoutManager(this));

        listaSApellido.setAdapter(adapterSApellido);
        listaSApellido.setLayoutManager(new LinearLayoutManager(this));

        listaSalario.setAdapter(adapterSalario);
        listaSalario.setLayoutManager(new LinearLayoutManager(this));

        listaPuesto.setAdapter(adapterPuesto);
        listaPuesto.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DatabaseManager.RemoveLoginRemember(ListasEmpleados.this);
                Intent intent = new Intent(ListasEmpleados.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

}

