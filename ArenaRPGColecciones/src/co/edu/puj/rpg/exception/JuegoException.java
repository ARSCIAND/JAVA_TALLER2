package co.edu.puj.rpg.exception;

public abstract class JuegoException extends Exception {

    public JuegoException(String mensaje) {
        super(mensaje);
    }

    // por si toca envolver otro error sin perder de donde vino
    public JuegoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
