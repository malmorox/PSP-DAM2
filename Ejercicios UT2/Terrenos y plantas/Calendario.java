package exercises.plantas;

public class Calendario {
    private int diaActual = 0;

    public synchronized void avanzarDia() {
        diaActual++;
    }

    public synchronized int getDiaActual() {
        return diaActual;
    }
}
