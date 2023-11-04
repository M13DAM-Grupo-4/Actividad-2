package m13dam.grupo4.actividad2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private GridView listaGrid;

    String ordenadoPor = "";
    DatabaseManager.Direccion direccion;

    private ArrayList<Empleado> ListaEmpleados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        añadirEmple = findViewById(R.id.añadir_empleado);
        nombreEncargado = findViewById(R.id.nombreEncargado);
        apellidosEncargado = findViewById(R.id.apellidosEncargado);

        listaGrid = findViewById(R.id.listaGrid);

        Thread thread = new Thread(() -> {
            try {
                System.out.println(CurrentSession.getUserID());
                Encargado enc = DatabaseManager.GetEncargadoById(CurrentSession.getUserID());

                ArrayList<Empleado> emps = DatabaseManager.GetEmpleadosByNombre(DatabaseManager.Direccion.ASC);
                System.out.println(emps);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    nombreEncargado.setText(enc.getNombre());
                    apellidosEncargado.setText(enc.getPApellido() + " " + enc.getSApellido());
                    UpdateListaEmpleados(emps);
                    ordenadoPor = "nombre";
                    direccion = DatabaseManager.Direccion.ASC;
                });

            } catch (Exception e){
                e.printStackTrace();
            }
        });
        thread.start();

        añadirEmple.setOnClickListener(v -> {
            Intent intent = new Intent(ListasEmpleados.this, FormularioEmpleado.class);
            startActivity(intent);
            finish();
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

        List<Item> data_Lista = new ArrayList<>(); // Replace with your data source

        for (Empleado e : emps){
            data_Lista.add(new Item(e.getID(),e.getNombre()));
            data_Lista.add(new Item(e.getID(),e.getPApellido()));
            data_Lista.add(new Item(e.getID(),e.getSApellido()));
            data_Lista.add(new Item(e.getID(),e.getPuestoTrabajo()));
        }

        MyAdapter adapter = new MyAdapter(this, data_Lista);
        listaGrid.setAdapter(adapter);

        listaGrid.setOnItemClickListener((parent, view, position, id) -> {
            Thread thread = new Thread(() -> {
                int fpos = (int) Math.ceil(position / 4);
                InfoUsuario.SettingsFragmentUsuario.setEmpleado(emps.get(fpos));
                InfoUsuario.SettingsFragmentUsuario.setDep(DatabaseManager.GetDepartamentoById(emps.get(fpos).getID_Departamento()));
                InfoUsuario.SettingsFragmentUsuario.setDeps(DatabaseManager.GetDepartamentos());
                Intent intent = new Intent(ListasEmpleados.this, InfoUsuario.class);
                startActivity(intent);
            });
            thread.start();
        });

    }

    public void OrdenarPorNombre(View view) {
        Thread thread = new Thread(() -> {
            if (!ordenadoPor.equals("nombre")){
                direccion = DatabaseManager.Direccion.ASC;
            } else if (direccion == DatabaseManager.Direccion.ASC){
                direccion = DatabaseManager.Direccion.DESC;
            } else if (direccion == DatabaseManager.Direccion.DESC) {
                direccion = DatabaseManager.Direccion.ASC;
            }
            ArrayList<Empleado> emp = DatabaseManager.GetEmpleadosByNombre(direccion);
            ordenadoPor = "nombre";
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    UpdateListaEmpleados(emp);
                }
            });

        });
        thread.start();
    }

    public void OrdenarPorPrimerApellido(View view) {
        Thread thread = new Thread(() -> {
            if (!ordenadoPor.equals("papellido")){
                direccion = DatabaseManager.Direccion.ASC;
            } else if (direccion == DatabaseManager.Direccion.ASC){
                direccion = DatabaseManager.Direccion.DESC;
            } else if (direccion == DatabaseManager.Direccion.DESC) {
                direccion = DatabaseManager.Direccion.ASC;
            }
            ArrayList<Empleado> emp = DatabaseManager.GetEmpleadosByPApellido(direccion);
            ordenadoPor = "papellido";
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    UpdateListaEmpleados(emp);
                }
            });

        });
        thread.start();
    }

    public void OrdenarPorSegundoApellido(View view) {
        Thread thread = new Thread(() -> {
            if (!ordenadoPor.equals("sapellido")){
                direccion = DatabaseManager.Direccion.ASC;
            } else if (direccion == DatabaseManager.Direccion.ASC){
                direccion = DatabaseManager.Direccion.DESC;
            } else if (direccion == DatabaseManager.Direccion.DESC) {
                direccion = DatabaseManager.Direccion.ASC;
            }
            ArrayList<Empleado> emp = DatabaseManager.GetEmpleadosBySApellido(direccion);
            ordenadoPor = "sapellido";
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    UpdateListaEmpleados(emp);
                }
            });

        });
        thread.start();
    }

    public void OrdenarPorPuesto(View view) {
            Thread thread = new Thread(() -> {
            if (!ordenadoPor.equals("puesto")){
                direccion = DatabaseManager.Direccion.ASC;
            } else if (direccion == DatabaseManager.Direccion.ASC){
                direccion = DatabaseManager.Direccion.DESC;
            } else if (direccion == DatabaseManager.Direccion.DESC) {
                direccion = DatabaseManager.Direccion.ASC;
            }
            ArrayList<Empleado> emp = DatabaseManager.GetEmpleadosByPuestoTrabajo(direccion);
            ordenadoPor = "puesto";
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    UpdateListaEmpleados(emp);
                }
            });

        });
        thread.start();
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

