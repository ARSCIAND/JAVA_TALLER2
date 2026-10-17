package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;

import java.util.Scanner;

public class Main {

    private static final int CURACION_POCION = 40;
    private static final double COSTO_POCION_GRANDE = 30.0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Personaje heroe = new Personaje("Aragorn", 100, 15, 50.0);
        Enemigo enemigo = new Enemigo("Orco de Mordor", 120, 12, 40.0, 120);


        // historial de la pelea
        StringBuilder historial = new StringBuilder();
        int ronda = 1;
        boolean huyo = false;

        System.out.println("===== SIMULADOR DE BATALLA - ARENA DE HEROES =====");
        System.out.printf("%s se enfrenta a %s.%n", heroe.getNombre(), enemigo.getNombre());

        do {
            System.out.printf("%n----- RONDA %d -----%n", ronda);
            System.out.printf("%-15s PV: %3d/%-3d | Nivel: %d | Oro: %.2f | Pociones: %d%n",
                    heroe.getNombre(), heroe.getPuntosVida(), heroe.getVidaMaxima(),
                    heroe.getNivel(), heroe.getOro(), heroe.getPociones());
            System.out.printf("%-15s PV: %3d/%-3d%n",
                    enemigo.getNombre(), enemigo.getPuntosVida(), enemigo.getVidaMaxima());
            System.out.println("1. Atacar\n2. Usar Pocion\n3. Ver Estado\n4. Huir\n5. Tienda");
            System.out.print("Elige una opcion: ");

            int opcion;
            // validacion de entrada con if-else
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
            } else {
                String basura = sc.next();
                System.out.printf("  [!] '%s' no es un numero valido.%n", basura);
                continue;
            }
            if (opcion < 1 || opcion > 5) {
                System.out.printf("  [!] La opcion %d no existe.%n", opcion);
                continue;
            }

            boolean turnoConsumido = false;

            switch (opcion) {
                case 1: // atacar
                    heroe.atacar(enemigo);
                    if (heroe.isUltimoGolpeCritico()) {
                        historial.append(String.format("[R%d] CRITICO! %s inflige %d de dano.%n",
                                ronda, heroe.getNombre(), heroe.getUltimoDano()));
                    } else {
                        historial.append(String.format("[R%d] %s inflige %d de dano.%n",
                                ronda, heroe.getNombre(), heroe.getUltimoDano()));
                    }
                    if (!enemigo.isEstaVivo()) {
                        historial.append(String.format("[R%d] %s derrotado. +%d XP, +%.2f oro.%n",
                                ronda, enemigo.getNombre(), enemigo.getExperienciaOtorgada(), enemigo.getOro()));
                    }
                    turnoConsumido = true;
                    break;
                case 2: // pocion
                    if (heroe.getPociones() > 0) {
                        int antes = heroe.getPuntosVida();
                        heroe.usarPocion(CURACION_POCION);
                        historial.append(String.format("[R%d] %s se cura %d PV.%n",
                                ronda, heroe.getNombre(), heroe.getPuntosVida() - antes));
                        turnoConsumido = true;
                    } else {
                        System.out.println("  [!] No tienes pociones.");
                    }
                    break;
                case 3: // ver estado
                    mostrarEstado(heroe, enemigo);
                    break;
                case 4: // huir
                    huyo = true;
                    break;
                case 5: // tienda
                    abrirTienda(sc, heroe, historial, ronda);
                    break;
                default:
                    System.out.println("  [!] Opcion invalida.");
            }

            if (huyo) {
                break;
            }

            // turno del enemigo
            if (turnoConsumido && enemigo.isEstaVivo() && heroe.isEstaVivo()) {
                int danoEnemigo = enemigo.atacar(heroe);
                historial.append(String.format("[R%d] %s contraataca: %d de dano.%n",
                        ronda, enemigo.getNombre(), danoEnemigo));
            }

            // historial al final de cada ronda
            if (turnoConsumido) {
                System.out.println("\n--- HISTORIAL ---");
                System.out.print(historial);
                ronda++;
            }

        } while (heroe.isEstaVivo() && enemigo.isEstaVivo());

        System.out.println("\n===== FIN DEL COMBATE =====");
        if (huyo) {
            System.out.printf("%s huyo de la arena.%n", heroe.getNombre());
        } else if (heroe.isEstaVivo()) {
            System.out.printf("VICTORIA! %s gano.%n", heroe.getNombre());
        } else {
            System.out.printf("DERROTA. %s cayo.%n", heroe.getNombre());
        }
        mostrarEstado(heroe, enemigo);
        sc.close();
    }

    // solo usa getters
    private static void mostrarEstado(Personaje heroe, Enemigo enemigo) {
        System.out.println("\n--- ESTADO ---");
        System.out.printf("Nombre: %s%n", heroe.getNombre());
        System.out.printf("Vida: %d / %d%n", heroe.getPuntosVida(), heroe.getVidaMaxima());
        System.out.printf("Ataque: %d%n", heroe.getPuntosAtaque());
        System.out.printf("Nivel: %d (XP %d/%d)%n", heroe.getNivel(), heroe.getExperiencia(), heroe.getUmbralExperiencia());
        System.out.printf("Oro: %.2f%n", heroe.getOro());
        System.out.printf("Vivo: %s%n", heroe.isEstaVivo() ? "Si" : "No");
        System.out.printf("Enemigo: %s - Vida: %d / %d%n",
                enemigo.getNombre(), enemigo.getPuntosVida(), enemigo.getVidaMaxima());
    }

    private static void abrirTienda(Scanner sc, Personaje heroe, StringBuilder historial, int ronda) {
        int op;
        do {
            System.out.printf("%n--- TIENDA --- Oro: %.2f%n", heroe.getOro());
            System.out.printf("1. Pocion Grande de Salud (%.2f)%n2. Salir%nElige: ", COSTO_POCION_GRANDE);
            if (sc.hasNextInt()) {
                op = sc.nextInt();
            } else {
                sc.next();
                op = 0;
                continue;
            }
            switch (op) {
                case 1:
                    if (heroe.comprarPocionGrande(COSTO_POCION_GRANDE)) {
                        historial.append(String.format("[R%d] Compra Pocion Grande (-%.2f oro).%n",
                                ronda, COSTO_POCION_GRANDE));
                        System.out.println("  Compra realizada.");
                    } else {
                        System.out.println("  No se entrego la pocion.");
                    }
                    break;
                case 2:
                    break;
                default:
                    System.out.println("  [!] Opcion invalida.");
            }
        } while (op != 2);
    }
}