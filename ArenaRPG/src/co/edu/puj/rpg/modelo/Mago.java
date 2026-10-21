package co.edu.puj.rpg.modelo;

public class Mago extends Personaje {

    protected int puntosMana;

    public Mago(String nombre, int vida, int ataqueBase, int posX, int posY, int puntosMana) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.puntosMana = puntosMana;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        // Cada hechizo gasta 20 de mana; sin mana, el mago solo puede dar un golpe flojo
        if (puntosMana >= 20) {
            puntosMana -= 20;
            System.out.println(nombre + " lanza BOLA DE FUEGO contra " + objetivo.getNombre() + "! (Mana: " + puntosMana + ")");
            objetivo.defender(ataqueBase * 2);
        } else {
            System.out.println(nombre + " se quedo sin mana y golpea con el baston.");
            objetivo.defender(ataqueBase / 2);
        }
    }

    @Override
    public char getSimbolo() {
        return 'M';
    }

    @Override
    public String toString() {
        return super.toString() + " | Mana: " + puntosMana;
    }
}
