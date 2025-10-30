package exercises.mensajes;

import java.time.LocalDateTime;

public enum TipoMensaje {
    ALERTA,
    INFORMACION,
    CONSULTA;

    @Override
    public String toString() {
        switch (this) {
            case ALERTA:
                return "Alerta";
            case INFORMACION:
                return "Información";
            case CONSULTA:
                return "Consulta";
            default:
                return this.toString();
        }
    }
}
