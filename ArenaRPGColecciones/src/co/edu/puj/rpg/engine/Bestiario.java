package co.edu.puj.rpg.engine;

import java.util.HashMap;
import java.util.Map;

public class Bestiario {

    private Map<String, Integer> bestiarioMap;

    public Bestiario() {
        this.bestiarioMap = new HashMap<>();
    }

    public int registrarDerrota(String enemigo) {
        int veces = bestiarioMap.getOrDefault(enemigo, 0) + 1;
        bestiarioMap.put(enemigo, veces);
        return veces;
    }

    public boolean yaFueDerrotado(String enemigo) {
        return bestiarioMap.containsKey(enemigo);
    }

    public int getDerrotas(String enemigo) {
        if (!bestiarioMap.containsKey(enemigo)) {
            return 0;
        }
        return bestiarioMap.get(enemigo);
    }

    public void mostrar() {
        System.out.println("\n--- BESTIARIO ---");
        if (bestiarioMap.isEmpty()) {
            System.out.println("Todavia no has derrotado a ningun enemigo.");
            return;
        }
        System.out.printf("%-20s %s%n", "Enemigo", "Derrotados");
        for (Map.Entry<String, Integer> entry : bestiarioMap.entrySet()) {
            System.out.printf("%-20s %d%n", entry.getKey(), entry.getValue());
        }
    }
}
