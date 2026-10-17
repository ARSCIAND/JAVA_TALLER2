package co.edu.puj.rpg.modelo.enemigo;

import co.edu.puj.rpg.modelo.Personaje;

public class Enemigo {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;
    private int experienciaOtorgada;

    // sin modificador (package-private), desde Main no se ve
    String notaInterna = "solo visible dentro de modelo.enemigo";

    public Enemigo(String nombre, int vidaMaxima, int puntosAtaque, double oro, int experienciaOtorgada) {
        this.nombre = nombre;
        this.vidaMaxima = (vidaMaxima > 0) ? vidaMaxima : 1;
        this.puntosAtaque = Math.max(puntosAtaque, 0);
        this.nivel = 1;
        this.experienciaOtorgada = Math.max(experienciaOtorgada, 0);
        this.estaVivo = true;
        this.setPuntosVida(this.vidaMaxima);
        if (!this.setOro(oro)) {
            this.oro = 0.0;
        }
    }

    public int atacar(Personaje heroe) {
        if (heroe == null || !this.estaVivo || !heroe.isEstaVivo()) {
            return 0;
        }
        heroe.recibirDano(this.puntosAtaque);
        return this.puntosAtaque;
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano <= 0) {
            return;
        }
        this.setPuntosVida(this.puntosVida - cantidadDano);
    }

    public void setPuntosVida(int vida) {
        if (vida <= 0) {
            this.puntosVida = 0;
            this.estaVivo = false;
        } else if (vida > this.vidaMaxima) {
            this.puntosVida = this.vidaMaxima;
            this.estaVivo = true;
        } else {
            this.puntosVida = vida;
            this.estaVivo = true;
        }
    }

    public boolean setOro(double oro) {
        if (oro < 0.0) {
            return false;
        }
        this.oro = oro;
        return true;
    }

    protected void setNivel(int nivel) {
        if (nivel >= 1) {
            this.nivel = nivel;
        }
    }

    public String getNombre() { return nombre; }
    public int getPuntosVida() { return puntosVida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getPuntosAtaque() { return puntosAtaque; }
    public int getNivel() { return nivel; }
    public double getOro() { return oro; }
    public boolean isEstaVivo() { return estaVivo; }
    public int getExperienciaOtorgada() { return experienciaOtorgada; }
}