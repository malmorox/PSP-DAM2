package exercises.montanarusa;

public class Atraccion {
    public static void main(String[] args) {
        MonitorMontanaRusa monitor = new MonitorMontanaRusa();

        Thread generador = new Thread(new GeneradorGrupos(monitor), "GeneradorGrupos");
        Thread montañaRusa = new Thread(new MontanaRusa(monitor), "MontañaRusa");

        generador.start();
        montañaRusa.start();
    }
}
