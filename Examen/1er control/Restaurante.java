package exercises.examen1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurante {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Pedido> colaPedidos = new LinkedBlockingQueue<>();

        List<Plato> carta = List.of(
            new Plato("Codornices en vinagre", 3, 34.1),
            new Plato("Ensalada de burrata", 5, 9.99),
            new Plato("Huevos revueltos", 4, 12.5),
            new Plato("Cocido madrileño", 2, 23.0),
            new Plato("Risotto de cecina", 7, 18.70),
            new Plato("Paella valeciana (2p)", 6,45.95),
            new Plato("Solomillo de buey", 5, 25.5)
        );

        final int NUM_MESAS = 5;
        final int NUM_COCINEROS = 2;

        List<Thread> hilosCamareros = new ArrayList<>();
        List<Camarero> camareros = new ArrayList<>();
        for (int i = 1; i <= NUM_MESAS; i++) {
            Camarero c = new Camarero(i, colaPedidos, carta);
            camareros.add(c);
            Thread t = new Thread(c, "Camarero-" +i);
            hilosCamareros.add(t);
            t.start();
        }

        List<Thread> hilosCocineros = new ArrayList<>();
        for (int i = 1; i <= NUM_COCINEROS; i++) {
            Cocinero c = new Cocinero(colaPedidos);
            Thread t = new Thread(c, "Cocinero-" +i);
            hilosCocineros.add(t);
            t.start();
        }

        for (Thread t : hilosCamareros) t.join();
        Thread.sleep(5000);
        for (Thread t : hilosCocineros) t.interrupt();
        for (Thread t : hilosCocineros) t.join();

        double facturacionTotalDia = 0;

        for (Camarero c : camareros) {
            facturacionTotalDia += c.getCuentaTotal();
        }
        Thread.sleep(5000);
        System.out.println("-- RESUMEN DEL DÍA --");
        System.out.printf(" Facturación total del día: %.1f€%n", facturacionTotalDia);
        System.out.println("-- RESTAURANTE CERRADO --");
    }
}
