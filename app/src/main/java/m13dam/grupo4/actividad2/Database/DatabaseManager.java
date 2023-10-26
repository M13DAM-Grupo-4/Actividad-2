package m13dam.grupo4.actividad2.Database;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import m13dam.grupo4.actividad2.BuildConfig;
import m13dam.grupo4.actividad2.MainActivity;
import m13dam.grupo4.actividad2.Types.Departamento;
import m13dam.grupo4.actividad2.Types.Empleado;
import m13dam.grupo4.actividad2.Types.Encargado;

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

    public static SQLiteDatabase GetLocalDB(@Nullable Context c){
         return new LocalDatabaseManager(c).getWritableDatabase();
    }

    public static int Login(String user, String pass, @Nullable Context ctx){
        SQLiteDatabase db = GetLocalDB(ctx);
        try {

            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT id FROM public.encargados WHERE usuario=? AND contra=?");
            stmt.setString(1, user);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int AddEmpleado(Empleado empleado, String Usuario, String Contra){
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

    public static int AddEncargado(Encargado encargado, String Usuario, String Contra){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.encargados (nombre, p_apellido, " +
                    "s_apellido, salario, horario_entrada, horario_salida, usuario, contra)" + " VALUES" +
                    "(?,?,?,?,?,?,?,?) RETURNING id");
            stmt.setString(1, encargado.getNombre());
            stmt.setString(2, encargado.getPApellido());
            stmt.setString(3, encargado.getSApellido());
            stmt.setBigDecimal(4, encargado.getSalario());
            stmt.setString(5, encargado.getHorario_Entrada());
            stmt.setString(6, encargado.getHorario_Salida());
            stmt.setString(7, Usuario);
            stmt.setString(8, Contra);

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

    public static int AddDepartamento(Departamento departamento){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.departamentos (nombre, id_encargado)"
                    + " VALUES (?,?) RETURNING id");
            stmt.setString(1, departamento.getNombre());
            stmt.setInt(2, departamento.getID_Encargado());

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
