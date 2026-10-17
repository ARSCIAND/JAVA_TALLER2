package com.taller.poo;

import java.util.Locale;
import java.util.Scanner;

public class SistemaGestionCuentas {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        // arreglo de 3 cuentas ya creadas
        CuentaBancaria[] cuentas = new CuentaBancaria[3];
        cuentas[0] = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.00);
        cuentas[1] = new CuentaBancaria("1002", "Ana Gómez");
        cuentas[2] = new CuentaBancaria("1003", "Luis Díaz", 300.00, null);

        int opcion;

        do {
            System.out.printf("%n===== SISTEMA DE GESTIÓN DE CUENTAS =====%n");
            System.out.printf("1. Depositar%n");
            System.out.printf("2. Retirar%n");
            System.out.printf("3. Ver reporte consolidado%n");
            System.out.printf("4. Salir%n");
            System.out.printf("Seleccione una opción: ");
            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1: {
                    System.out.printf("Número de cuenta: ");
                    String numero = scanner.next();
                    CuentaBancaria cuenta = buscarCuenta(cuentas, numero);

                    if (cuenta == null) {
                        System.out.printf("ERROR: la cuenta %s no existe.%n", numero);
                    } else {
                        System.out.printf("Monto a depositar: ");
                        double monto = leerDouble(scanner);
                        if (monto <= 0) {
                            System.out.printf("ERROR: el monto debe ser mayor a cero.%n");
                        } else if (cuenta.depositar(monto)) {
                            System.out.printf("Depósito exitoso. Nuevo saldo: $%.2f%n", cuenta.getSaldo());
                        }
                    }
                    break;
                }
                case 2: {
                    System.out.printf("Número de cuenta: ");
                    String numero = scanner.next();
                    CuentaBancaria cuenta = buscarCuenta(cuentas, numero);

                    if (cuenta == null) {
                        System.out.printf("ERROR: la cuenta %s no existe.%n", numero);
                    } else {
                        System.out.printf("Monto a retirar: ");
                        double monto = leerDouble(scanner);
                        if (monto <= 0) {
                            System.out.printf("ERROR: el monto debe ser mayor a cero.%n");
                        } else if (cuenta.retirar(monto)) {
                            System.out.printf("Retiro exitoso. Nuevo saldo: $%.2f%n", cuenta.getSaldo());
                        } else {
                            System.out.printf("ERROR: fondos insuficientes en la cuenta %s.%n", numero);
                        }
                    }
                    break;
                }
                case 3: {
                    StringBuilder reporte = new StringBuilder();
                    double total = 0.0;

                    for (int i = 0; i < cuentas.length; i++) {
                        CuentaBancaria c = cuentas[i];
                        double credito = 0.0;
                        if (c.getLimiteCredito() != null) {
                            credito = c.getLimiteCredito();
                        }
                        reporte.append(String.format("[%d] Cuenta: %s | Titular: %s | Saldo: $%.2f | Crédito: $%.2f%n",
                                i + 1, c.getNumeroCuenta(), c.getTitular(), c.getSaldo(), credito));
                        total += c.getSaldo();
                    }

                    System.out.printf("%n=== REPORTE CONSOLIDADO ===%n");
                    System.out.printf("%s", reporte.toString());
                    System.out.printf("Total de cuentas: %d%n", cuentas.length);
                    System.out.printf("Saldo total acumulado: $%.2f%n", total);
                    break;
                }
                case 4:
                    System.out.printf("Saliendo del sistema...%n");
                    break;
                default:
                    System.out.printf("ERROR: opción %d no válida.%n", opcion);
            }

        } while (opcion != 4);

        scanner.close();
    }

    // busca la cuenta por numero, si no la encuentra devuelve null
    private static CuentaBancaria buscarCuenta(CuentaBancaria[] cuentas, String numeroCuenta) {
        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].getNumeroCuenta().equals(numeroCuenta)) {
                return cuentas[i];
            }
        }
        return null;
    }

    // para que no se dañe si el usuario escribe letras en vez de numeros
    private static int leerEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.printf("Ingrese un número entero: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double leerDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.printf("Ingrese un valor numérico (use punto decimal): ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
