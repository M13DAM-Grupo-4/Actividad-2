package m13dam.grupo4.actividad2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import m13dam.grupo4.actividad2.Database.DatabaseManager;

public class ListasEmpleados extends AppCompatActivity  {

    private Button añadirEmple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        añadirEmple = findViewById(R.id.añadir_empleado);

        añadirEmple.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                Intent intent = new Intent(ListasEmpleados.this, FormularioEmpleado.class);
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

