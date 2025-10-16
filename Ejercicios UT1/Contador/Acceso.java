package exercises.contador;

public class Acceso {
    private String nombreHilo;
    private int contadorAccesos;

    public Acceso(String nombreHilo) {
        super();
        this.nombreHilo = nombreHilo;
        this.contadorAccesos = 0;
    }

    public void incrementoAcceso() {
        this.contadorAccesos++;
    }

    public String getNombreHilo() {
        return nombreHilo;
    }

    public int getContadorAccesos() {
        return contadorAccesos;
    }

    @Override
    public String toString() {
        return nombreHilo + " -> " + contadorAccesos;
    }
}
