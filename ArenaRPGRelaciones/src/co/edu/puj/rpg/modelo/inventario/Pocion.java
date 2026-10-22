package co.edu.puj.rpg.modelo.inventario;

public class Pocion {

    private String nombre;
    private int curacion;

    public Pocion(String nombre, int curacion) {
        this.nombre = nombre;
        this.curacion = curacion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCuracion() {
        return curacion;
    }

    @Override
    public String toString() {
        return nombre + " (+" + curacion + " de vida)";
    }
}
