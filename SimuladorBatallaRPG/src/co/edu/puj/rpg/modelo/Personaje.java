package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enemigo.Enemigo;

public class Personaje {

    private static final int UMBRAL_EXPERIENCIA = 100;
    private static final double PROBABILIDAD_CRITICO = 0.15;

    // atributos del heroe, todos private
    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;

    // para los retos
    private int experiencia;
    private int pociones;
    private int ultimoDano;
    private boolean ultimoGolpeCritico;

    public Personaje(String nombre, int vidaMaxima, int puntosAtaque, double oro) {
        this.nombre = nombre;
        this.vidaMaxima = (vidaMaxima > 0) ? vidaMaxima : 1;
        this.puntosAtaque = Math.max(puntosAtaque, 0);
        this.nivel = 1;
        this.estaVivo = true;
        this.experiencia = 0;
        this.pociones = 2;
        // uso los setters para que pase por las validaciones
        this.setPuntosVida(this.vidaMaxima);
        if (!this.setOro(oro)) {
            this.oro = 0.0;
        }
    }

    // constructor corto con valores por defecto
    public Personaje(String nombre) {
        this(nombre, 100, 15, 50.0);
    }

    public void atacar(Enemigo enemigo) {
        if (enemigo == null || !this.estaVivo || !enemigo.isEstaVivo()) {
            this.ultimoDano = 0;
            this.ultimoGolpeCritico = false;
            return;
        }
        int dano = this.puntosAtaque;
        // 15% de critico, se duplica el daño
        this.ultimoGolpeCritico = Math.random() < PROBABILIDAD_CRITICO;
        if (this.ultimoGolpeCritico) {
            dano = dano * 2;
        }
        this.ultimoDano = dano;
        enemigo.recibirDano(dano);

        if (!enemigo.isEstaVivo()) {
            this.ganarExperiencia(enemigo.getExperienciaOtorgada());
            this.setOro(this.oro + enemigo.getOro());
        }
    }

    public void usarPocion(int cantidadRestaurada) {
        if (!this.estaVivo || this.pociones <= 0 || cantidadRestaurada <= 0) {
            return;
        }
        this.pociones--;
        this.setPuntosVida(this.puntosVida + cantidadRestaurada);
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano <= 0) {
            return;
        }
        this.setPuntosVida(this.puntosVida - cantidadDano);
    }

    // reto 1
    public boolean comprarPocionGrande(double costo) {
        boolean compraAprobada = this.setOro(this.oro - costo);
        if (compraAprobada) {
            this.pociones++;
        }
        return compraAprobada;
    }

    // reto 2: cada 100 de xp sube un nivel
    public void ganarExperiencia(int puntos) {
        if (puntos <= 0) {
            return;
        }
        this.experiencia += puntos;
        while (this.experiencia >= UMBRAL_EXPERIENCIA) {
            this.experiencia -= UMBRAL_EXPERIENCIA;
            this.setNivel(this.nivel + 1);
            this.vidaMaxima += 10;
            this.puntosAtaque += 3;
            this.setPuntosVida(this.vidaMaxima);
        }
    }

    // ---------- setters ----------

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
            System.out.printf("  [!] Transaccion rechazada: saldo insuficiente (tienes %.2f de oro).%n", this.oro);
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

    // ---------- getters ----------

    public String getNombre() { return nombre; }
    public int getPuntosVida() { return puntosVida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getPuntosAtaque() { return puntosAtaque; }
    public int getNivel() { return nivel; }
    public double getOro() { return oro; }
    public boolean isEstaVivo() { return estaVivo; }
    public int getExperiencia() { return experiencia; }
    public int getUmbralExperiencia() { return UMBRAL_EXPERIENCIA; }
    public int getPociones() { return pociones; }
    public int getUltimoDano() { return ultimoDano; }
    public boolean isUltimoGolpeCritico() { return ultimoGolpeCritico; }
}