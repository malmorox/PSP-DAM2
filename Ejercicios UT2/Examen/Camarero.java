package control1.marcos.almorox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Camarero implements Runnable {
    private int idMesa;
    private BlockingQueue<Pedido> colaPedidos;
    private List<Plato> carta;
    private double cuentaTotal = 0;

    public Camarero(int idMesa, BlockingQueue<Pedido> colaPedidos, List<Plato> carta) {
        this.idMesa = idMesa;
        this.colaPedidos = colaPedidos;
        this.carta = carta;
    }

    public double getCuentaTotal() {
        return cuentaTotal;
    }

    @Override
    public void run() {
        try {
            Random r = new Random();
            int numPlatos = 1 + r.nextInt(3);
            List<Plato> platosPedido = new ArrayList<>();

            for (int i = 0; i < numPlatos; i++) {
                Plato p = carta.get(r.nextInt(carta.size()));
                platosPedido.add(p);
                cuentaTotal += p.getPrecio();
            }

            Pedido pedido = new Pedido(idMesa, platosPedido, cuentaTotal);
            colaPedidos.put(pedido);
            System.out.printf("%s coge un pedido para %s%n", Thread.currentThread().getName(), pedido);
            pedido.esperarServido();

            System.out.printf("%s ha servido el pedido para %s%n", Thread.currentThread().getName(), pedido);
            Thread.sleep(15000 + r.nextInt(10000));

            System.out.println(mostrarFacturaMesa(idMesa, platosPedido, cuentaTotal));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String mostrarFacturaMesa(int idMesa, List<Plato> platos, Double cuentaTotal) {
        String cadena = " FACTURA DE MESA " + idMesa + "\n";
        for (Plato plato : platos) {
            cadena += plato.getNombre() + "      " + String.format("%.1f€", plato.getPrecio()) + " \n";
        }
        cadena += "TOTAL:  " + cuentaTotal;

        return cadena;
    }
}
