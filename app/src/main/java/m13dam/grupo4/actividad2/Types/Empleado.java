package m13dam.grupo4.actividad2.Types;

import java.math.BigDecimal;

public class Empleado {
    int ID, ID_Departamento;
    BigDecimal Salario;
    String Nombre, PApellido, SApellido, PuestoTrabajo, Horario_Entrada, Horario_Salida;;

    public Empleado(int ID, int ID_Departamento, BigDecimal salario, String nombre,
                    String PApellido, String SApellido, String puestoTrabajo,
                    String horario_Entrada, String horario_Salida) {
        this.ID = ID;
        this.ID_Departamento = ID_Departamento;
        Salario = salario;
        Nombre = nombre;
        this.PApellido = PApellido;
        this.SApellido = SApellido;
        PuestoTrabajo = puestoTrabajo;
        Horario_Entrada = horario_Entrada;
        Horario_Salida = horario_Salida;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Departamento() {
        return ID_Departamento;
    }

    public void setID_Departamento(int ID_Departamento) {
        this.ID_Departamento = ID_Departamento;
    }

    public BigDecimal getSalario() {
        return Salario;
    }

    public void setSalario(BigDecimal salario) {
        Salario = salario;
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

    public String getPuestoTrabajo() {
        return PuestoTrabajo;
    }

    public void setPuestoTrabajo(String puestoTrabajo) {
        PuestoTrabajo = puestoTrabajo;
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
}
