package exercises.calendario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class HiloCalendario extends Thread {
    private final Calendario calendario;
    private final long intervaloMs;
    private volatile boolean running;

    public HiloCalendario(Calendario calendario, long intervaloMs) {
        this.calendario = calendario;
        this.intervaloMs = intervaloMs;
        this.running = true;
    }

    public void stopCalendar() {
        running = false;
    }

    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Locale locale = new Locale("es", "ES");

        while (running) {
            try {
                Thread.sleep(intervaloMs);
                calendario.avanzarDia();

                int dia = calendario.getDiaActual();
                LocalDate fecha = calendario.getFechaActual();
                String diaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);

                System.out.printf("Día %d -> %s (%s)%n",
                        dia, fecha.format(formatter), diaSemana);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Hilo interrumpido");
            }
        }

        System.out.println("Simulación detenida");
    }
}
