package exercises.taller;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.Random;

public class Cliente implements Runnable {
    private int id;
    private BlockingQueue<Reparacion> colaReparaciones;
    private List<Reparacion> reparacionesPosibles;
    private Random random = new Random();

    public Cliente(int id, BlockingQueue<Reparacion> colaReparaciones, List<Reparacion> reparacionesPosibles) {
        this.id = id;
        this.colaReparaciones = colaReparaciones;
        this.reparacionesPosibles = reparacionesPosibles;
    }

    @Override
    public void run() {
        int reparacionesAleatorias = 1 + random.nextInt(3);
        System.out.printf("%s llega al taller con %d reparaciones.%n", Thread.currentThread().getName(), reparacionesAleatorias);

        for (int i = 0; i < reparacionesAleatorias; i++) {
            try {
                Reparacion plantilla = reparacionesPosibles.get(random.nextInt(reparacionesPosibles.size()));
                Reparacion reparacion = new Reparacion(plantilla.getTipo(), plantilla.getTiempo(), plantilla.getPrecio());

                colaReparaciones.put(reparacion);

                System.out.printf("%s solicita: %s%n", Thread.currentThread().getName(), reparacion);
                Thread.sleep(100 + random.nextInt(900));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
