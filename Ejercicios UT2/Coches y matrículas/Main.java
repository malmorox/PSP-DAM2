package exercises.coches;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Coche> colaAcceso = new LinkedBlockingQueue<>();

        int maxCoches = 10;
        for(int i = 0; i < maxCoches; i++) {
            Coche coche = new Coche();
            colaAcceso.put(coche);
            System.out.println(coche.getMatricula() + " ha entrado. Plazas ocupadas: " + colaAcceso.size());
        }
    }
}
