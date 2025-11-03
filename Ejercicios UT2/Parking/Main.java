package exercises.parking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int CAPACIDAD = 100;
        BlockingQueue<Coche> parking = new LinkedBlockingQueue<>(CAPACIDAD);

        EntradaParking entrada = new EntradaParking(parking);
        SalidaParking salida = new SalidaParking(parking);

        System.out.println("Iniciando simulación del parking (" + CAPACIDAD + " plazas, tarifa 0.05 €/minuto)");

        entrada.start();
        salida.start();

        Thread.sleep(30000);

        entrada.detener();
        salida.detener();

        entrada.join();
        salida.join();

        System.out.println("\nDeteniendo simulación...\n");
        System.out.println("Resumen final:");
        System.out.println("  Coches que entraron: " + entrada.cochesEntrados);
        System.out.println("  Coches que salieron: " + salida.cochesSalidos);
        System.out.printf("  Recaudación total: %.2f €%n", salida.recaudacion);
        System.out.println("  Coches restantes en parking: " + parking.size());
        System.out.println("Fin de la simulación.");
    }
}