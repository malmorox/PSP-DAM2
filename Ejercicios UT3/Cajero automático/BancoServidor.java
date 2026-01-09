package exercises.cajero;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class BancoServidor {
    private static double saldo = 1000.0;

    public static void main(String[] args) {
        final int PUERTO = 5000;

        System.out.println("BancoServidor iniciado en el puerto " + PUERTO);
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {

            ExecutorService pool = Executors.newCachedThreadPool();

            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado desde " + cliente.getInetAddress());
                pool.execute(new ManejadorCajero(cliente));
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static synchronized double consultarSaldo() {
        return saldo;
    }

    private static synchronized double ingresar(double cantidad) {
        saldo += cantidad;
        return saldo;
    }

    private static synchronized boolean retirar(double cantidad) {
        if (cantidad > saldo) {
            return false;
        }
        saldo -= cantidad;
        return true;
    }

    private static class ManejadorCajero implements Runnable {
        private final Socket socket;

        public ManejadorCajero(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(
                            socket.getOutputStream(), true)
            ) {
                String mensaje;

                while ((mensaje = in.readLine()) != null) {
                    String[] partes = mensaje.split(":");

                    if (partes.length != 2) {
                        out.println("ERROR:Formato inválido");
                        continue;
                    }

                    String operacion = partes[0].toUpperCase();
                    double cantidad;

                    try {
                        cantidad = Double.parseDouble(partes[1]);
                    } catch (NumberFormatException e) {
                        out.println("ERROR:Cantidad inválida");
                        continue;
                    }

                    switch (operacion) {
                        case "CONSULTAR":
                            out.println("OK:Saldo actual es " +
                                    String.format("%.2f", consultarSaldo()) + "€");
                            break;

                        case "INGRESAR":
                            if (cantidad < 0) {
                                out.println("ERROR:Cantidad negativa");
                                break;
                            }
                            out.println("OK:Ingreso realizado. Nuevo saldo: " +
                                    String.format("%.2f", ingresar(cantidad)) + "€");
                            break;

                        case "RETIRAR":
                            if (cantidad < 0) {
                                out.println("ERROR:Cantidad negativa");
                                break;
                            }
                            if (retirar(cantidad)) {
                                out.println("OK:Retirada con éxito. Nuevo saldo: " +
                                        String.format("%.2f", consultarSaldo()) + "€");
                            } else {
                                out.println("ERROR:Saldo insuficiente");
                            }
                            break;

                        case "SALIR":
                            out.println("ADIOS:Gracias por su visita");
                            return;

                        default:
                            out.println("ERROR:Operación desconocida");
                    }
                }

            } catch (IOException e) {
                System.out.println("Cliente desconectado");
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }
}