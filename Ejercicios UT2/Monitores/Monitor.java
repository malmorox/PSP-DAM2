package exercises.monitor;

import java.util.ArrayList;
import java.util.List;

public class Monitor<T> {
    private List<T> lista = new ArrayList<>();
    private final int CAPACIDAD = 10;

    public synchronized void poner(T elemento) throws InterruptedException {
        while (lista.size() == CAPACIDAD) {
            wait();
        }
        lista.add(elemento);
        notifyAll();
    }

    public synchronized T recuperar() throws InterruptedException {
        while (lista.isEmpty()) {
            wait();
        }
        T elemento = lista.remove(0);
        notifyAll();
        return elemento;
    }

}
