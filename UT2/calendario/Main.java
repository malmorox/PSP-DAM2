package exercises.calendario;
import java.time.LocalDate;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        LocalDate fechaBase;

        fechaBase = LocalDate.now();

        Calendario calendario = new Calendario(fechaBase);
        HiloCalendario hilo = new HiloCalendario(calendario, 1000);
        hilo.start();

        System.out.println("Simulación iniciada. Pulsa ENTER para detener...");

        try (Scanner sc = new Scanner(System.in)) {
            sc.nextLine();
        }

        hilo.stopCalendar();
    }
}
