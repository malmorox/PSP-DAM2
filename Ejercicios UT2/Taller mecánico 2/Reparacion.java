package exercises.taller2;

class Reparacion {
    String tipo;
    int tiempo; // segundos simulados
    double precio;

    public Reparacion(String tipo, int tiempo, double precio) {
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return tipo + " (" + tiempo + "s, " + precio + "€)";
    }
}