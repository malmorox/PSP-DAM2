package exercises.parking;
import java.util.concurrent.BlockingQueue;
import java.util.Random;

public class SalidaParking extends Thread {
    private final BlockingQueue<Coche> parking;
    private volatile boolean running = true;
    public int cochesSalidos = 0;
    public double recaudacion = 0.0;
    private static final double TARIFA_MINUTO = 0.05;

    public SalidaParking(BlockingQueue<Coche> parking) {
        this.parking = parking;
    }

    public void run() {
        Random rnd = new Random();
        try {
            while (running) {
                Coche coche = parking.take();
                int tiempoEstancia = 5 + rnd.nextInt(11);
                Thread.sleep(tiempoEstancia * 1000L);
                double coste = tiempoEstancia * TARIFA_MINUTO;
                cochesSalidos++;
                recaudacion += coste;
                System.out.printf("<- Salida: [%s] | Estancia: %d min | Coste: %.2f €%n",
                        coche.getMatricula(), tiempoEstancia, coste);
            }
        } catch (InterruptedException e) {

        }
    }

    public void detener() {
        running = false;
        this.interrupt();
    }
}