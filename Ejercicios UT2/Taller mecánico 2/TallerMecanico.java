package exercises.taller2;

import java.util.concurrent.*;
import java.util.*;

//Programa principal
public class TallerMecanico {
 public static void main(String[] args) throws InterruptedException {
     // Catálogo de reparaciones
     List<Reparacion> catalogo = List.of(
             new Reparacion("Cambio aceite", 3, 50.0),
             new Reparacion("Frenos", 5, 120.0),
             new Reparacion("ITV", 4, 80.0),
             new Reparacion("Batería", 2, 60.0)
     );

     BlockingQueue<Reparacion> cola = new LinkedBlockingQueue<>();
     Map<String,ArrayList<Double>> trabajosRealizados = new ConcurrentHashMap<String, ArrayList<Double>>();

     // Crear mecánicos
     Mecanico m1 = new Mecanico( cola, trabajosRealizados);
     

     Thread h1 = new Thread(m1,"mecanico 1");
     Thread h2 = new Thread(m1,"mecanico 2");
     h1.start();
     h2.start();

     // Crear clientes
     List<Thread> clientes = new ArrayList<>();
     for (int i = 1; i <= 5; i++) {
         Thread c = new Thread(new Cliente("Cliente-" + i, cola, catalogo));
         clientes.add(c);
         c.start();
     }

     // Esperar a que terminen los clientes
     for (Thread c : clientes) c.join();

     // Dejar tiempo para que terminen los trabajos pendientes
     Thread.sleep(10000);

     // Terminar mecánicos
     h1.interrupt();
     h2.interrupt();
     h1.join();
     h2.join();

     // Mostrar resumen
     double totalDia = 0.0 ;
     int totalTrabajos = 0;
     
     for (Map.Entry<String, ArrayList<Double>> entry : trabajosRealizados.entrySet()) {
		String key = entry.getKey();
		ArrayList<Double> val = entry.getValue();
		System.out.println("El "+key+ " ha realizado "+ val.get(0)+" trabajos, con una recaudación de "+val.get(1));
		totalDia += val.get(1);
		totalTrabajos += val.get(0);
		
	}

     System.out.println("\n ---- RESUMEN DEL DÍA -----------");
     System.out.println("   Trabajos realizados: " + totalTrabajos);
     System.out.println("   Facturación total: " + totalDia + " €");
     System.out.println("  ----- Fin de la simulación. -----");
 }
}