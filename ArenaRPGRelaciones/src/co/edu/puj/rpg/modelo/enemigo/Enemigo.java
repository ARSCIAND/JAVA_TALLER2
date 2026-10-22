package co.edu.puj.rpg.modelo.enemigo;

import co.edu.puj.rpg.modelo.Personaje;

public class Enemigo extends Personaje {

    public Enemigo(String nombre, int nivel, int saludBase, int posX, int posY) {
        super(nombre, nivel, saludBase, 0, posX, posY);
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        System.out.println(nombre + " lanza un zarpazo contra " + objetivo.getNombre() + "!");
        objetivo.defender(calcularAtaqueTotal());
    }

    @Override
    public char getSimbolo() {
        return 'E';
    }

    @Override
    public String getTipo() {
        return "Enemigo";
    }
}
