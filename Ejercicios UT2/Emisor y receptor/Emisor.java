package exercises.mensajes;

import java.util.concurrent.BlockingQueue;
import java.util.Random;

public class Emisor implements Runnable {
    private BlockingQueue<Mensaje> cola;
    private String[] mensajes = {
        "Mensaje de prueba 1",
        "Mensaje de prueba 2",
        "Mensaje de prueba 3",
        "Mensaje de prueba 4",
        "Mensaje de prueba 5"
    };
    Random random = new Random();

    public Emisor(BlockingQueue<Mensaje> cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            for (String msg : mensajes) {
                TipoMensaje tipo = TipoMensaje.values()[random.nextInt(TipoMensaje.values().length)];
                Mensaje mensaje = new Mensaje(msg, tipo);

                System.out.println("Emisor -> Enviando: " + mensaje);
                cola.put(mensaje);
                Thread.sleep(1000);
            }

            cola.put(new Mensaje("FIN", TipoMensaje.ALERTA));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}