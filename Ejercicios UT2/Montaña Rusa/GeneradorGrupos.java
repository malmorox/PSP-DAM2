package exercises.montanarusa;

import java.util.Random;

public class GeneradorGrupos implements Runnable {
    private MonitorMontanaRusa monitor;
    Random random = new Random();

    public GeneradorGrupos(MonitorMontanaRusa monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int numPersonasGrupo;
                boolean discapacidad = random.nextBoolean();
                numPersonasGrupo = discapacidad ? 1 + random.nextInt(10) : 1 + random.nextInt(4);

                Grupo grupo = new Grupo(numPersonasGrupo, discapacidad);
                monitor.llegarGrupo(grupo);

                Thread.sleep(500 + random.nextInt(1500));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
