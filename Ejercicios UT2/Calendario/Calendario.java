package exercises.calendario;
import java.time.LocalDate;

public class Calendario {
    private int diaActual = 0;
    private LocalDate fechaBase;

    public Calendario(LocalDate fechaBase) {
        this.fechaBase = fechaBase;
    }

    public synchronized void avanzarDia() {
        diaActual++;
    }

    public synchronized int getDiaActual() {
        return diaActual;
    }

    public synchronized LocalDate getFechaActual() {
        return fechaBase.plusDays(diaActual);
    }
}

