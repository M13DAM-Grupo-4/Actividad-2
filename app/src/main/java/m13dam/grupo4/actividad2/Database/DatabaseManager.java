package m13dam.grupo4.actividad2.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Properties;

import m13dam.grupo4.actividad2.BuildConfig;
import m13dam.grupo4.actividad2.Types.Empleado;

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

    public static int AddEmpleado(Empleado empleado){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.empleados (nombre, p_apellido, " +
                    "s_apellido, salario, horario_entrada, horario_salida, " +
                    "id_departamento, puesto_trabajo) VALUES" +
                    "(?,?,?,?,?,?,?,?) RETURNING id");
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getPApellido());
            stmt.setString(3, empleado.getSApellido());
            stmt.setBigDecimal(4, empleado.getSalario());
            stmt.setString(5, empleado.getHorario_Entrada());
            stmt.setString(6, empleado.getHorario_Salida());
            stmt.setInt(7, empleado.getID_Departamento());
            stmt.setString(8, empleado.getPuestoTrabajo());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                return rs.getInt(1);
            }

            stmt.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

}
