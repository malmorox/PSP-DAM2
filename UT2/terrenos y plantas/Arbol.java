package exercises.plantas;

public class Arbol extends Planta {
    public Arbol(String nombre) {
        super(nombre);
    }

    @Override
    public void crecer(int dias) {
        for (int i = 0; i < dias; i++) {
            if (aguaDisponible >= consumoDiarioAgua()) {
                altura += 1;
                aguaDisponible-= consumoDiarioAgua();
            } else {
                System.out.println(nombre + " necesita agua para crecer.");
            }
        }
    }

    @Override
    protected int consumoDiarioAgua() {
        return 5;
    }

    @Override
    public String tipo() {
        return "Árbol";
    }

    @Override
    public String estado() {
        return String.format("%s (%s): Altura=%d - Agua disponible=%d", nombre, tipo(), altura, aguaDisponible);
    }
}
