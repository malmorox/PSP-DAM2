package exercises.plantas;
import java.util.List;

public class HiloMonitor extends Thread {
    private List<Terreno> terrenos;

    public HiloMonitor(List<Terreno> terrenos) {
        this.terrenos = terrenos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                System.out.println("\nEstado de los terrenos:");
                for (Terreno t : terrenos) {
                    t.mostrarEstado();
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
