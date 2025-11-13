package exercises.montanarusa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorMontanaRusa {
    private final int CAPACIDAD_TREN = 24;
    private final int CUPO_DISCAPACIDAD = (int) (CAPACIDAD_TREN * 0.20);

    private final BlockingQueue<Grupo> colaPrioridad = new LinkedBlockingQueue<>();
    private final BlockingQueue<Grupo> colaNormal = new LinkedBlockingQueue<>();

    private final Lock lock = new ReentrantLock();
    private final Condition hayVisitantes = lock.newCondition();

    public void llegarGrupo(Grupo g) {
        lock.lock();
        try {
            if (g.isDiscapacidad()) {
                colaPrioridad.add(g);
            } else {
                colaNormal.add(g);
            }

            System.out.println("Llega " + g);
            hayVisitantes.signal();
        } finally {
            lock.unlock();
        }
    }

    public List<Grupo> cargarTren() throws InterruptedException {
        lock.lock();
        try {
            while (colaPrioridad.isEmpty() && colaNormal.isEmpty()) {
                System.out.println("Montaña rusa esperando visitantes...");
                hayVisitantes.await();
            }

            int plazasLibres = CAPACIDAD_TREN;
            int discapacidadRestante = CUPO_DISCAPACIDAD;

            List<Grupo> embarcados = new ArrayList<>();

            List<Grupo> snapshotPrioridad = new ArrayList<>(colaPrioridad); // copia para poder “saltar”
            int i = 0;
            while (i < snapshotPrioridad.size() && plazasLibres > 0 && discapacidadRestante > 0) {
                Grupo g = snapshotPrioridad.get(i);

                if (g.getTamanio() <= plazasLibres && g.getTamanio() <= discapacidadRestante) {
                    if (colaPrioridad.remove(g)) {
                        embarcados.add(g);
                        plazasLibres -= g.getTamanio();
                        discapacidadRestante -= g.getTamanio();
                    }
                }
                i++;
            }

            List<Grupo> snapshotNormal = new ArrayList<>(colaNormal);
            i = 0;
            while (i < snapshotNormal.size() && plazasLibres > 0) {
                Grupo g = snapshotNormal.get(i);

                if (g.getTamanio() <= plazasLibres) {
                    if (colaNormal.remove(g)) {
                        embarcados.add(g);
                        plazasLibres -= g.getTamanio();
                    }
                }
                i++;
            }

            int plazasOcupadas = CAPACIDAD_TREN - plazasLibres;
            int discapacidadUsada = CUPO_DISCAPACIDAD - discapacidadRestante;

            System.out.printf(
                    "%n--- CARGA COMPLETADA --- %nPlazas ocupadas: %d / %d (discapacidad: %d / %d)%n%n",
                    plazasOcupadas, CAPACIDAD_TREN,
                    discapacidadUsada, CUPO_DISCAPACIDAD
            );

            for (Grupo g : embarcados) {
                System.out.println("Sube al tren -> " + g);
            }

            return embarcados;

        } finally {
            lock.unlock();
        }
    }
}
