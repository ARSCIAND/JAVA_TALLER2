package co.edu.puj.rpg.exception;

// esta no hereda de JuegoException porque la guia la pide no verificada
public class VidaInsuficienteException extends RuntimeException {

    public VidaInsuficienteException(String mensaje) {
        super(mensaje);
    }

    public VidaInsuficienteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
