package co.edu.puj.rpg.model;

public class CorazonRPG {

    private int vidaMaxima;
    private int manaMaximo;
    private int vidaActual;
    private int manaActual;

    public CorazonRPG(int vidaMaxima, int manaMaximo) {
        this.vidaMaxima = vidaMaxima;
        this.manaMaximo = manaMaximo;
        this.vidaActual = vidaMaxima;
        this.manaActual = manaMaximo;
    }

    public void recibirDano(int cantidad) {
        vidaActual -= cantidad;
        if (vidaActual < 0) {
            vidaActual = 0;
        }
    }

    public void gastarMana(int cantidad) {
        manaActual -= cantidad;
    }

    public boolean estaVivo() {
        return vidaActual > 0;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public int getManaActual() {
        return manaActual;
    }

    @Override
    public String toString() {
        return "Vida " + vidaActual + "/" + vidaMaxima + " | Mana " + manaActual + "/" + manaMaximo;
    }
}
