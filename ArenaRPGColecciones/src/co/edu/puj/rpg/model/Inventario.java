package co.edu.puj.rpg.model;

import co.edu.puj.rpg.exception.CapacidadExcedidaException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventario<T> {

    private List<T> items;
    private int capacidadMaxima;

    public Inventario(int capacidadMaxima) {
        this.items = new ArrayList<>();
        this.capacidadMaxima = capacidadMaxima;
    }

    public void agregar(T item) throws CapacidadExcedidaException {
        if (items.size() >= capacidadMaxima) {
            throw new CapacidadExcedidaException("La mochila esta llena (" + capacidadMaxima + " objetos), no cabe nada mas.");
        }
        items.add(item);
    }

    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int tamano() {
        return items.size();
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
}
