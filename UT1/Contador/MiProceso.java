package exercises;

public class MiProceso implements Runnable {
    private String nombre;
    private int tiempo_ejecucion;

    public MiProceso(String nombre, int tiempo_ejecucion) {
        this.nombre = nombre;
        this.tiempo_ejecucion = tiempo_ejecucion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempo_ejecucion() {
        return tiempo_ejecucion;
    }

    public void setTiempo_ejecucion(int tiempo_ejecucion) {
        this.tiempo_ejecucion = tiempo_ejecucion;
    }

    private void ejecutar() {
        System.out.println("Ejecutando el proceso: " + this.getNombre());
         try {
             Thread.sleep(this.getTiempo_ejecucion()*1000);
         } catch (Exception e) {
             e.printStackTrace();
         }
         System.out.println("FIN del proceso: " + this.getNombre());
    }

    @Override
    public void run() {
        this.ejecutar();
    }
}
