package exercises.taller;

import java.util.concurrent.BlockingQueue;

public class Mecanico implements Runnable {
    private int id;
    private BlockingQueue<Reparacion> colaReparaciones;
    private double totalRecaudado = 0;
    private int trabajosHechos = 0;

    public Mecanico(int id, BlockingQueue<Reparacion> colaReparaciones){
        this.id = id;
        this.colaReparaciones = colaReparaciones;
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }

    public int getTrabajosHechos() {
        return trabajosHechos;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Reparacion reparacion = colaReparaciones.take();
                System.out.printf("%s repara: %s%n", Thread.currentThread().getName(), reparacion);

                trabajosHechos++;
                totalRecaudado += reparacion.getPrecio();

                Thread.sleep(reparacion.getTiempo() * 1000L);
                System.out.printf("%s finalizó: %s%n", Thread.currentThread().getName(), reparacion);
            }
        } catch (InterruptedException e) {
            System.out.printf("%s finalizó su jornada.%n", Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        }
    }
}
