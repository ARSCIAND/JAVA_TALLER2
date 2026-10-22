package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Guerrero;
import co.edu.puj.rpg.modelo.Mago;
import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.equipamiento.Arma;
import co.edu.puj.rpg.modelo.grupos.Escuadron;
import co.edu.puj.rpg.modelo.grupos.Gremio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplicacionArenaRPG {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        List<Personaje> personajes = new ArrayList<>();
        List<Gremio> gremios = new ArrayList<>();
        List<Escuadron> escuadrones = new ArrayList<>();

        // dos de prueba para no tener que crear todo cada vez que corro el programa
        personajes.add(new Guerrero("Thorin", 3, 120, 0, 0, 10));
        personajes.add(new Mago("Gandalf", 5, 80, 50, 0, 1));

        do {
            System.out.println("\n=== MENU ARENA DE HEROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Crear Gremio");
            System.out.println("3. Reclutar Personaje a Escuadron");
            System.out.println("4. Equipar Arma a Personaje");
            System.out.println("5. Mostrar Estado Detallado (Personaje y Corazon RPG)");
            System.out.println("6. Salir");
            opcion = leerNumero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    crearPersonaje(personajes);
                    break;
                case 2:
                    crearGremio(gremios, personajes);
                    break;
                case 3:
                    reclutar(escuadrones, personajes);
                    break;
                case 4:
                    equiparArma(personajes);
                    break;
                case 5:
                    mostrarEstado(personajes, gremios, escuadrones);
                    break;
                case 6:
                    System.out.println("Cerrando la Arena de Heroes...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    public static void crearPersonaje(List<Personaje> personajes) {
        String nombre = leerTexto("Nombre del personaje: ");
        System.out.println("1. Guerrero");
        System.out.println("2. Mago");
        int tipo = leerNumero("Que tipo de personaje es?: ");
        int nivel = leerNumero("Nivel: ");
        if (nivel < 1) {
            nivel = 1;
        }

        Personaje nuevo;
        if (tipo == 2) {
            nuevo = new Mago(nombre, nivel, 80, 50, 0, 0);
        } else {
            nuevo = new Guerrero(nombre, nivel, 120, 0, 0, 10);
        }
        personajes.add(nuevo);
        System.out.println(nuevo.getTipo() + " " + nombre + " creado.");
    }

    public static void crearGremio(List<Gremio> gremios, List<Personaje> personajes) {
        String nombre = leerTexto("Nombre del gremio: ");
        Gremio gremio = new Gremio(nombre);
        gremios.add(gremio);
        System.out.println("Gremio " + nombre + " creado.");

        if (personajes.isEmpty()) {
            return;
        }
        String respuesta = leerTexto("Quieres inscribir personajes ahora? (s/n): ");
        while (respuesta.equalsIgnoreCase("s")) {
            Personaje p = elegirPersonaje(personajes);
            if (p != null) {
                p.unirseAGremio(gremio);
            }
            respuesta = leerTexto("Inscribir a otro? (s/n): ");
        }
    }

    public static void reclutar(List<Escuadron> escuadrones, List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("Primero crea algun personaje.");
            return;
        }
        Personaje p = elegirPersonaje(personajes);
        if (p == null) {
            return;
        }

        System.out.println("0. Crear un escuadron nuevo");
        for (int i = 0; i < escuadrones.size(); i++) {
            System.out.println((i + 1) + ". " + escuadrones.get(i).getNombre());
        }
        int eleccion = leerNumero("A que escuadron va?: ");

        Escuadron escuadron;
        if (eleccion == 0) {
            escuadron = new Escuadron(leerTexto("Nombre del escuadron: "));
            escuadrones.add(escuadron);
        } else if (eleccion >= 1 && eleccion <= escuadrones.size()) {
            escuadron = escuadrones.get(eleccion - 1);
        } else {
            System.out.println("Ese escuadron no existe.");
            return;
        }
        escuadron.reclutarPersonaje(p);
    }

    public static void equiparArma(List<Personaje> personajes) {
        Personaje p = elegirPersonaje(personajes);
        if (p == null) {
            return;
        }
        if (p.getArmaEquipada() != null) {
            System.out.println(p.getNombre() + " ya tiene " + p.getArmaEquipada() + ", se va a cambiar.");
            p.desequiparArma();
        }
        String nombreArma = leerTexto("Nombre del arma: ");
        int danio = leerNumero("Dano base del arma: ");
        p.equiparArma(new Arma(nombreArma, danio));
    }

    public static void mostrarEstado(List<Personaje> personajes, List<Gremio> gremios, List<Escuadron> escuadrones) {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes todavia.");
            return;
        }

        System.out.println("\n--------------------------------------- PERSONAJES ---------------------------------------");
        System.out.printf("%-16s %-9s %5s %9s %7s %-18s %-14s %6s%n",
                "Nombre", "Tipo", "Nivel", "Salud", "Mana", "Arma", "Gremio", "Ataque");
        for (Personaje p : personajes) {
            String arma = "Desarmado";
            if (p.getArmaEquipada() != null) {
                arma = p.getArmaEquipada().toString();
            }
            String gremio = "-";
            if (p.getGremio() != null) {
                gremio = p.getGremio().getNombre();
            }
            System.out.printf("%-16s %-9s %5d %9d %7d %-18s %-14s %6d%n",
                    p.getNombre(), p.getTipo(), p.getNivel(), p.getVida(), p.getMana(),
                    arma, gremio, p.calcularAtaqueTotal());
        }

        System.out.println("\n--- Detalle de cada personaje ---");
        for (Personaje p : personajes) {
            System.out.println(p);
        }

        StringBuilder grupos = new StringBuilder();
        grupos.append("\n--- Gremios ---\n");
        if (gremios.isEmpty()) {
            grupos.append("No hay gremios creados.\n");
        }
        for (Gremio g : gremios) {
            grupos.append(g).append("\n");
        }
        grupos.append("\n--- Escuadrones ---\n");
        if (escuadrones.isEmpty()) {
            grupos.append("No hay escuadrones creados.\n");
        }
        for (Escuadron e : escuadrones) {
            grupos.append(e).append("\n");
        }
        System.out.print(grupos);
    }

    public static Personaje elegirPersonaje(List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes creados.");
            return null;
        }
        for (int i = 0; i < personajes.size(); i++) {
            Personaje p = personajes.get(i);
            System.out.println("  " + i + ". " + p.getNombre() + " (" + p.getTipo() + ")");
        }
        int indice = leerNumero("Elige un personaje: ");
        if (indice < 0 || indice >= personajes.size()) {
            System.out.println("Ese numero no esta en la lista.");
            return null;
        }
        return personajes.get(indice);
    }

    public static int leerNumero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Escribe solo un numero: ");
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // limpia el enter que deja nextInt, si no el siguiente nextLine sale vacio
        return numero;
    }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String texto = scanner.nextLine().trim();
        while (texto.isEmpty()) {
            System.out.print("No puede quedar vacio, escribe algo: ");
            texto = scanner.nextLine().trim();
        }
        return texto;
    }
}
