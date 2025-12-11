package exercises.restaurante;

import java.util.Arrays;

public class GestorMesas {
    private static final int NUM_MESAS = 5;

    private final boolean[] estadoMesas = new boolean[NUM_MESAS];

    public GestorMesas() {
        Arrays.fill(estadoMesas, false);
        System.out.println("Se crean " + NUM_MESAS + " mesas del restaurante");
    }

    public synchronized int asignarMesa() {
        for (int i = 0; i < NUM_MESAS; i++) {
            if(!estadoMesas[i]) {
                estadoMesas[i] = true;
                return i;
            }
        }

        return -1;
    }

    public synchronized boolean liberarMesa(int numeroMesa) {
        if(numeroMesa<1 || numeroMesa>NUM_MESAS || !estadoMesas[numeroMesa-1]) {
            return false;
        }

        estadoMesas[numeroMesa-1] = false;
        return true;
    }
}
