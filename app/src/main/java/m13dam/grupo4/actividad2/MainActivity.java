package m13dam.grupo4.actividad2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;

import java.math.BigDecimal;

import m13dam.grupo4.actividad2.Database.DatabaseManager;
import m13dam.grupo4.actividad2.Types.Empleado;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TEST

        Thread test1 = new Thread(() -> {
            Empleado emp1 = new Empleado(-1,1, new BigDecimal(1500), "Nombre",
                    "1Apellido", "2Apellido", "No trabaja", "08:30",
                    "14:30");

            int final_id = DatabaseManager.AddEmpleado(emp1);
            System.out.println("Id es: " + final_id);
        });
        test1.start();
    }

}
