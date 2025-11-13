package exercises.montanarusa;

import java.util.List;

public class MontanaRusa implements Runnable {
    private MonitorMontanaRusa monitor;

    public MontanaRusa(MonitorMontanaRusa monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        int numViaje = 0;

        try {
            while (true) {
                numViaje++;
                System.out.println("\n===== INICIO VIAJE " + numViaje + " =====");

                List<Grupo> pasajeros = monitor.cargarTren();

                if (pasajeros.isEmpty()) {
                    System.out.println("Viaje " + numViaje + " cancelado (sin pasajeros).");
                    continue;
                }

                System.out.println(">>> ¡Empieza el viaje " + numViaje + "! <<<");
                Thread.sleep(2000);

                System.out.println("<<< Fin del viaje " + numViaje + ". Pasajeros bajan. >>>");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
