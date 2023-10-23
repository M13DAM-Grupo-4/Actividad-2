package m13dam.grupo4.actividad2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import m13dam.grupo4.actividad2.Database.DatabaseManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.out.println(DatabaseManager.GetTables());
    }
}
