package co.edu.puj.rpg;

import co.edu.puj.rpg.engine.Bestiario;
import co.edu.puj.rpg.engine.CombateEngine;
import co.edu.puj.rpg.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.exception.SinManaException;
import co.edu.puj.rpg.exception.VidaInsuficienteException;
import co.edu.puj.rpg.model.PerfilJugador;
import co.edu.puj.rpg.model.Personaje;
import co.edu.puj.rpg.model.enums.TipoPersonaje;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== ARENA DE HEROES RPG =====");
        PerfilJugador perfil = new PerfilJugador(leerTexto("Como te llamas, jugador?: "));
        Bestiario bestiario = new Bestiario();
        CombateEngine motor = new CombateEngine(bestiario, perfil);
        List<Personaje> personajes = new ArrayList<>();

        int opcion;
        do {
            System.out.println("\n=== MENU ARENA RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Asignar Habilidad Unica");
            System.out.println("3. Registrar Victoria en Bestiario");
            System.out.println("4. Ejecutar Ataque Especial");
            System.out.println("5. Consultar Bestiario y Logros");
            System.out.println("6. Salir");
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    crearPersonaje(personajes);
                    break;
                case 2:
                    asignarHabilidad(personajes);
                    break;
                case 3:
                    registrarVictoria(personajes, motor);
                    break;
                case 4:
                    ataqueEspecial(personajes, motor);
                    break;
                case 5:
                    consultar(personajes, bestiario, perfil, motor);
                    break;
                case 6:
                    System.out.println("Hasta la proxima, " + perfil.getNombreJugador() + ".");
                    break;
                default:
                    System.out.println("Esa opcion no existe.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    private static void crearPersonaje(List<Personaje> personajes) {
        String nombre = leerTexto("Nombre del personaje: ");

        TipoPersonaje[] tipos = TipoPersonaje.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }
        int eleccion = leerEntero("Que tipo es?: ");
        while (eleccion < 1 || eleccion > tipos.length) {
            System.out.println("Elige un numero de la lista.");
            eleccion = leerEntero("Que tipo es?: ");
        }

        Personaje nuevo = new Personaje(nombre, tipos[eleccion - 1]);
        personajes.add(nuevo);
        System.out.println("Personaje creado:");
        System.out.println(nuevo);
    }

    private static void asignarHabilidad(List<Personaje> personajes) {
        Personaje p = elegirPersonaje(personajes);
        if (p == null) {
            return;
        }
        String habilidad = leerTexto("Nombre de la habilidad: ");
        try {
            boolean laAprendio = p.aprenderHabilidad(habilidad);
            if (laAprendio) {
                System.out.println(p.getNombre() + " aprendio " + habilidad + ".");
            } else {
                System.out.println("Ojo: " + p.getNombre() + " ya sabe " + habilidad + ", no se agrega otra vez.");
            }
        } catch (CapacidadExcedidaException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void registrarVictoria(List<Personaje> personajes, CombateEngine motor) {
        Personaje p = elegirPersonaje(personajes);
        if (p == null) {
            return;
        }
        String enemigo = arreglarNombre(leerTexto("Que enemigo derroto?: "));
        try {
            motor.registrarVictoria(p, enemigo);
        } catch (CapacidadExcedidaException e) {
            System.out.println(e.getMessage() + " El objeto se queda tirado en la arena.");
        }
    }

    private static void ataqueEspecial(List<Personaje> personajes, CombateEngine motor) {
        Personaje p = elegirPersonaje(personajes);
        if (p == null) {
            return;
        }
        int costo = leerEntero("Cuanto mana va a usar en el ataque?: ");
        if (costo <= 0) {
            System.out.println("El costo tiene que ser mayor que cero.");
            return;
        }
        try {
            motor.ejecutarAtaqueEspecial(p, costo);
        } catch (SinManaException e) {
            System.out.println("No se pudo: " + e.getMessage());
        } catch (VidaInsuficienteException e) {
            System.out.println("No se pudo: " + e.getMessage());
        }
    }

    private static void consultar(List<Personaje> personajes, Bestiario bestiario, PerfilJugador perfil, CombateEngine motor) {
        bestiario.mostrar();

        System.out.println("\n--- LOGROS DE " + perfil.getNombreJugador().toUpperCase() + " ---");
        if (perfil.listarLogrosOrdenados().isEmpty()) {
            System.out.println("Ningun logro todavia.");
        }
        for (String logro : perfil.listarLogrosOrdenados()) {
            System.out.println("- " + logro);
        }

        motor.mostrarEstadisticas();

        System.out.println("\n--- PERSONAJES ---");
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes.");
        }
        for (Personaje p : personajes) {
            System.out.println(p);
        }
    }

    private static Personaje elegirPersonaje(List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("Primero crea un personaje (opcion 1).");
            return null;
        }
        for (int i = 0; i < personajes.size(); i++) {
            Personaje p = personajes.get(i);
            System.out.println("  " + (i + 1) + ". " + p.getNombre() + " (" + p.getTipoPersonaje() + ", " + p.getEstado() + ")");
        }
        int indice = leerEntero("Elige el personaje: ");
        if (indice < 1 || indice > personajes.size()) {
            System.out.println("Ese numero no esta en la lista.");
            return null;
        }
        return personajes.get(indice - 1);
    }

    // "orco", "ORCO" y "Orco" tienen que contar como el mismo enemigo
    private static String arreglarNombre(String texto) {
        String limpio = texto.trim().toLowerCase();
        return limpio.substring(0, 1).toUpperCase() + limpio.substring(1);
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Eso no es un numero, intenta otra vez.");
            } finally {
                // se limpia la linea siempre, haya salido bien o mal, para que no quede basura
                scanner.nextLine();
            }
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String texto = scanner.nextLine().trim();
        while (texto.isEmpty()) {
            System.out.print("No puede quedar vacio: ");
            texto = scanner.nextLine().trim();
        }
        return texto;
    }
}
