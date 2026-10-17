package com.taller.poo;

public class CuentaBancaria {
    private String numeroCuenta;
    private String titular;
    private double saldo;
    private Double limiteCredito;

    /*
     * PREGUNTAS DE ANÁLISIS:
     * 1. ¿Qué diferencia ocurre a nivel de memoria y asignación por defecto al
     *    declarar 'saldo' como double primitivo frente a 'limiteCredito' como
     *    Wrapper Double?
     *
     *    R: saldo al ser double guarda el numero directamente en el objeto y
     *    ocupa 8 bytes, y si no se le asigna nada queda en 0.0 por defecto.
     *    En cambio limiteCredito al ser Double es un objeto, entonces lo que
     *    se guarda es una referencia que apunta a ese objeto en memoria, por
     *    eso gasta mas espacio. Si no se inicializa su valor por defecto es
     *    null, o sea que no apunta a nada.
     *
     * 2. ¿Por qué una operación aritmética sobre un atributo no inicializado de
     *    tipo Double provoca una NullPointerException, mientras que sobre un
     *    double primitivo opera con 0.0?
     *
     *    R: Porque para poder hacer la cuenta Java tiene que sacar el valor del
     *    objeto Double (unboxing), y eso lo hace llamando internamente a
     *    doubleValue(). Si la variable esta en null no hay objeto al cual
     *    llamarle el metodo y por eso sale el NullPointerException. Con el
     *    double primitivo no pasa porque nunca es null, siempre tiene un valor
     *    y si no se inicializo vale 0.0.
     */

    // constructor por defecto
    public CuentaBancaria() {
        this.numeroCuenta = "000000";
        this.titular = "Sin Nombre";
        this.saldo = 0.0;
        this.limiteCredito = 0.0;
    }

    // constructor con numero de cuenta y titular
    public CuentaBancaria(String numeroCuenta, String titular) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0.0;
        this.limiteCredito = 100.0;
    }

    // constructor completo
    public CuentaBancaria(String numeroCuenta, String titular,
                          double saldo, Double limiteCredito) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
    }

    public boolean depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            return true;
        } else {
            return false;
        }
    }

    public boolean retirar(double monto) {
        // si el credito es null lo tomo como 0 para que no se caiga
        double credito = 0.0;
        if (limiteCredito != null) {
            credito = limiteCredito;
        }
        double disponible = saldo + credito;

        if (monto > 0 && monto <= disponible) {
            this.saldo -= monto;
            return true;
        } else {
            return false;
        }
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }
}