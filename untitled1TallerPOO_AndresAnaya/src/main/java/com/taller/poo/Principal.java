package com.taller.poo;

import java.util.Locale;

public class Principal {

    public static void main(String[] args) {
        // para que los decimales salgan con punto y no con coma
        Locale.setDefault(Locale.US);

        // vehiculos
        Vehiculo[] vehiculos = {
                new Vehiculo("Toyota", "Corolla", 15000.50, 180, true),
                new Vehiculo(),
                new Vehiculo("Mazda", "CX-30", 32000.75, 200, false)
        };

        // estudiantes
        Estudiante[] estudiantes = {
                new Estudiante("2021001", "Laura Martínez", 4.35, true),
                new Estudiante("2021002", "Andrés Rojas", 3.80, true),
                new Estudiante("2021003", "Sofía Herrera", 2.95, false)
        };

        // cuentas, una con cada constructor
        CuentaBancaria cuenta1 = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.00);
        CuentaBancaria cuenta2 = new CuentaBancaria("1002", "Ana Gómez");
        CuentaBancaria cuenta3 = new CuentaBancaria();

        // depositos y retiros
        boolean d1 = cuenta1.depositar(500.75);
        boolean r1 = cuenta1.retirar(250.00);
        boolean d2 = cuenta2.depositar(50.00);
        boolean r2 = cuenta2.retirar(500.00);  // no alcanza, debe dar false
        boolean d3 = cuenta3.depositar(-20.00); // monto negativo, debe dar false

        System.out.printf("=== OPERACIONES ===%n");
        System.out.printf("Depósito cuenta 1001: %b%n", d1);
        System.out.printf("Retiro cuenta 1001: %b%n", r1);
        System.out.printf("Depósito cuenta 1002: %b%n", d2);
        System.out.printf("Retiro cuenta 1002: %b%n", r2);
        System.out.printf("Depósito cuenta 000000: %b%n%n", d3);

        System.out.printf("=== REPORTE DE VEHÍCULOS ===%n");
        for (int i = 0; i < vehiculos.length; i++) {
            Vehiculo v = vehiculos[i];
            System.out.printf("[%d] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                    i + 1, v.getMarca(), v.getModelo(), v.getKilometraje(),
                    v.getVelocidadMaxima(), v.isEncendido());
        }

        System.out.printf("%n=== REPORTE DE ESTUDIANTES ===%n");
        for (Estudiante e : estudiantes) {
            System.out.printf("%s%n", e.obtenerReporteAcademicos());
        }

        System.out.printf("%n=== REPORTE DE CUENTAS BANCARIAS ===%n");
        CuentaBancaria[] cuentas = {cuenta1, cuenta2, cuenta3};
        for (CuentaBancaria c : cuentas) {
            System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: $%.2f | Crédito: $%.2f%n",
                    c.getNumeroCuenta(), c.getTitular(), c.getSaldo(), c.getLimiteCredito());
        }

        // metodos static, se llaman con el nombre de la clase
        double euros = ConversorMoneda.convertirDolaresAEuros(100.0, 0.92);
        double invalido = ConversorMoneda.convertirDolaresAEuros(-50.0, 0.92);
        System.out.printf("%n=== CONVERSOR DE MONEDA ===%n");
        System.out.printf("100 USD a EUR: %s%n", ConversorMoneda.formatearMoneda(euros));
        System.out.printf("Conversión inválida: %s%n", ConversorMoneda.formatearMoneda(invalido));
    }
}