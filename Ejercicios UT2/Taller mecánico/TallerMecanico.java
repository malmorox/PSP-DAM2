package exercises.taller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TallerMecanico {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Reparacion> colaReparaciones = new LinkedBlockingQueue<>();

        List<Reparacion> reparacionesPosibles = List.of(
            new Reparacion("Cambio de aceite", 2, 35.00),
            new Reparacion("Cambio de frenos", 4, 150.00),
            new Reparacion("Alineación de dirección", 3, 60.00),
            new Reparacion("Sustitución de batería", 2, 95.00),
            new Reparacion("Revisión general (ITV)", 5, 120.00),
            new Reparacion("Cambio de neumáticos", 4, 260.00),
            new Reparacion("Diagnóstico electrónico", 2, 50.00),
            new Reparacion("Correa de distribución", 6, 450.00)
        );

        final int NUM_CLIENTES = 5;
        final int NUM_MECANICOS = 3;

        List<Thread> hilosClientes = new ArrayList<>();
        for (int i = 1; i <= NUM_CLIENTES; i++) {
            Thread t = new Thread(new Cliente(i, colaReparaciones, reparacionesPosibles), "Cliente-" +i);
            hilosClientes.add(t);
            t.start();
        }

        List<Thread> hilosMecanicos = new ArrayList<>();
        List<Mecanico> mecanicos = new ArrayList<>();
        for (int i = 1; i <= NUM_MECANICOS; i++) {
            Mecanico m = new Mecanico(i, colaReparaciones);
            mecanicos.add(m);
            Thread t = new Thread(m, "Mecánico-" +i);
            hilosMecanicos.add(t);
            t.start();
        }

        for (Thread t : hilosClientes) t.join();
        Thread.sleep(5000);
        for (Thread t : hilosMecanicos) t.interrupt();
        for (Thread t : hilosMecanicos) t.join();

        double totalDiaRecaudado = 0;
        int trabajosDiaHechos = 0;

        for (Mecanico m : mecanicos) {
            totalDiaRecaudado += m.getTotalRecaudado();
            trabajosDiaHechos += m.getTrabajosHechos();
        }

        System.out.println("RESUMEN DEL DÍA");
        System.out.println(" Trabajos realizados: " + trabajosDiaHechos);
        System.out.printf(" Facturación total: %.1f€%n", totalDiaRecaudado);
        System.out.println("Fin de la simulación.");
    }
}
