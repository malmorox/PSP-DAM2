package exercises.chat;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServidorChat {
    private static final int PUERTO = 9999;
    private static Map<String, ManejadorCliente> clientesConectados = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("=== SERVIDOR DE CHAT INICIADO ===");
        System.out.println("Esperando conexiones en puerto " + PUERTO);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Nueva conexión desde: " + clienteSocket.getInetAddress().getHostAddress());

                ManejadorCliente manejador = new ManejadorCliente(clienteSocket);
                new Thread(manejador).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    // Métodos para gestionar clientes
    public static synchronized boolean registrarCliente(String nombreUsuario, ManejadorCliente manejador) {
        if (clientesConectados.containsKey(nombreUsuario)) {
            return false; // Usuario ya existe
        }
        clientesConectados.put(nombreUsuario, manejador);
        System.out.println("Usuario registrado: " + nombreUsuario + " (Total: " + clientesConectados.size() + ")");
        return true;
    }

    public static synchronized void desconectarCliente(String nombreUsuario) {
        clientesConectados.remove(nombreUsuario);
        System.out.println("Usuario desconectado: " + nombreUsuario + " (Total: " + clientesConectados.size() + ")");
    }

    public static synchronized void difundirMensaje(String mensaje, String remitente) {
        System.out.println("[DIFUSIÓN] " + remitente + ": " + mensaje);
        for (Map.Entry<String, ManejadorCliente> entry : clientesConectados.entrySet()) {
            if (!entry.getKey().equals(remitente)) {
                entry.getValue().enviarMensaje("[" + remitente + "]: " + mensaje);
            }
        }
    }

    public static synchronized void enviarMensajePrivado(String mensaje, String remitente, String destinatario) {
        ManejadorCliente cliente = clientesConectados.get(destinatario);
        if (cliente != null) {
            cliente.enviarMensaje("[PRIVADO de " + remitente + "]: " + mensaje);
            ManejadorCliente remitenteCliente = clientesConectados.get(remitente);
            if (remitenteCliente != null) {
                remitenteCliente.enviarMensaje("[PRIVADO a " + destinatario + "]: " + mensaje);
            }
        } else {
            ManejadorCliente remitenteCliente = clientesConectados.get(remitente);
            if (remitenteCliente != null) {
                remitenteCliente.enviarMensaje("[ERROR] Usuario " + destinatario + " no encontrado");
            }
        }
    }

    public static synchronized String obtenerListaUsuarios() {
        if (clientesConectados.isEmpty()) {
            return "No hay usuarios conectados";
        }
        StringBuilder lista = new StringBuilder("Usuarios conectados:\n");
        for (String usuario : clientesConectados.keySet()) {
            lista.append("  - ").append(usuario).append("\n");
        }
        return lista.toString();
    }

    public static synchronized void notificarConexion(String nombreUsuario) {
        String mensaje = ">>> " + nombreUsuario + " se ha unido al chat";
        System.out.println(mensaje);
        for (Map.Entry<String, ManejadorCliente> entry : clientesConectados.entrySet()) {
            if (!entry.getKey().equals(nombreUsuario)) {
                entry.getValue().enviarMensaje(mensaje);
            }
        }
    }

    public static synchronized void notificarDesconexion(String nombreUsuario) {
        String mensaje = "<<< " + nombreUsuario + " ha salido del chat";
        System.out.println(mensaje);
        for (ManejadorCliente cliente : clientesConectados.values()) {
            cliente.enviarMensaje(mensaje);
        }
    }
}

class ManejadorCliente implements Runnable {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private String nombreUsuario;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Solicitar nombre de usuario
            salida.println("Bienvenido al chat. Introduce tu nombre de usuario:");
            nombreUsuario = entrada.readLine();

            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                salida.println("Nombre de usuario inválido. Desconectando...");
                cerrarConexion();
                return;
            }

            nombreUsuario = nombreUsuario.trim();

            // Intentar registrar el cliente
            if (!ServidorChat.registrarCliente(nombreUsuario, this)) {
                salida.println("ERROR: El nombre de usuario ya está en uso. Desconectando...");
                cerrarConexion();
                return;
            }

            // Notificar conexión exitosa
            salida.println("¡Conectado exitosamente como " + nombreUsuario + "!");
            salida.println("Comandos disponibles:");
            salida.println("  /usuarios - Ver usuarios conectados");
            salida.println("  /privado <usuario> <mensaje> - Enviar mensaje privado");
            salida.println("  /salir - Salir del chat");
            salida.println("------------------------------------");

            ServidorChat.notificarConexion(nombreUsuario);

            // Procesar mensajes
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                procesarMensaje(mensaje.trim());
            }

        } catch (IOException e) {
            System.err.println("Error con cliente " + nombreUsuario + ": " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    private void procesarMensaje(String mensaje) {
        if (mensaje.isEmpty()) {
            return;
        }

        if (mensaje.startsWith("/")) {
            procesarComando(mensaje);
        } else {
            ServidorChat.difundirMensaje(mensaje, nombreUsuario);
        }
    }

    private void procesarComando(String comando) {
        String[] partes = comando.split(" ", 3);
        String cmd = partes[0].toLowerCase();

        switch (cmd) {
            case "/usuarios":
                enviarMensaje(ServidorChat.obtenerListaUsuarios());
                break;

            case "/privado":
                if (partes.length < 3) {
                    enviarMensaje("[ERROR] Uso: /privado <usuario> <mensaje>");
                } else {
                    String destinatario = partes[1];
                    String mensajePrivado = partes[2];
                    ServidorChat.enviarMensajePrivado(mensajePrivado, nombreUsuario, destinatario);
                }
                break;

            case "/salir":
                enviarMensaje("Desconectando...");
                desconectar();
                break;

            default:
                enviarMensaje("[ERROR] Comando desconocido: " + cmd);
        }
    }

    public void enviarMensaje(String mensaje) {
        if (salida != null) {
            salida.println(mensaje);
        }
    }

    private void desconectar() {
        if (nombreUsuario != null) {
            ServidorChat.desconectarCliente(nombreUsuario);
            ServidorChat.notificarDesconexion(nombreUsuario);
        }
        cerrarConexion();
    }

    private void cerrarConexion() {
        try {
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}