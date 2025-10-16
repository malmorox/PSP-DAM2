package exercises.simulacion;

public class SimuladorProcesos {
    public static void main(String[] args) {
        Thread procesoA = new Thread(new ProcesoSimulado("A"));
        Thread procesoB = new Thread(new ProcesoSimulado("B"));

        procesoA.start();
        procesoB.start();

        try {
            procesoA.join();
            procesoB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Simulación de todos los procesos finalizada.");
    }
}