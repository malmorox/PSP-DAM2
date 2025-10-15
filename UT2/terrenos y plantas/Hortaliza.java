package exercises.plantas;

public class Hortaliza extends Planta {
    public Hortaliza(String nombre) {
        super(nombre);
    }

    @Override
    public void crecer(int dias) {
        for (int i = 0; i < dias; i++) {
            if (aguaDisponible >= consumoDiarioAgua()) {
                altura += 2;
                aguaDisponible-= consumoDiarioAgua();
            } else {
                System.out.println(nombre + " necesita agua para crecer.");
            }
        }
    }

    @Override
    protected int consumoDiarioAgua() {
        return 1;
    }

    @Override
    protected int crecimientoDiario() { return 3; }

    @Override
    public String tipo() {
        return "Hortaliza";
    }

    @Override
    public String estado() {
        return String.format("%s (%s): Altura=%d, AguaDisponible=%d", nombre, tipo(), altura, aguaDisponible);
    }
}
