package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.equipamiento.Arma;
import co.edu.puj.rpg.modelo.grupos.Gremio;

public abstract class Personaje implements ICombatiente {

    protected String nombre;
    protected int nivel;
    protected int posicionX;
    protected int posicionY;

    private Arma armaEquipada;
    private Gremio gremio;
    private final CorazonRPG corazon;

    public Personaje(String nombre, int nivel, int saludBase, int manaBase, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.armaEquipada = null;
        this.gremio = null;
        this.corazon = new CorazonRPG(saludBase, manaBase); // sin setter a proposito
    }

    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    public abstract char getSimbolo();

    public abstract String getTipo();

    @Override
    public void defender(int danoRecibido) {
        recibirDano(danoRecibido);
    }

    public void equiparArma(Arma nuevaArma) {
        if (nuevaArma == null) {
            System.out.println("No hay ningun arma para equipar.");
            return;
        }
        this.armaEquipada = nuevaArma;
        System.out.println(nombre + " ahora lleva " + nuevaArma.getNombre() + ".");
    }

    public void desequiparArma() {
        if (armaEquipada != null) {
            System.out.println(nombre + " guarda su " + armaEquipada.getNombre() + ".");
            armaEquipada = null;
        } else {
            System.out.println(nombre + " no tiene ningun arma para quitarse.");
        }
    }

    public int calcularAtaqueTotal() {
        int ataque = nivel * 5;
        if (armaEquipada != null) {
            ataque += armaEquipada.getDanioBase();
        }
        return ataque;
    }

    public void unirseAGremio(Gremio nuevoGremio) {
        if (nuevoGremio == null || nuevoGremio == this.gremio) {
            return;
        }
        // primero se sale del gremio viejo, si no quedaria en dos listas
        if (this.gremio != null) {
            salirDelGremio();
        }
        this.gremio = nuevoGremio;
        System.out.println(nombre + " se unio al gremio " + nuevoGremio.getNombre() + ".");
        nuevoGremio.agregarMiembro(this);
    }

    public void salirDelGremio() {
        if (this.gremio == null) {
            return;
        }
        Gremio viejo = this.gremio;
        this.gremio = null; // se pone en null antes de avisar, asi el gremio no lo vuelve a llamar
        System.out.println(nombre + " se salio del gremio " + viejo.getNombre() + ".");
        viejo.removerMiembro(this);
    }

    public boolean estaVivo() {
        return corazon.estaVivo();
    }

    public void recibirDano(int cantidad) {
        corazon.recibirDano(cantidad);
        System.out.println("   " + nombre + " recibe " + cantidad + " de dano. Vida: " + getVida());
    }

    public void curar(int cantidad) {
        corazon.curar(cantidad);
        System.out.println("   " + nombre + " se cura. Vida: " + getVida());
    }

    protected CorazonRPG getCorazon() {
        return corazon;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public int getVida() {
        return corazon.getSaludActual();
    }

    public int getMana() {
        return corazon.getManaActual();
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public Gremio getGremio() {
        return gremio;
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
        String textoArma = "Desarmado";
        if (armaEquipada != null) {
            textoArma = armaEquipada.toString();
        }

        // solo el nombre del gremio; con el toString completo se llaman entre los dos y revienta (StackOverflow)
        String textoGremio = "Sin gremio";
        if (gremio != null) {
            textoGremio = gremio.getNombre();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(nombre + " (" + getTipo() + ", nivel " + nivel + ")\n");
        sb.append("   Corazon: " + corazon + "\n");
        sb.append("   Arma: " + textoArma + "\n");
        sb.append("   Gremio: " + textoGremio + "\n");
        sb.append("   Ataque total: " + calcularAtaqueTotal());
        return sb.toString();
    }
}
