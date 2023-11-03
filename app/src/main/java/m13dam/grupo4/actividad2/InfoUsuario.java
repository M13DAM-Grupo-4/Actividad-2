package m13dam.grupo4.actividad2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import m13dam.grupo4.actividad2.Types.Empleado;

public class InfoUsuario extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
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
    }

    public static class SettingsFragmentUsuario extends PreferenceFragmentCompat {

        private static Empleado empleado;

        public static Empleado getEmpleado() {
            return empleado;
        }

        public static void setEmpleado(Empleado empleado) {
            SettingsFragmentUsuario.empleado = empleado;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            EditTextPreference nombre = getPreferenceManager().findPreference("usuario-nombre");
            nombre.setText(empleado.getNombre());

            EditTextPreference papellido = getPreferenceManager().findPreference("usuario-papellido");
            papellido.setText(empleado.getPApellido());

            EditTextPreference sapellido = getPreferenceManager().findPreference("usuario-sapellido");
            sapellido.setText(empleado.getSApellido());

            EditTextPreference hentrada = getPreferenceManager().findPreference("usuario-hentrada");
            hentrada.setText(empleado.getHorario_Entrada());

            EditTextPreference hsalida = getPreferenceManager().findPreference("usuario-hsalida");
            hsalida.setText(empleado.getHorario_Salida());

            EditTextPreference salario = getPreferenceManager().findPreference("usuario-salario");
            salario.setText(empleado.getSalario().toString());

            EditTextPreference puesto = getPreferenceManager().findPreference("usuario-puesto");
            puesto.setText(empleado.getPuestoTrabajo());




        }


    }
}