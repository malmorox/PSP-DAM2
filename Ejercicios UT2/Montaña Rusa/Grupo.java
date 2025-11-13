package exercises.montanarusa;

public class Grupo {
    private static int contadorId = 0;

    private int id;
    private int tamanio;
    private boolean discapacidad;

    public Grupo(int tamanio, boolean discapacidad) {
        this.id = ++contadorId;
        this.tamanio = tamanio;
        this.discapacidad = discapacidad;
    }

    public int getId() {
        return id;
    }

    public int getTamanio() {
        return tamanio;
    }

    public boolean isDiscapacidad() {
        return discapacidad;
    }

    @Override
    public String toString() {
        return String.format("[Grupo %d: %d personas, %s discapacidad]", id, tamanio, discapacidad ? "CON" : "SIN");
    }
}
