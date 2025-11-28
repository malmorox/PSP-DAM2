package exercises.montanarusa;

public class Atraccion {
    public static void main(String[] args) {
        MonitorMontanaRusa monitor = new MonitorMontanaRusa();

        Thread generador = new Thread(new GeneradorGrupos(monitor), "Generador de grupos");
        Thread montanaRusa = new Thread(new MontanaRusa(monitor), "Montaña rusa");

        generador.start();
        montanaRusa.start();
    }
}
