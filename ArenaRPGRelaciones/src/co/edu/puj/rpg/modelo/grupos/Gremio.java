package co.edu.puj.rpg.modelo.grupos;

import co.edu.puj.rpg.modelo.Personaje;

import java.util.ArrayList;
import java.util.List;

public class Gremio {

    private String nombre;
    private List<Personaje> miembros;

    public Gremio(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }

    public void agregarMiembro(Personaje p) {
        if (p == null) {
            return;
        }
        if (!miembros.contains(p)) {
            miembros.add(p);
        }
        // si lo agregaron desde el gremio, el personaje todavia no se ha enterado
        if (p.getGremio() != this) {
            p.unirseAGremio(this);
        }
    }

    public void removerMiembro(Personaje p) {
        if (p == null) {
            return;
        }
        miembros.remove(p);
        if (p.getGremio() == this) {
            p.salirDelGremio();
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Personaje> getMiembros() {
        return miembros;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gremio " + nombre + " (" + miembros.size() + " miembros): ");
        if (miembros.isEmpty()) {
            sb.append("nadie todavia");
        }
        for (int i = 0; i < miembros.size(); i++) {
            sb.append(miembros.get(i).getNombre());
            if (i < miembros.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
