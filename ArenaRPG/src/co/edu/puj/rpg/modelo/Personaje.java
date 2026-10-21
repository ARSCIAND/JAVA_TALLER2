package co.edu.puj.rpg.modelo;

public abstract class Personaje implements ICombatiente {

    protected String nombre;
    protected int vida;
    protected int ataqueBase;
    protected int posicionX;
    protected int posicionY;

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataqueBase = ataqueBase;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    public abstract char getSimbolo();

    @Override
    public void defender(int danoRecibido) {
        recibirDano(danoRecibido);
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void recibirDano(int cantidad) {
        vida -= cantidad;
        // Si el golpe es muy fuerte, la vida se queda en cero y no en negativo
        if (vida < 0) {
            vida = 0;
        }
        System.out.println("   " + nombre + " recibe " + cantidad + " de dano. Vida: " + vida);
    }

    public void curar(int cantidad) {
        vida += cantidad;
        System.out.println("   " + nombre + " recupera " + cantidad + " de vida. Vida: " + vida);
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicion(int posicionX, int posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    @Override
    public String toString() {
        return nombre + " [" + getSimbolo() + "] Vida: " + vida + " | Posicion: (" + posicionX + "," + posicionY + ")";
    }
}
