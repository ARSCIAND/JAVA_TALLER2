package co.edu.puj.rpg.model;

import co.edu.puj.rpg.model.enums.RaridadItem;

public class Item {

    private String nombre;
    private int poderBase;
    private RaridadItem rareza;

    public Item(String nombre, int poderBase, RaridadItem rareza) {
        this.nombre = nombre;
        this.poderBase = poderBase;
        this.rareza = rareza;
    }

    public int getPoder() {
        return (int) Math.round(poderBase * rareza.getMultiplicadorStat());
    }

    public String getNombre() {
        return nombre;
    }

    public RaridadItem getRareza() {
        return rareza;
    }

    @Override
    public String toString() {
        return nombre + " [" + rareza + "] poder " + getPoder();
    }
}
