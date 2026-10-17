package com.taller.poo;

public class Estudiante {

    // los atributos no se inicializan aqui, eso lo hacen los constructores
    private String identificacion;
    private String nombreCompleto;
    private double promedioCalificaciones;
    private boolean estaMatriculado;

    public Estudiante() {
        this.identificacion = "0000";
        this.nombreCompleto = "Sin Nombre";
        this.promedioCalificaciones = 0.0;
        this.estaMatriculado = false;
    }

    public Estudiante(String identificacion, String nombreCompleto,
                      double promedioCalificaciones, boolean estaMatriculado) {
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.promedioCalificaciones = promedioCalificaciones;
        this.estaMatriculado = estaMatriculado;
    }

    // arma el reporte y lo devuelve, no lo imprime (eso se hace en Principal)
    public String obtenerReporteAcademicos() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ID: %s | ", identificacion));
        sb.append(String.format("Nombre: %s | ", nombreCompleto));
        sb.append(String.format("Promedio: %.2f | ", promedioCalificaciones));
        sb.append(String.format("Matriculado: %b", estaMatriculado));
        return sb.toString();
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public double getPromedioCalificaciones() {
        return promedioCalificaciones;
    }

    public boolean isEstaMatriculado() {
        return estaMatriculado;
    }
}