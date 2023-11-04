package m13dam.grupo4.actividad2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import m13dam.grupo4.actividad2.Database.DatabaseManager;
import m13dam.grupo4.actividad2.Types.Departamento;
import m13dam.grupo4.actividad2.Types.Empleado;

public class InfoUsuario extends AppCompatActivity {

    private Button botonGuardar;
    private static Empleado emp;

    public static Empleado getEmp() {
        return emp;
    }

    public static void setEmp(Empleado emp) {
        InfoUsuario.emp = emp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        botonGuardar = findViewById(R.id.guardarUsuario);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragmentUsuario())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(InfoUsuario.this, ListasEmpleados.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

    }

    public void guardarUsuario(View view){

        try {
            String Nombre = SettingsFragmentUsuario.nombre.getText();
            String PApellido = SettingsFragmentUsuario.papellido.getText();
            String SApellido = SettingsFragmentUsuario.sapellido.getText();
            String HEntrada = SettingsFragmentUsuario.hentrada.getText();
            String HSalida = SettingsFragmentUsuario.hsalida.getText();
            BigDecimal Salario = new BigDecimal(SettingsFragmentUsuario.salario.getText());
            String Puesto = SettingsFragmentUsuario.puesto.getText();

            if (HEntrada.length() != 5 || HEntrada.split(":").length != 2){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Formato horario aceptado: De 00:00 a 23:59", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }
            if (Integer.parseInt(HEntrada.split(":")[0]) < 0 || Integer.parseInt(HEntrada.split(":")[0]) > 23){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Formato horario aceptado: De 00:00 a 23:59", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }
            if (Integer.parseInt(HEntrada.split(":")[1]) < 0 || Integer.parseInt(HEntrada.split(":")[1]) > 59){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Formato horario aceptado: De 00:00 a 23:59", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }

            if (HSalida.length() != 5 || HSalida.split(":").length != 2){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Formato horario aceptado: De 00:00 a 23:59", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }
            if (Integer.parseInt(HSalida.split(":")[0]) < 0 || Integer.parseInt(HSalida.split(":")[0]) > 23){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Formato horario aceptado: De 00:00 a 23:59", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }
            if (Integer.parseInt(HSalida.split(":")[1]) < 0 || Integer.parseInt(HSalida.split(":")[1]) > 59){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Formato horario aceptado: De 00:00 a 23:59", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }

            if(Salario.compareTo(new BigDecimal(99999)) > 0 || Salario.compareTo(new BigDecimal(0)) < 0){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "El salario debe comprender entre 0 y 99999", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }

            int Dep = Integer.parseInt(SettingsFragmentUsuario.departamento.getValue());
            
            Empleado newEmpleado = new Empleado(SettingsFragmentUsuario.empleado.getID(), Dep,
                    Salario, Nombre, PApellido, SApellido, Puesto, HEntrada, HSalida);

            InfoUsuario.setEmp(newEmpleado);

        } catch (Exception e){
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> {
            try {
                DatabaseManager.EditEmpleado(emp);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Empleado modificado", Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e){
                e.printStackTrace();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error al modificar el empleado", Toast.LENGTH_LONG).show();
                    }
                });

            }

        });
        thread.start();



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(InfoUsuario.this, ListasEmpleados.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    public static class SettingsFragmentUsuario extends PreferenceFragmentCompat {

        private static Empleado empleado;
        private static Departamento dep;
        private static ArrayList<Departamento> deps;

        public static EditTextPreference nombre;
        public static EditTextPreference papellido;
        public static EditTextPreference sapellido;
        public static EditTextPreference hentrada;
        public static EditTextPreference hsalida;
        public static EditTextPreference salario;
        public static EditTextPreference puesto;
        public static ListPreference departamento;

        public static void setDep(Departamento dep) {
            SettingsFragmentUsuario.dep = dep;
        }

        public static void setDeps(ArrayList<Departamento> deps) {
            SettingsFragmentUsuario.deps = deps;
        }

        public static Empleado getEmpleado() {
            return empleado;
        }

        public static void setEmpleado(Empleado empleado) {
            SettingsFragmentUsuario.empleado = empleado;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            nombre = getPreferenceManager().findPreference("usuario-nombre");
            nombre.setText(empleado.getNombre());

            papellido = getPreferenceManager().findPreference("usuario-papellido");
            papellido.setText(empleado.getPApellido());

            sapellido = getPreferenceManager().findPreference("usuario-sapellido");
            sapellido.setText(empleado.getSApellido());

            hentrada = getPreferenceManager().findPreference("usuario-hentrada");
            hentrada.setText(empleado.getHorario_Entrada());

            hsalida = getPreferenceManager().findPreference("usuario-hsalida");
            hsalida.setText(empleado.getHorario_Salida());

            salario = getPreferenceManager().findPreference("usuario-salario");
            salario.setText(empleado.getSalario().toString());

            puesto = getPreferenceManager().findPreference("usuario-puesto");
            puesto.setText(empleado.getPuestoTrabajo());


            ArrayList<String> depsnom = new ArrayList<>();
            ArrayList<String> depsid = new ArrayList<>();

            for (Departamento d : deps){
                depsnom.add(d.getNombre());
                depsid.add(String.valueOf(d.getID()));
            }

            String[] newEntries = depsnom.toArray(new String[depsnom.size()]);
            String[] newEntriesValues = depsid.toArray(new String[depsid.size()]);

            departamento = getPreferenceManager().findPreference("usuarioDep");
            departamento.setEntries(newEntries);
            departamento.setEntryValues(newEntriesValues);
            departamento.setValue(String.valueOf(empleado.getID_Departamento()));

        }

    }
}
