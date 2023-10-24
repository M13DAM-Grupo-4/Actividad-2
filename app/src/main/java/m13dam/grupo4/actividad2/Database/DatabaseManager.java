package m13dam.grupo4.actividad2.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import m13dam.grupo4.actividad2.BuildConfig;

/* Ejemplo de ejecutar consultas en segundo plano(Obligatorio en Android).

        Thread testThread = new Thread(() -> {
            try  {
                System.out.println(DatabaseManager.GetTables());;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        testThread.start();

*/

public class DatabaseManager {

    public static Connection CreateConnection(){

        String DB_DATABASE = BuildConfig.PostgreSQL_DB_DATABASE;
        String DB_HOST = BuildConfig.PostgreSQL_DB_HOST;
        int DB_PORT = Integer.parseInt(BuildConfig.PostgreSQL_DB_PORT);
        String jdbcUrl = "jdbc:postgresql://"+ DB_HOST +":"+DB_PORT+"/"+ DB_DATABASE;
        String user = BuildConfig.PostgreSQL_DB_USER;
        String password = BuildConfig.PostgreSQL_DB_PASSWORD;
        String sslMode = "require";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("sslmode", sslMode);

        try {
            return DriverManager.getConnection(jdbcUrl, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<String> GetTables(){

        ArrayList<String> Tablas = new ArrayList<>();
        try {
            Connection c = DatabaseManager.CreateConnection();
            String SQL = "SELECT table_name\n" +
                    "  FROM information_schema.tables\n" +
                    " WHERE table_schema='public'\n" +
                    "   AND table_type='BASE TABLE';";
            assert c != null;
            PreparedStatement stmt = c.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while ( rs.next() ) {
                Tablas.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            c.close();
            return Tablas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
