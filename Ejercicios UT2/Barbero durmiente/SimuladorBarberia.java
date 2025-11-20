package exercises.barberia;

import java.util.List;

public class SimuladorBarberia {
    public static void main(String[] args) {
        Barberia barberia = new Barberia(3);
        Thread hiloBarbero = new Thread(new Barbero(barberia), "Barbero");
        hiloBarbero.start();

        List<String> nombreClientes = List.of("Ana", "Luis", "Marta", "Carlos", "Sofía", "Jorge", "Lucía", "Miguel");

        for (String nombre : nombreClientes) {
            Thread hiloCliente = new Thread(new Cliente(nombre, barberia), nombre);
            hiloCliente.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
