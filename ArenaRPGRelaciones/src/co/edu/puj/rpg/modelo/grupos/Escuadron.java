package co.edu.puj.rpg.modelo.grupos;

import co.edu.puj.rpg.modelo.Personaje;

import java.util.ArrayList;
import java.util.List;

public class Escuadron {

    private String nombre;
    private List<Personaje> miembros;

    public Escuadron(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }

    public void reclutarPersonaje(Personaje p) {
        if (p == null) {
            System.out.println("No se puede reclutar a nadie.");
            return;
        }
        if (miembros.contains(p)) {
            System.out.println(p.getNombre() + " ya esta en el escuadron " + nombre + ".");
            return;
        }
        miembros.add(p);
        System.out.println(p.getNombre() + " fue reclutado en el escuadron " + nombre + ".");
    }

    public void expulsarPersonaje(Personaje p) {
        boolean estaba = miembros.remove(p);
        if (estaba) {
            // ojo: solo sale de la lista, el personaje queda igual
            System.out.println(p.getNombre() + " salio del escuadron " + nombre + ", pero sigue igual que antes.");
        } else {
            System.out.println("Ese personaje no estaba en el escuadron.");
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
        sb.append("Escuadron " + nombre + " (" + miembros.size() + " integrantes): ");
        if (miembros.isEmpty()) {
            sb.append("vacio");
        }
        for (int i = 0; i < miembros.size(); i++) {
            Personaje p = miembros.get(i);
            sb.append(p.getNombre() + " [" + p.getVida() + " PV]");
            if (i < miembros.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
