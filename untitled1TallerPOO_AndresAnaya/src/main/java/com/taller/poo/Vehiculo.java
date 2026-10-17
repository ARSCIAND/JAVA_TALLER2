package com.taller.poo;

/*
 * Diferencia entre primitivos y Wrappers en memoria:
 * Los primitivos como double, int o boolean guardan el dato directo en el
 * objeto, por eso gastan menos memoria. Ademas nunca pueden quedar en null,
 * si no se les asigna nada Java les pone un valor por defecto (0, 0.0 o false).
 * Los Wrappers como Double, Integer o Boolean son objetos, entonces el atributo
 * solo guarda la direccion donde esta ese objeto y eso ocupa mas memoria.
 * La ventaja es que si pueden ser null, lo cual sirve cuando un dato todavia
 * no se conoce, pero hay que tener cuidado porque si se hace una operacion
 * con un Wrapper en null el programa lanza un NullPointerException.
 */
public class Vehiculo {

    private String marca;

    private String modelo;

    // Use double primitivo porque un vehiculo siempre tiene kilometraje,
    // aunque sea nuevo (0.0), asi que no tiene sentido que sea null.
    private double kilometraje;

    // Use int primitivo porque la velocidad maxima siempre es un numero
    // entero conocido y no necesito que quede vacia.
    private int velocidadMaxima;


    // Use boolean primitivo porque el motor solo puede estar prendido o
    // apagado, no hay una tercera opcion que se tenga que guardar como null.
    private boolean encendido;

    public Vehiculo() {
        this.marca = "Genérico";
        this.modelo = "Sin Modelo";
        this.kilometraje = 0.0;
        this.velocidadMaxima = 120;
        this.encendido = false;
    }

    public Vehiculo(String marca, String modelo, double kilometraje,
                    int velocidadMaxima, boolean encendido) {
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.velocidadMaxima = velocidadMaxima;
        this.encendido = encendido;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public int getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public boolean isEncendido() {
        return encendido;
    }
}
