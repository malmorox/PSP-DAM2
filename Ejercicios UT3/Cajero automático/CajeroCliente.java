package exercises.cajero;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CajeroCliente {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 5000;

        try (
                Socket socket = new Socket(HOST, PUERTO);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true);
                Scanner sc = new Scanner(System.in)
        ) {
            System.out.println("Conectado al banco");

            boolean salir = false;

            while (!salir) {
                System.out.println("\n1. Consultar saldo");
                System.out.println("2. Ingresar");
                System.out.println("3. Retirar");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");

                String opcion = sc.nextLine();

                switch (opcion) {
                    case "1":
                        out.println("CONSULTAR:0");
                        mostrarRespuesta(in.readLine());
                        break;

                    case "2":
                        double ingreso = pedirCantidad(sc);
                        if (ingreso >= 0) {
                            out.println("INGRESAR:" + ingreso);
                            mostrarRespuesta(in.readLine());
                        }
                        break;

                    case "3":
                        double retirada = pedirCantidad(sc);
                        if (retirada >= 0) {
                            out.println("RETIRAR:" + retirada);
                            mostrarRespuesta(in.readLine());
                        }
                        break;

                    case "4":
                        out.println("SALIR:0");
                        mostrarRespuesta(in.readLine());
                        salir = true;
                        break;

                    default:
                        System.out.println("Opción no válida");
                }
            }

        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    private static double pedirCantidad(Scanner sc) {
        System.out.print("Introduzca cantidad: ");
        try {
            return Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Cantidad inválida");
            return -1;
        }
    }

    private static void mostrarRespuesta(String respuesta) {
        if (respuesta == null) {
            System.out.println("Sin respuesta del servidor");
            return;
        }

        String[] partes = respuesta.split(":", 2);
        if (partes.length == 2) {
            System.out.println(partes[1]);
        } else {
            System.out.println(respuesta);
        }
    }
}