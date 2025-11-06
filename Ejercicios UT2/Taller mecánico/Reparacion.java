package exercises.taller;

public class Reparacion {
    private String tipo;
    private int tiempo;
    private double precio;

    public Reparacion(String tipo, int tiempo, double precio) {
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTiempo() {
        return tiempo;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return String.format("%s (%ds, %.1f€)", tipo, tiempo, precio);
    }
}
