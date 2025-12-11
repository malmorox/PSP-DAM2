package exercises.restaurante;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CamareroCliente {
    private static final String IP_SERVIDOR = "localhost";
    private static final int PUERTO_SERVIDOR = 8888;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(IP_SERVIDOR, PUERTO_SERVIDOR);

                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Scanner sc = new Scanner(System.in)
        ) {
            boolean ejecutando = true;
            while (true) {
                mostrarMenu();
                if (!sc.hasNextInt()) {
                    System.err.println("Entrada invalida");
                    sc.nextLine();
                    continue;
                }

                int opcion = sc.nextInt();
                String ordenEnvio = "";

                switch (opcion) {
                    case 1:
                        System.out.println("Cuantos comensales?");
                        int comensalas = sc.nextInt();
                        ordenEnvio = "SENTAR;" + comensalas;
                        break;
                    case 2:
                        System.out.println("Que mesa se levanta?");
                        int numeroMesa = sc.nextInt();
                        ordenEnvio = "LEVANTAR;" + numeroMesa;
                    case 3:
                        ejecutando = false;
                        break;
                    default:
                        System.err.println("Opcion no valida");
                }

                if (!ordenEnvio.isEmpty()) {
                    salida.println(ordenEnvio);
                    String respuestaServidor = entrada.readLine();
                    procesarRespuesta(respuestaServidor);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Host no encontrado");
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor");
        }
    }

    public static void mostrarMenu() {
        System.out.println("---- Sistema Camarero ----");
        System.out.println("1. ");
        System.out.println("2. ");
        System.out.println("3. Salir");
    }

    private String procesarRespuesta(String respuestaServidor) {}
}
