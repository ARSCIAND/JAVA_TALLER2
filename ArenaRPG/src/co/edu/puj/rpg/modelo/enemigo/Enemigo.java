package co.edu.puj.rpg.modelo.enemigo;

import co.edu.puj.rpg.modelo.Personaje;

public class Enemigo extends Personaje {

    public Enemigo(String nombre, int vida, int ataqueBase, int posX, int posY) {
        super(nombre, vida, ataqueBase, posX, posY);
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        System.out.println(nombre + " lanza un zarpazo contra " + objetivo.getNombre() + "!");
        objetivo.defender(ataqueBase);
    }

    @Override
    public char getSimbolo() {
        return 'E';
    }
}
