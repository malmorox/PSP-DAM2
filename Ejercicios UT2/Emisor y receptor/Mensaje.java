package exercises.mensajes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mensaje {
    private String msg;
    private TipoMensaje tipo;
    private LocalDateTime fecha;

    public Mensaje(String msg, TipoMensaje tipo) {
        this.msg = msg;
        this.tipo = tipo;
        this.fecha = LocalDateTime.now();
    }

    public String getMsg() {
        return msg;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public TipoMensaje getTipo() {
        return tipo;
    }

    private String formatearFecha() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fecha.format(formato);
    }

    @Override
    public String toString() {
        return "[" + tipo + "] " + msg + " (" + formatearFecha() + ")";
    }
}
