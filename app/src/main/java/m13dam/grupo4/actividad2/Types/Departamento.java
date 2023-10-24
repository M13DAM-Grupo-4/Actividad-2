package m13dam.grupo4.actividad2.Types;

public class Departamento {

    int ID, ID_Encargado;
    String Nombre;

    public Departamento(int ID, int ID_Encargado, String nombre) {
        this.ID = ID;
        this.ID_Encargado = ID_Encargado;
        Nombre = nombre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Encargado() {
        return ID_Encargado;
    }

    public void setID_Encargado(int ID_Encargado) {
        this.ID_Encargado = ID_Encargado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
