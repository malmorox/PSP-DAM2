package exercises.taller2;

import java.util.concurrent.*;
import java.util.*;

//Cliente: produce trabajos
class Cliente implements Runnable {
 private final BlockingQueue<Reparacion> cola;
 private final List<Reparacion> catalogo;
 private final String nombre;

 public Cliente(String nombre, BlockingQueue<Reparacion> cola, List<Reparacion> catalogo) {
     this.nombre = nombre;
     this.cola = cola;
     this.catalogo = catalogo;
 }

 @Override
 public void run() {
     try {
         Random r = new Random();
         int numReparaciones = 1 + r.nextInt(3);
         System.out.println("" + nombre + " llega al taller con " + numReparaciones + " reparaciones.");

         for (int i = 0; i < numReparaciones; i++) {
             Reparacion rep = catalogo.get(r.nextInt(catalogo.size()));
             cola.put(rep);
             System.out.println("" + nombre + " solicita: " + rep);
             Thread.sleep(500);
         }
     } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
     }
 }
}