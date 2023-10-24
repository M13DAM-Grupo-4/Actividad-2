package m13dam.grupo4.actividad2.Database;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import m13dam.grupo4.actividad2.BuildConfig;

public class DatabaseManager {

    private static String DB_HOST = BuildConfig.PostgreSQL_DB_HOST;
    private static int DB_PORT = Integer.parseInt(BuildConfig.PostgreSQL_DB_PORT);
    private static String DB_DATABASE = BuildConfig.PostgreSQL_DB_DATABASE;
    private static String DB_USER = BuildConfig.PostgreSQL_DB_USER;
    private static String DB_PASSWORD = BuildConfig.PostgreSQL_DB_PASSWORD;

    public static Connection CreateConnection(){

        String jdbcUrl = "jdbc:postgresql://"+DB_HOST+":"+DB_PORT+"/"+DB_DATABASE;
        String user = DB_USER;
        String password = DB_PASSWORD;
        String sslMode = "require";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("sslmode", sslMode);

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, props);
            return connection;
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
            PreparedStatement stmt = c.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while ( rs.next() ) {
                Tablas.add(rs.getString(1));
            }
            return Tablas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
