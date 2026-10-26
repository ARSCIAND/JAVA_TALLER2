package co.edu.puj.rpg.engine;

import co.edu.puj.rpg.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.exception.SinManaException;
import co.edu.puj.rpg.model.Item;
import co.edu.puj.rpg.model.PerfilJugador;
import co.edu.puj.rpg.model.Personaje;
import co.edu.puj.rpg.model.enums.EstadoPersonaje;
import co.edu.puj.rpg.model.enums.RaridadItem;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class CombateEngine {

    private Bestiario bestiario;
    private PerfilJugador perfil;
    private Map<String, Double> estadisticas;
    private Random random;

    public CombateEngine(Bestiario bestiario, PerfilJugador perfil) {
        this.bestiario = bestiario;
        this.perfil = perfil;
        this.estadisticas = new TreeMap<>();
        this.random = new Random();
    }

    public void registrarVictoria(Personaje p, String enemigo) throws CapacidadExcedidaException {
        boolean primeraVez = !bestiario.yaFueDerrotado(enemigo);
        int veces = bestiario.registrarDerrota(enemigo);
        System.out.println(p.getNombre() + " derroto a un " + enemigo + ". Van " + veces + " en total.");

        perfil.registrarLogro("Primera Victoria");
        if (primeraVez) {
            perfil.registrarLogro("Primera victoria contra " + enemigo);
        }
        if (veces == 5) {
            perfil.registrarLogro("Experto en " + enemigo);
        }
        if (enemigo.equals("Dragon")) {
            perfil.registrarLogro("Cazador de Dragones");
        }
        sumarEstadistica("Victorias", 1);

        // el botin va de ultimas: si la mochila esta llena la victoria igual queda guardada
        Item botin = generarBotin(enemigo);
        System.out.println("El " + enemigo + " solto: " + botin);
        p.guardarEnMochila(botin);
    }

    private Item generarBotin(String enemigo) {
        double suerte = random.nextDouble();
        RaridadItem rareza;
        if (suerte < 0.6) {
            rareza = RaridadItem.COMUN;
        } else if (suerte < 0.9) {
            rareza = RaridadItem.RARO;
        } else {
            rareza = RaridadItem.LEGENDARIO;
        }
        int poder = 5 + random.nextInt(8);
        return new Item("Trofeo de " + enemigo, poder, rareza);
    }

    public void ejecutarAtaqueEspecial(Personaje p, int costoMana) throws SinManaException {
        if (p.getEstado() == EstadoPersonaje.DERROTADO) {
            System.out.println(p.getNombre() + " esta derrotado, no puede pelear.");
            return;
        }

        if (p.getEstado() == EstadoPersonaje.ENVENENADO) {
            p.sufrirVeneno(5);
            if (p.getEstado() == EstadoPersonaje.DERROTADO) {
                return;
            }
            if (random.nextDouble() < 0.4) {
                p.setEstado(EstadoPersonaje.VIVO);
                System.out.println("   Por suerte el veneno ya se le paso.");
            }
        }

        if (p.getEstado() == EstadoPersonaje.ATURDIDO) {
            System.out.println(p.getNombre() + " esta aturdido y pierde el turno.");
            p.setEstado(EstadoPersonaje.VIVO);
            sumarEstadistica("Turnos perdidos", 1);
            return;
        }

        // si no le alcanza el mana, aqui se corta y el error sube hasta el Main
        p.lanzarAtaqueEspecial(costoMana);

        int dano = p.calcularAtaque() + costoMana / 2;
        System.out.println(p.getNombre() + " lanza su ataque especial y hace " + dano + " de dano!");
        sumarEstadistica("Ataques especiales", 1);
        sumarEstadistica("Dano total causado", dano);
        sumarEstadistica("Mana gastado", costoMana);

        if (estadisticas.get("Mana gastado") >= 100) {
            perfil.registrarLogro("Maestro del Mana");
        }

        respuestaDelEnemigo(p);
    }

    private void respuestaDelEnemigo(Personaje p) {
        System.out.println("El enemigo contraataca...");
        p.defender(6 + random.nextInt(10));
        if (p.getEstado() == EstadoPersonaje.DERROTADO) {
            return;
        }

        double efecto = random.nextDouble();
        if (efecto < 0.2) {
            p.setEstado(EstadoPersonaje.ATURDIDO);
            System.out.println("   El golpe dejo aturdido a " + p.getNombre() + ".");
        } else if (efecto < 0.45 && p.getEstado() == EstadoPersonaje.VIVO) {
            p.setEstado(EstadoPersonaje.ENVENENADO);
            System.out.println("   " + p.getNombre() + " quedo envenenado.");
        }
    }

    private void sumarEstadistica(String nombre, double valor) {
        if (estadisticas.containsKey(nombre)) {
            estadisticas.put(nombre, estadisticas.get(nombre) + valor);
        } else {
            estadisticas.put(nombre, valor);
        }
    }

    public void mostrarEstadisticas() {
        System.out.println("\n--- ESTADISTICAS DE COMBATE ---");
        if (estadisticas.isEmpty()) {
            System.out.println("Aun no hay estadisticas.");
            return;
        }
        for (Map.Entry<String, Double> entry : estadisticas.entrySet()) {
            System.out.printf("%-22s %8.0f%n", entry.getKey(), entry.getValue());
        }
    }
}
