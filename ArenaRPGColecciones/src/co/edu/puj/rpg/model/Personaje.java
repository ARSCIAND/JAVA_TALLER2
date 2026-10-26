package co.edu.puj.rpg.model;

import co.edu.puj.rpg.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.exception.SinManaException;
import co.edu.puj.rpg.exception.VidaInsuficienteException;
import co.edu.puj.rpg.model.enums.EstadoPersonaje;
import co.edu.puj.rpg.model.enums.RaridadItem;
import co.edu.puj.rpg.model.enums.TipoPersonaje;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Personaje implements ICombatiente {

    private static final int MAX_HABILIDADES = 5;
    private static final int TAMANO_MOCHILA = 4;

    private String nombre;
    private int nivel;
    private TipoPersonaje tipoPersonaje;
    private EstadoPersonaje estado;

    private final CorazonRPG corazon;
    private Item armaEquipada;
    private Set<String> habilidades;
    private Inventario<Item> mochila;

    public Personaje(String nombre, TipoPersonaje tipoPersonaje) {
        this.nombre = nombre;
        this.nivel = 1;
        this.tipoPersonaje = tipoPersonaje;
        this.estado = EstadoPersonaje.VIVO;
        this.corazon = new CorazonRPG(tipoPersonaje.getVidaInicial(), tipoPersonaje.getManaInicial());
        this.habilidades = new HashSet<>();
        this.mochila = new Inventario<>(TAMANO_MOCHILA);
        this.armaEquipada = armaInicial(tipoPersonaje);
    }

    private Item armaInicial(TipoPersonaje tipo) {
        switch (tipo) {
            case GUERRERO:
                return new Item("Espada oxidada", 8, RaridadItem.COMUN);
            case MAGO:
                return new Item("Baston de roble", 6, RaridadItem.COMUN);
            default:
                return new Item("Arco corto", 7, RaridadItem.COMUN);
        }
    }

    public boolean aprenderHabilidad(String habilidad) throws CapacidadExcedidaException {
        // primero lo de repetida, porque si ya la sabe no tiene sentido decirle que esta lleno
        if (habilidades.contains(habilidad)) {
            return false;
        }
        if (habilidades.size() >= MAX_HABILIDADES) {
            throw new CapacidadExcedidaException(nombre + " ya tiene el libro lleno (" + MAX_HABILIDADES + " habilidades).");
        }
        habilidades.add(habilidad);
        return true;
    }

    public Set<String> obtenerHabilidades() {
        return Collections.unmodifiableSet(habilidades);
    }

    public int calcularAtaque() {
        int ataque = nivel * 5;
        if (armaEquipada != null) {
            ataque += armaEquipada.getPoder();
        }
        return (int) Math.round(ataque * tipoPersonaje.getBonificadorAtaqueBase());
    }

    public void lanzarAtaqueEspecial(int costo) throws SinManaException {
        int manaActual = corazon.getManaActual();
        if (costo > manaActual) {
            throw new SinManaException(nombre + " necesita " + costo + " de mana y solo tiene " + manaActual + ".");
        }

        // los ataques fuertes tambien cansan: cuestan un cuarto del mana en vida
        int costoVida = costo / 4;
        if (costoVida >= corazon.getVidaActual()) {
            throw new VidaInsuficienteException(nombre + " no aguanta ese esfuerzo, le costaria " + costoVida
                    + " de vida y solo tiene " + corazon.getVidaActual() + ".");
        }

        corazon.gastarMana(costo);
        corazon.recibirDano(costoVida);
    }

    @Override
    public void defender(int danoRecibido) {
        int dano = (int) Math.round(danoRecibido / tipoPersonaje.getBonificadorDefensaBase());
        corazon.recibirDano(dano);
        System.out.println("   " + nombre + " recibe " + dano + " de dano. Vida: " + corazon.getVidaActual());
        revisarSiCayo();
    }

    public void sufrirVeneno(int dano) {
        corazon.recibirDano(dano);
        System.out.println("   El veneno le quita " + dano + " de vida a " + nombre + ". Vida: " + corazon.getVidaActual());
        revisarSiCayo();
    }

    private void revisarSiCayo() {
        if (!corazon.estaVivo()) {
            estado = EstadoPersonaje.DERROTADO;
            System.out.println("   " + nombre + " ha sido derrotado.");
        }
    }

    public void guardarEnMochila(Item item) throws CapacidadExcedidaException {
        mochila.agregar(item);
        System.out.println(nombre + " guarda " + item + " en la mochila.");
        if (armaEquipada == null || item.getPoder() > armaEquipada.getPoder()) {
            armaEquipada = item;
            System.out.println("Es mejor que lo que tenia, asi que se lo equipa.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public TipoPersonaje getTipoPersonaje() {
        return tipoPersonaje;
    }

    public EstadoPersonaje getEstado() {
        return estado;
    }

    public void setEstado(EstadoPersonaje estado) {
        // si ya cayo no se le puede cambiar el estado
        if (this.estado == EstadoPersonaje.DERROTADO) {
            return;
        }
        this.estado = estado;
    }

    public int getVida() {
        return corazon.getVidaActual();
    }

    public int getMana() {
        return corazon.getManaActual();
    }

    public Item getArmaEquipada() {
        return armaEquipada;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre + " (" + tipoPersonaje + ", nivel " + nivel + ") - " + estado + "\n");
        sb.append("   " + corazon + "\n");
        sb.append("   Arma: " + armaEquipada + " | Ataque: " + calcularAtaque() + "\n");
        sb.append("   Habilidades: ");
        if (habilidades.isEmpty()) {
            sb.append("ninguna todavia");
        } else {
            sb.append(habilidades);
        }
        sb.append("\n   Mochila (" + mochila.tamano() + "/" + mochila.getCapacidadMaxima() + "): " + mochila.getItems());
        return sb.toString();
    }
}
