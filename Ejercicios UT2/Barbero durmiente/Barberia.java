package exercises.barberia;

import exercises.montanarusa.Grupo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Barberia {
    private final int NUM_SILLAS_ESPERA;
    private final BlockingQueue<Cliente> SALA_ESPERA = new LinkedBlockingQueue<>();

    private Cliente clienteEnSilla = null;

    public Barberia(int numSillasEspera) {
        this.NUM_SILLAS_ESPERA = numSillasEspera;
    }

    public synchronized void entrarCliente(Cliente cliente) throws InterruptedException {
        System.out.println(cliente + " llega a la barbería");

        if(clienteEnSilla == null && SALA_ESPERA.isEmpty()) {
            clienteEnSilla = cliente;
            System.out.println(cliente + " encuentra al barbero dormido, lo despierta y se sienta en la silla de barbero");
            notifyAll();
            return;
        }

        if (SALA_ESPERA.size() < NUM_SILLAS_ESPERA) {
            SALA_ESPERA.add(cliente);
            System.out.println(cliente + " se sienta en la sala de espera. Esperando: " + SALA_ESPERA.size());
            notifyAll();
            return;
        }

        System.out.println(cliente + " no encuentra sitio y se va");
    }

    public synchronized Cliente esperarCliente() throws InterruptedException {
        while (clienteEnSilla == null && SALA_ESPERA.isEmpty()) {
            System.out.println("El barbero se duerme esperando clientes...");
            wait();
        }

        if (clienteEnSilla == null && !SALA_ESPERA.isEmpty()) {
            clienteEnSilla = SALA_ESPERA.poll();
            System.out.println("El barbero llama a " + clienteEnSilla + " de la sala de espera a la silla de barbero");
        }

        return clienteEnSilla;
    }

    public synchronized void terminarCorte() {
        System.out.println("El barbero ha terminado el corte de pelo de " + clienteEnSilla + " y se va");
        clienteEnSilla = null;
        notifyAll();
    }
}
