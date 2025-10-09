package exercises;

public class HiloContador extends Thread {
    Contador contador;
    String nombre;

    public HiloContador(String nombre, Contador contador, int prioridad) {
        this.contador = contador;
        this.setPriority(prioridad);
        this.nombre = nombre;
    }

    @Override
    public void run() {
        while (this.contador.getValor() < 2000) {
            this.contador.incrementar(this.nombre);
        }
    }
}
