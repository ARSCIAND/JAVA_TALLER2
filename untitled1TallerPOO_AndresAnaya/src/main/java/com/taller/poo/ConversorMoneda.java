package com.taller.poo;

// clase utilitaria, los metodos son static asi que se usan sin hacer new
public class ConversorMoneda {

    // constructor privado para que nadie pueda crear objetos de esta clase
    private ConversorMoneda() {
    }

    public static double convertirDolaresAEuros(double montoDolares, double tasaCambio) {
        if (montoDolares <= 0 || tasaCambio <= 0) {
            return 0.0;
        }
        return montoDolares * tasaCambio;
    }

    public static String formatearMoneda(double monto) {
        return String.format("$%.2f", monto);
    }
}