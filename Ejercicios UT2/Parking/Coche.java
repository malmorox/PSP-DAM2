package exercises.parking;

import java.util.Random;

public class Coche {
    private String matricula;
    private final long horaEntrada;

    public Coche() {
        this.matricula = generarMatriculaAleatoria();
        this.horaEntrada = System.currentTimeMillis();
    }

    public String getMatricula() {
        return matricula;
    }

    public long getHoraEntrada() {
        return horaEntrada;
    }

    private String generarMatriculaAleatoria() {
        Random rand = new Random();
        int numeros = 1000 + rand.nextInt(9000);
        String letras = "";
        for (int i = 0; i < 3; i++) {
            letras += (char) ('A' + rand.nextInt(26));
        }
        return numeros + letras;
    }
}
