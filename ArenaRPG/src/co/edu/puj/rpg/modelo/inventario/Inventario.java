package co.edu.puj.rpg.modelo.inventario;

import java.util.ArrayList;
import java.util.List;

public class Inventario<T> {

    private List<T> items = new ArrayList<>();

    public void agregar(T item) {
        items.add(item);
    }

    public T obtener(int index) {
        return items.get(index);
    }

    public List<T> getItems() {
        return items;
    }
}
