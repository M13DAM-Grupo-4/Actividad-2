package m13dam.grupo4.actividad2.Types;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Encargado {

    int ID;
    String Nombre, PApellido, SApellido, Horario_Entrada, Horario_Salida;
    BigDecimal Salario;

    public Encargado(int ID, String nombre, String PApellido, String SApellido,
                     String horario_Entrada, String horario_Salida, BigDecimal salario) {
        this.ID = ID;
        Nombre = nombre;
        this.PApellido = PApellido;
        this.SApellido = SApellido;
        Horario_Entrada = horario_Entrada;
        Horario_Salida = horario_Salida;
        Salario = salario;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPApellido() {
        return PApellido;
    }

    public void setPApellido(String PApellido) {
        this.PApellido = PApellido;
    }

    public String getSApellido() {
        return SApellido;
    }

    public void setSApellido(String SApellido) {
        this.SApellido = SApellido;
    }

    public String getHorario_Entrada() {
        return Horario_Entrada;
    }

    public void setHorario_Entrada(String horario_Entrada) {
        Horario_Entrada = horario_Entrada;
    }

    public String getHorario_Salida() {
        return Horario_Salida;
    }

    public void setHorario_Salida(String horario_Salida) {
        Horario_Salida = horario_Salida;
    }

    public BigDecimal getSalario() {
        return Salario;
    }

    public void setSalario(BigDecimal salario) {
        Salario = salario;
    }
}
