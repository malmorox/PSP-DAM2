package exercises.barberia;

public class Barbero implements Runnable {
    private final Barberia barberia;

    public Barbero(Barberia barberia) {
        this.barberia = barberia;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Cliente cliente = barberia.esperarCliente();

                cortarPelo(cliente);

                barberia.terminarCorte();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void cortarPelo(Cliente cliente) throws InterruptedException {
        System.out.println("Cortando el pelo a " + cliente + "...");
        Thread.sleep(2000);
    }
}
