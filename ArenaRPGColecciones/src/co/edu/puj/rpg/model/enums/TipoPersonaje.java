package co.edu.puj.rpg.model.enums;

public enum TipoPersonaje {

    // ataque, defensa, vida inicial, mana inicial
    GUERRERO(1.2, 1.5, 130, 30),
    MAGO(1.6, 0.8, 80, 120),
    ARQUERO(1.4, 1.0, 100, 60);

    private final double bonificadorAtaqueBase;
    private final double bonificadorDefensaBase;
    private final int vidaInicial;
    private final int manaInicial;

    private TipoPersonaje(double bonificadorAtaqueBase, double bonificadorDefensaBase, int vidaInicial, int manaInicial) {
        this.bonificadorAtaqueBase = bonificadorAtaqueBase;
        this.bonificadorDefensaBase = bonificadorDefensaBase;
        this.vidaInicial = vidaInicial;
        this.manaInicial = manaInicial;
    }

    public double getBonificadorAtaqueBase() {
        return bonificadorAtaqueBase;
    }

    public double getBonificadorDefensaBase() {
        return bonificadorDefensaBase;
    }

    public int getVidaInicial() {
        return vidaInicial;
    }

    public int getManaInicial() {
        return manaInicial;
    }
}
