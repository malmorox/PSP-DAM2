package exercises.barberia;

public class Cliente implements Runnable {
    private final String nombre;
    private final Barberia barberia;

    public Cliente(String nombre, Barberia barberia) {
        this.nombre = nombre;
        this.barberia = barberia;
    }

    @Override
    public void run() {
        try {
            barberia.entrarCliente(this);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
