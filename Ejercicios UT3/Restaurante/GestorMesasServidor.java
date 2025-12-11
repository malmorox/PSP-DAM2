package exercises.restaurante;

import java.net.ServerSocket;
import java.net.Socket;

public class GestorMesasServidor {
    private static final int PUERTO = 8888;

    public static void main(String[] args) {
        GestorMesas gestorMesas = new GestorMesas();

        try (
            ServerSocket serverSocket = new ServerSocket(PUERTO);
        ) {
            System.out.println("Servidor central iniciado");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Se conecta una camarero desde: "+ clienteSocket.getInetAddress().getHostAddress());

                Runnable comanderoCamarero = new ComanderoCamarero(clienteSocket, gestorMesas);
                Thread hiloCamarero = new Thread(comanderoCamarero);
            }
        } catch (Exception e) {

        }
    }
}
