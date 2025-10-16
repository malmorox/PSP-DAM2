package exercises.contador;

public class Controlador {
    public static void main(String[] args) {
        Contador contador = new Contador();

        HiloContador hilo1 = new HiloContador("hilo-1", contador, Thread.MAX_PRIORITY);
        HiloContador hilo2 = new HiloContador("hilo-2", contador, Thread.NORM_PRIORITY);

        hilo1.start();
        hilo2.start();

        try {
            hilo1.join();
            hilo2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Valor final del contador: " + contador.getValor());
        for (Acceso acceso : contador.obtenerAccesosTotales()) {
            System.out.println(acceso);
        }
    }
}
