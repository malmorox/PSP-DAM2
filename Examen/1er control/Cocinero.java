package exercises.examen1;

import java.util.concurrent.BlockingQueue;

public class Cocinero implements Runnable {
    private BlockingQueue<Pedido> colaPedidos;

    public Cocinero(BlockingQueue<Pedido> colaPedidos) {
        this.colaPedidos = colaPedidos;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Pedido pedido = colaPedidos.take();
                System.out.println(Thread.currentThread().getName() + " toma el pedido para " + pedido);
                for (Plato plato : pedido.getPlatos()) {
                    System.out.println(Thread.currentThread().getName() + " empieza a preparar: " + plato + " para la mesa " + pedido.getIdMesa());
                    Thread.sleep(plato.getTiempoPreparacion() * 1000L);
                }
                System.out.println(Thread.currentThread().getName() + " termina el pedido para " + pedido);
                pedido.marcarServido();
            }
        } catch (InterruptedException e) {
            System.out.printf("%s termino de cocinar.%n", Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        }
    }
}
