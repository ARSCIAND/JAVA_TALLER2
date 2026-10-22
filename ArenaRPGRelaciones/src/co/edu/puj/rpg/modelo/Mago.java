package co.edu.puj.rpg.modelo;

public class Mago extends Personaje {

    public Mago(String nombre, int nivel, int saludBase, int manaBase, int posX, int posY) {
        super(nombre, nivel, saludBase, manaBase, posX, posY);
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        boolean pudoLanzar = getCorazon().gastarMana(20);
        if (pudoLanzar) {
            System.out.println(nombre + " lanza BOLA DE FUEGO contra " + objetivo.getNombre() + "! (Mana: " + getMana() + ")");
            objetivo.defender(calcularAtaqueTotal() * 2);
        } else {
            System.out.println(nombre + " se quedo sin mana y golpea con el baston.");
            objetivo.defender(calcularAtaqueTotal() / 2);
        }
    }

    @Override
    public char getSimbolo() {
        return 'M';
    }

    @Override
    public String getTipo() {
        return "Mago";
    }
}
