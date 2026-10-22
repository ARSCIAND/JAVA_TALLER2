package co.edu.puj.rpg.modelo;

public class CorazonRPG {

    private int saludBase;
    private int manaBase;
    private int saludActual;
    private int manaActual;

    public CorazonRPG(int saludBase, int manaBase) {
        this.saludBase = saludBase;
        this.manaBase = manaBase;
        this.saludActual = saludBase;
        this.manaActual = manaBase;
    }

    public void recibirDano(int cantidad) {
        saludActual -= cantidad;
        if (saludActual < 0) {
            saludActual = 0;
        }
    }

    public void curar(int cantidad) {
        saludActual += cantidad;
        if (saludActual > saludBase) {
            saludActual = saludBase;
        }
    }

    public boolean gastarMana(int cantidad) {
        if (manaActual < cantidad) {
            return false;
        }
        manaActual -= cantidad;
        return true;
    }

    public boolean estaVivo() {
        return saludActual > 0;
    }

    public int getSaludBase() {
        return saludBase;
    }

    public int getManaBase() {
        return manaBase;
    }

    public int getSaludActual() {
        return saludActual;
    }

    public int getManaActual() {
        return manaActual;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Salud " + saludActual + "/" + saludBase);
        sb.append(" | Mana " + manaActual + "/" + manaBase);
        return sb.toString();
    }
}
