package exercises.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final int PUERTO = 12345;

    public static void main(String[] args) {
        try (
                ServerSocket serverSocket = new ServerSocket(PUERTO);
        ) {
            System.out.println("Servidor iniciado. Esperando conexión en el puerto " + PUERTO + "...");

            Socket clienteSocket = serverSocket.accept();
            System.out.println("Cliente conectado desde: " + clienteSocket.getInetAddress());

            BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            PrintStream salida = new PrintStream(clienteSocket.getOutputStream(), true);

            String mensajeCliente;

            while ((mensajeCliente = entrada.readLine()) != null) {
                System.out.println("Mensaje recibido del cliente: " + mensajeCliente);

                //Respuesta para el cliente
                String respuesta = "ECHO: " + mensajeCliente;
                salida.println(respuesta);

                if (mensajeCliente.equalsIgnoreCase("FIN")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor");
        }
    }
}
