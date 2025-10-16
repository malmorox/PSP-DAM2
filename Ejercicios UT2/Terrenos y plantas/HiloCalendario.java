package exercises.plantas;
import java.util.List;

public class HiloCalendario extends Thread {
    private Calendario calendario;
    private List<Terreno> terrenos;

    public HiloCalendario(Calendario calendario, List<Terreno> terrenos) {
        this.calendario = calendario;
        this.terrenos = terrenos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                synchronized (calendario) {
                    calendario.avanzarDia();
                    System.out.println("\n- Día " + calendario.getDiaActual() + " -");
                    for (Terreno t : terrenos) {
                        Vegetal v = t.getPlanta();
                        if (v instanceof Planta p && p.viva()) {
                            p.crecer(1);
                        }
                    }
                    calendario.notifyAll();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
