package exercises.coches;

import java.util.Random;

public class Coche {
    private String matricula;

    public Coche() {
        this.matricula = generarMatriculaAleatoria();
    }

    public String getMatricula() {
        return matricula;
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
