package exercises.parking;
import java.util.concurrent.BlockingQueue;

public class EntradaParking extends Thread {
    private final BlockingQueue<Coche> parking;
    private volatile boolean running = true;
    final int CAPACIDAD = 100;
    public int cochesEntrados = 0;

    public EntradaParking(BlockingQueue<Coche> parking) {
        this.parking = parking;
    }

    public void run() {
        try {
            while (running) {
                Coche coche = new Coche();
                parking.put(coche);
                cochesEntrados++;
                System.out.println("-> Entrada: [" + coche.getMatricula() + "] | Plazas ocupadas: "
                        + parking.size() + "/" + CAPACIDAD);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {

        }
    }

    public void detener() {
        running = false;
        this.interrupt();
    }
}
