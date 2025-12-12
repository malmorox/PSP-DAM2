package exercises.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteChat {
    private static final String IP_SERVIDOR = "localhost";
    private static final int PUERTO = 12345;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(IP_SERVIDOR, PUERTO);

                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Scanner sc = new Scanner(System.in)
        ) {
            System.out.println("Conectando al servidor en " + IP_SERVIDOR + ":" + PUERTO);

            String mensajeUsuario;
            String respuestaServidor;

            do {
                System.out.print("Ingrese mensaje (o FIN para terminar): ");
                mensajeUsuario = sc.nextLine();

                salida.println(mensajeUsuario);

                respuestaServidor = entrada.readLine();
                System.out.println("Servidor dice: " + respuestaServidor);
            } while (!mensajeUsuario.equalsIgnoreCase("FIN"));
        } catch (UnknownHostException e) {
            System.err.println("Host no encontrado");
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor");
        }
    }
}
