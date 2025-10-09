package exercises.plantas;

public abstract class Planta implements Vegetal {
    protected String nombre;
    protected int altura;
    protected int aguaDisponible;
    protected int diasSinAgua;
    protected final int DIAS_SIN_AGUA_MAX = 3;
    private boolean viva = true;

    public Planta(String nombre) {
        this.nombre = nombre;
        this.altura = 0;
        this.aguaDisponible = 0;
    }

    @Override
    public void regar() {
        aguaDisponible++;
    }

    abstract int consumoDiarioAgua();

    public boolean viva() {
        return viva;
    }
}

