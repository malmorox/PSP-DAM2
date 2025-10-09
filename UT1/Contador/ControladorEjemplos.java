package exercises;

public class ControladorEjemplos {
    public static void main(String[] args) {
        MiProceso proc_1 = new MiProceso("Proceso 1", 6);
        MiProceso proc_2 = new MiProceso("Proceso 2", 6);
        MiProceso proc_3 = new MiProceso("Proceso 3", 6);

        Thread h1 = new Thread(proc_1);
        Thread h2 = new Thread(proc_2);
        Thread h3 = new Thread(proc_3);

        print(h1);
        print(h2);
        print(h3);

        h1.start();
        h2.start();
        h3.start();

        print(h1);
        print(h2);
        print(h3);

        try {
            while (h1.isAlive() | h2.isAlive() | h3.isAlive()) {
                print(h1);
                print(h2);
                print(h3);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        print(h1);
        print(h2);
        print(h3);
    }

    public static void print(Thread t) {
        System.out.println("El hilo " + t.getName() + " está en estado " + t.getState());
    }
}
