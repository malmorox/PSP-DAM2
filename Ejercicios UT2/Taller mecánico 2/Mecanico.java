package exercises.taller2;

import java.util.concurrent.*;
import java.util.*;

//Mecánico: consume trabajos
class Mecanico implements Runnable {
 private final BlockingQueue<Reparacion> cola;
 private final Map<String,ArrayList<Double>> trabajosRealizados;


 public Mecanico( BlockingQueue<Reparacion> cola, Map<String,ArrayList<Double>> trabajosRealizados) {
     this.trabajosRealizados = trabajosRealizados;
     this.cola = cola;
 }

 @Override
 public void run() {
     try {
         while (true) {
             Reparacion rep = cola.take();
             System.out.println("" + Thread.currentThread().getName() + " repara: " + rep);
             Thread.sleep(rep.tiempo * 1000L);
             if(!trabajosRealizados.containsKey(Thread.currentThread().getName())) {
            	 ArrayList<Double> tmp = new ArrayList<Double>();
            	 tmp.add(0.0);
            	 tmp.add(0.0);
            	 trabajosRealizados.put(Thread.currentThread().getName(), tmp );
             }
             double contadorTrabajos = trabajosRealizados.get(Thread.currentThread().getName()).get(0);
             contadorTrabajos += 1;
             trabajosRealizados.get(Thread.currentThread().getName()).set(0,contadorTrabajos);
             
             double recaudacion = trabajosRealizados.get(Thread.currentThread().getName()).get(1);
             recaudacion += rep.precio;
             trabajosRealizados.get(Thread.currentThread().getName()).set(1,recaudacion);
             
            
             
             System.out.println("" + Thread.currentThread().getName() + " finalizó: " + rep);
         }
     } catch (InterruptedException e) {
         System.out.println("" + Thread.currentThread().getName() + " terminó su jornada.");
     }
 }


}