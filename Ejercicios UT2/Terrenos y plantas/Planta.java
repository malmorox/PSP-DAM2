package exercises.plantas;

public abstract class Planta implements Vegetal {
    protected String nombre;
    protected int altura;
    protected int aguaDisponible;
    protected int diasSinAgua;
    protected final int DIAS_SIN_AGUA_MAX = 3;
    private boolean viva;

    public Planta(String nombre) {
        this.nombre = nombre;
        this.altura = 0;
        this.aguaDisponible = 5;
        this.viva = true;
    }

    @Override
    public void regar() {
        if (viva) {
            aguaDisponible += 5;
            System.out.println(nombre + " se ha regado. Agua disponible: " + aguaDisponible);
        }
    }

    public boolean viva() {
        return viva;
    }

    abstract int consumoDiarioAgua();

    abstract int crecimientoDiario();
}

