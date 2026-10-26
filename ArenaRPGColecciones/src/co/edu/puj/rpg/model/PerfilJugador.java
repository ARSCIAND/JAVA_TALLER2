package co.edu.puj.rpg.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class PerfilJugador {

    private String nombreJugador;
    private Set<String> logros;

    public PerfilJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.logros = new TreeSet<>(); // TreeSet para que salgan en orden alfabetico solos
    }

    public boolean registrarLogro(String logro) {
        boolean esNuevo = logros.add(logro);
        if (esNuevo) {
            System.out.println("*** Logro desbloqueado: " + logro + " ***");
        }
        return esNuevo;
    }

    public Set<String> listarLogrosOrdenados() {
        return Collections.unmodifiableSet(logros);
    }

    public String getNombreJugador() {
        return nombreJugador;
    }
}
