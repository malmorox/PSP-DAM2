package exercises.examen1;

import java.util.List;

public class Pedido {
    private int idMesa;
    private List<Plato> platos;
    private double total;
    private boolean servido = false;

    public Pedido(int idMesa, List<Plato> platos, double total) {
        this.idMesa = idMesa;
        this.platos = platos;
        this.total = total;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public List<Plato> getPlatos() {
        return platos;
    }

    public double getTotal() {
        return total;
    }

    public synchronized void marcarServido() {
        servido = true;
        notify();
    }

    public synchronized void esperarServido() throws InterruptedException {
        while (!servido) {
            wait();
        }
    }

    @Override
    public String toString() {
        return String.format("la mesa %d (%.1f€)", idMesa, total);
    }
}
