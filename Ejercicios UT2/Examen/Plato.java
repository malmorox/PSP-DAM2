package control1.marcos.almorox;

public class Plato {
    private String nombre;
    private int tiempoPreparacion;
    private double precio;

    public Plato(String nombre, int tiempoPreparacion, double precio) {
        this.nombre = nombre;
        this.tiempoPreparacion = tiempoPreparacion;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return String.format("%s (%.1f€)", nombre, precio);
    }
}
