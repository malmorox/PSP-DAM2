package exercises.calendario;
import exercises.plantas.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calendario calendario = new Calendario();
        List<Terreno> terrenos = new ArrayList<>();

        Terreno t1 = new Terreno();
        t1.sembrar(new Hortaliza("Zanahoria"));

        Terreno t2 = new Terreno();
        t2.sembrar(new Arbol("Roble"));

        terrenos.add(t1);
        terrenos.add(t2);

        HiloCalendario hc = new HiloCalendario(calendario, terrenos);
        hc.start();

        HiloMonitor hm = new HiloMonitor(terrenos);
        hm.start();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nOpciones: (1) Regar Zanahoria (2) Regar Roble (3) Salir");
            String op = sc.nextLine();

            if (op.equals("1"))
                ((Planta) t1.getPlanta()).regar();
            else if (op.equals("2"))
                ((Planta) t2.getPlanta()).regar();
            else if (op.equals("3")) {
                System.out.println("Finalizando simulación...");
                hc.interrupt();
                hm.interrupt();
                break;
            }
        }

        sc.close();
    }
}
