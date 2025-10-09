package exercises;

public class ProcesoSimulado implements Runnable {
    private String nombre;

    public ProcesoSimulado(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void ejecutar(String nombreHilo) {
        Thread hiloActual = Thread.currentThread();

        for (int i = 1; i <= 3; i++) {
            System.out.println("El " + nombreHilo + " del proceso " + this.getNombre()
                    + " está en estado: " + hiloActual.getState());

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        Thread hilo1 = new Thread(() -> ejecutar("Hilo 1"));
        Thread hilo2 = new Thread(() -> ejecutar("Hilo 2"));

        hilo1.start();
        hilo2.start();

        try {
            hilo1.join();
            hilo2.join();


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Proceso " + this.getNombre() + " finalizado.");
    }
}
