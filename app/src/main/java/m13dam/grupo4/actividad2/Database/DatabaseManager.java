package m13dam.grupo4.actividad2.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import m13dam.grupo4.actividad2.BuildConfig;
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

    public static int LoginRemember(@Nullable Context c){
        try {
            SQLiteDatabase dbl = GetLocalDB(c);
            Cursor cr = dbl.rawQuery("SELECT id FROM login WHERE remember=true AND id=1", null);
            if (cr.moveToFirst()){
                do {
                    // Passing values
                    int ResId = Integer.parseInt(cr.getString(0));
                    return ResId;
                } while(cr.moveToNext());
            }
            dbl.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void SaveLoginRemember(int userid, @Nullable Context c){
        try{
            SQLiteDatabase dbl = GetLocalDB(c);
            dbl.execSQL("DELETE FROM login");
            dbl.execSQL("INSERT INTO login (id, user_id, remember) VALUES (1,'"+userid+"', true)");
            dbl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RemoveLoginRemember(@Nullable Context c){
        try{
            SQLiteDatabase dbl = GetLocalDB(c);
            dbl.execSQL("DELETE FROM login");
            dbl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int Login(String user, String pass){
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

    public static Empleado GetEmpleadoById(int empleado_id){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM public.empleados WHERE id="+empleado_id+"");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                String p_apellido = rs.getString(3);
                String s_apellido = rs.getString(4);
                BigDecimal salario = rs.getBigDecimal(5);
                int id_departamento = rs.getInt(6);
                String puesto_trabajo = rs.getString(7);
                String horario_entrada = rs.getString(8);
                String horario_salida = rs.getString(9);

                Empleado emp = new Empleado(id, id_departamento, salario, nombre, p_apellido,
                        s_apellido, puesto_trabajo, horario_entrada, horario_salida);

                rs.close();
                stmt.close();
                c.close();

                return emp;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Empleado> GetEmpleados(){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM public.empleados");
            ResultSet rs = stmt.executeQuery();

            ArrayList<Empleado> Empleados = new ArrayList<>();

            while(rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                String p_apellido = rs.getString(3);
                String s_apellido = rs.getString(4);
                BigDecimal salario = rs.getBigDecimal(5);
                int id_departamento = rs.getInt(6);
                String puesto_trabajo = rs.getString(7);
                String horario_entrada = rs.getString(8);
                String horario_salida = rs.getString(9);

                Empleado emp = new Empleado(id, id_departamento, salario, nombre, p_apellido,
                        s_apellido, puesto_trabajo, horario_entrada, horario_salida);

                Empleados.add(emp);
            }

            rs.close();
            stmt.close();
            c.close();

            return Empleados;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Departamento GetDepartamentoById(int departamento_id){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM public.departamentos WHERE id="+departamento_id+"");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                int id_encargado = rs.getInt(3);

                Departamento dep = new Departamento(id, id_encargado, nombre);

                rs.close();
                stmt.close();
                c.close();

                return dep;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Departamento> GetDepartamentos(){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM public.departamentos");
            ResultSet rs = stmt.executeQuery();

            ArrayList<Departamento> Departamentos = new ArrayList<>();

            while(rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                int id_encargado = rs.getInt(3);

                Departamento dep = new Departamento(id, id_encargado, nombre);

                Departamentos.add(dep);
            }

            rs.close();
            stmt.close();
            c.close();

            return Departamentos;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Encargado GetEncargadoById(int encargado_id){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM public.encargados WHERE id="+encargado_id+"");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                String p_apellido = rs.getString(3);
                String s_apellido = rs.getString(4);
                BigDecimal salario = rs.getBigDecimal(5);
                //String usuario = rs.getString(6);
                //String contra = rs.getString(7);
                String horario_entrada = rs.getString(8);
                String horario_salida = rs.getString(9);

                Encargado enc = new Encargado(id, nombre, p_apellido, s_apellido, horario_entrada,
                        horario_salida, salario);

                rs.close();
                stmt.close();
                c.close();

                return enc;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Encargado> GetEncargados(){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM public.encargados");
            ResultSet rs = stmt.executeQuery();

            ArrayList<Encargado> Encargados = new ArrayList<>();

            while(rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                String p_apellido = rs.getString(3);
                String s_apellido = rs.getString(4);
                BigDecimal salario = rs.getBigDecimal(5);
                //String usuario = rs.getString(6);
                //String contra = rs.getString(7);
                String horario_entrada = rs.getString(8);
                String horario_salida = rs.getString(9);

                Encargado enc = new Encargado(id, nombre, p_apellido, s_apellido, horario_entrada,
                        horario_salida, salario);


                Encargados.add(enc);
            }

            rs.close();
            stmt.close();
            c.close();

            return Encargados;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
