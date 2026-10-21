package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Guerrero;
import co.edu.puj.rpg.modelo.Mago;
import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;
import co.edu.puj.rpg.modelo.inventario.Inventario;
import co.edu.puj.rpg.modelo.inventario.Pocion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int FILA_COFRE = 2;
    private static final int COLUMNA_COFRE = 2;

    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {

        char[][] tablero = new char[5][5];

        List<Personaje> heroes = new ArrayList<>();
        heroes.add(new Guerrero("Thorin", 120, 15, 0, 0, 10));
        heroes.add(new Mago("Gandalf", 80, 25, 0, 1, 50));
        Personaje heroePrincipal = heroes.get(0);

        List<Personaje> enemigos = new ArrayList<>();
        enemigos.add(new Enemigo("Orco", 60, 12, 4, 0));
        enemigos.add(new Enemigo("Goblin", 40, 8, 0, 4));
        enemigos.add(new Enemigo("Troll", 90, 14, 4, 4));

        Inventario<Pocion> inventario = new Inventario<>();
        inventario.agregar(new Pocion("Pocion menor", 30));
        inventario.agregar(new Pocion("Pocion mayor", 60));

        boolean cofreAlcanzado = false;
        int turno = 1;

        System.out.println("========== ARENA RPG ==========");
        System.out.println("Gana eliminando a todos los enemigos o llevando a " + heroePrincipal.getNombre() + " al cofre [C].");

        do {
            System.out.println("\n========== TURNO " + turno + " ==========");

            actualizarTablero(tablero, heroes, enemigos);
            mostrarTablero(tablero);
            mostrarEstadisticas(heroes, enemigos);

            for (Personaje heroe : heroes) {
                if (!heroe.estaVivo() || enemigos.isEmpty() || cofreAlcanzado) {
                    continue;
                }

                boolean turnoUsado = false;
                while (!turnoUsado) {
                    System.out.println("\n--- Turno de " + heroe.getNombre() + " ---");
                    System.out.println("1. Atacar");
                    System.out.println("2. Usar inventario");
                    System.out.println("3. Mover en tablero");
                    int opcion = leerNumero("Elige una opcion: ");

                    switch (opcion) {
                        case 1:
                            Personaje objetivo = elegirEnemigo(enemigos);
                            heroe.realizarAtaqueEspecial(objetivo);
                            turnoUsado = true;
                            break;
                        case 2:
                            turnoUsado = usarInventario(heroe, inventario);
                            break;
                        case 3:
                            turnoUsado = mover(heroe, tablero);
                            if (turnoUsado) {
                                actualizarTablero(tablero, heroes, enemigos);
                                mostrarTablero(tablero);
                            }
                            if (heroe == heroePrincipal
                                    && heroe.getPosicionY() == FILA_COFRE
                                    && heroe.getPosicionX() == COLUMNA_COFRE) {
                                System.out.println(heroe.getNombre() + " llego al cofre!");
                                cofreAlcanzado = true;
                            }
                            break;
                        default:
                            System.out.println("Esa opcion no existe, intenta de nuevo.");
                    }
                }

                // Los enemigos que caen se retiran de una vez para que no vuelvan a atacar
                enemigos.removeIf(e -> !e.estaVivo());
            }

            if (!enemigos.isEmpty() && !cofreAlcanzado) {
                System.out.println("\n--- Turno de los enemigos ---");
                for (Personaje enemigo : enemigos) {
                    List<Personaje> vivos = new ArrayList<>();
                    for (Personaje h : heroes) {
                        if (h.estaVivo()) {
                            vivos.add(h);
                        }
                    }
                    if (vivos.isEmpty()) {
                        break;
                    }
                    Personaje victima = vivos.get(random.nextInt(vivos.size()));
                    enemigo.realizarAtaqueEspecial(victima);
                }
            }

            heroes.removeIf(h -> !h.estaVivo());
            enemigos.removeIf(e -> !e.estaVivo());
            turno++;

        } while (!heroes.isEmpty() && vidaTotal(heroes) > 0 && !enemigos.isEmpty() && !cofreAlcanzado);

        actualizarTablero(tablero, heroes, enemigos);
        mostrarTablero(tablero);

        System.out.println("\n===============================");
        if (heroes.isEmpty() || vidaTotal(heroes) <= 0) {
            System.out.println("DERROTA: todo el escuadron cayo en batalla.");
        } else if (enemigos.isEmpty()) {
            System.out.println("VICTORIA: no queda ningun enemigo en pie.");
        } else {
            System.out.println("VICTORIA: " + heroePrincipal.getNombre() + " encontro el tesoro.");
        }
        System.out.println("===============================");
        sc.close();
    }

    public static void mostrarTablero(char[][] matriz) {
        System.out.println();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }
        System.out.println("G = Guerrero, M = Mago, E = Enemigo, C = Cofre, . = Vacio");
    }

    public static void actualizarTablero(char[][] matriz, List<Personaje> heroes, List<Personaje> enemigos) {
        // Se limpia todo el mapa y se vuelve a dibujar a cada uno en su lugar actual
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = '.';
            }
        }
        matriz[FILA_COFRE][COLUMNA_COFRE] = 'C';
        for (Personaje e : enemigos) {
            matriz[e.getPosicionY()][e.getPosicionX()] = e.getSimbolo();
        }
        for (Personaje h : heroes) {
            matriz[h.getPosicionY()][h.getPosicionX()] = h.getSimbolo();
        }
    }

    public static void mostrarEstadisticas(List<Personaje> heroes, List<Personaje> enemigos) {
        System.out.println("\nHEROES:");
        for (Personaje h : heroes) {
            System.out.println("  " + h);
        }
        System.out.println("ENEMIGOS:");
        for (int i = 0; i < enemigos.size(); i++) {
            System.out.println("  " + i + ". " + enemigos.get(i));
        }
    }

    public static int vidaTotal(List<Personaje> lista) {
        int total = 0;
        for (Personaje p : lista) {
            total += p.getVida();
        }
        return total;
    }

    public static Personaje elegirEnemigo(List<Personaje> enemigos) {
        for (int i = 0; i < enemigos.size(); i++) {
            System.out.println("  " + i + ". " + enemigos.get(i));
        }
        int indice = leerNumero("A cual enemigo atacas?: ");
        while (indice < 0 || indice >= enemigos.size()) {
            System.out.println("Ese numero no esta en la lista.");
            indice = leerNumero("A cual enemigo atacas?: ");
        }
        return enemigos.get(indice);
    }

    public static boolean usarInventario(Personaje heroe, Inventario<Pocion> inventario) {
        if (inventario.getItems().isEmpty()) {
            System.out.println("Ya no quedan pociones.");
            return false;
        }
        for (int i = 0; i < inventario.getItems().size(); i++) {
            System.out.println("  " + i + ". " + inventario.obtener(i));
        }
        int indice = leerNumero("Cual pocion usas?: ");
        if (indice < 0 || indice >= inventario.getItems().size()) {
            System.out.println("Esa pocion no existe.");
            return false;
        }
        Pocion pocion = inventario.obtener(indice);
        inventario.getItems().remove(indice);
        heroe.curar(pocion.getCuracion());
        return true;
    }

    public static boolean mover(Personaje heroe, char[][] matriz) {
        System.out.print("Hacia donde? (W = arriba, S = abajo, A = izquierda, D = derecha): ");
        String direccion = sc.next().toUpperCase();

        int nuevaX = heroe.getPosicionX();
        int nuevaY = heroe.getPosicionY();

        switch (direccion) {
            case "W": nuevaY--; break;
            case "S": nuevaY++; break;
            case "A": nuevaX--; break;
            case "D": nuevaX++; break;
            default:
                System.out.println("Esa direccion no es valida.");
                return false;
        }

        // Antes de moverse se revisa que no se salga del mapa ni pise a otro personaje
        if (nuevaY < 0 || nuevaY >= matriz.length || nuevaX < 0 || nuevaX >= matriz[0].length) {
            System.out.println("No puedes salirte del tablero.");
            return false;
        }
        if (matriz[nuevaY][nuevaX] != '.' && matriz[nuevaY][nuevaX] != 'C') {
            System.out.println("Esa casilla ya esta ocupada.");
            return false;
        }

        heroe.setPosicion(nuevaX, nuevaY);
        System.out.println(heroe.getNombre() + " se mueve a (" + nuevaX + "," + nuevaY + ")");
        return true;
    }

    public static int leerNumero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Escribe solo un numero: ");
        }
        return sc.nextInt();
    }
}
