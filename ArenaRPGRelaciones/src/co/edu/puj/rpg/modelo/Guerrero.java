package co.edu.puj.rpg.modelo;

public class Guerrero extends Personaje {

    protected int fuerzaEscudo;

    public Guerrero(String nombre, int nivel, int saludBase, int posX, int posY, int fuerzaEscudo) {
        super(nombre, nivel, saludBase, 0, posX, posY);
        this.fuerzaEscudo = fuerzaEscudo;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        atacar(objetivo, true);
    }

    @Override
    public void defender(int danoRecibido) {
        // el escudo absorbe la mitad de su fuerza
        int dano = danoRecibido - fuerzaEscudo / 2;
        if (dano < 0) {
            dano = 0;
        }
        System.out.println("   " + nombre + " se cubre con el escudo (" + danoRecibido + " -> " + dano + ")");
        recibirDano(dano);
    }

    public void atacar(Personaje p) {
        System.out.println(nombre + " ataca a " + p.getNombre());
        p.defender(calcularAtaqueTotal());
    }

    public void atacar(Personaje p, boolean modoFuria) {
        if (modoFuria) {
            System.out.println(nombre + " entra en MODO FURIA contra " + p.getNombre() + "!");
            p.defender(calcularAtaqueTotal() * 2);
        } else {
            atacar(p);
        }
    }

    @Override
    public char getSimbolo() {
        return 'G';
    }

    @Override
    public String getTipo() {
        return "Guerrero";
    }

    @Override
    public String toString() {
        return super.toString() + "\n   Escudo: " + fuerzaEscudo;
    }
}
