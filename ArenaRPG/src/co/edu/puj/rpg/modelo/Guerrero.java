package co.edu.puj.rpg.modelo;

public class Guerrero extends Personaje {

    protected int fuerzaEscudo;

    public Guerrero(String nombre, int vida, int ataqueBase, int posX, int posY, int fuerzaEscudo) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.fuerzaEscudo = fuerzaEscudo;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        // El ataque especial del guerrero es pelear en modo furia
        atacar(objetivo, true);
    }

    @Override
    public void defender(int danoRecibido) {
        // El escudo le quita una parte al golpe antes de que llegue
        int danoFinal = Math.max(0, danoRecibido - fuerzaEscudo / 2);
        System.out.println("   " + nombre + " se cubre con el escudo (" + danoRecibido + " -> " + danoFinal + ")");
        recibirDano(danoFinal);
    }

    public void atacar(Personaje p) {
        System.out.println(nombre + " ataca a " + p.getNombre());
        p.defender(ataqueBase);
    }

    public void atacar(Personaje p, boolean modoFuria) {
        if (modoFuria) {
            System.out.println(nombre + " entra en MODO FURIA contra " + p.getNombre() + "!");
            p.defender(ataqueBase * 3);
        } else {
            atacar(p);
        }
    }

    @Override
    public char getSimbolo() {
        return 'G';
    }

    @Override
    public String toString() {
        return super.toString() + " | Escudo: " + fuerzaEscudo;
    }
}
