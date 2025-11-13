package exercises.barberia;

public class SimuladorBarberia {
    public static void main(String[] args) {
        Thread hiloBarbero = new Thread(new Barbero(), "Barbero");
    }
}
