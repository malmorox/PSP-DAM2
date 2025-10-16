package exercises.contador;

import java.util.ArrayList;

public class Contador {
    private int contador;
    private ArrayList<Acceso> accesos;

    public Contador() {
        super();
        this.contador = 0;
        this.accesos = new ArrayList<>();
    }

    public synchronized void incrementar(String nombreHilo) {
        this.contador++;

        Acceso accesoExistente = null;
        for (Acceso a : accesos) {
            if (a.getNombreHilo().equals(nombreHilo)) {
                accesoExistente = a;
                break;
            }
        }

        if (accesoExistente != null) {
            accesoExistente.incrementoAcceso();
        } else {
            Acceso nuevo = new Acceso(nombreHilo);
            nuevo.incrementoAcceso();
            accesos.add(nuevo);
        }
    }

    public int getValor() {
        return this.contador;
    }

    public ArrayList<Acceso> obtenerAccesosTotales() {
        return this.accesos;
    }
}
