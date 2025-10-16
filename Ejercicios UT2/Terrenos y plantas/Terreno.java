package exercises.plantas;

public class Terreno {
    private Vegetal planta;

    public void sembrar(Vegetal v) {
        this.planta = v;
        System.out.println("Se ha sembrado una planta de tipo: " + v.tipo());
    }

    void mostrarEstado() {
        if (planta != null) {
            System.out.println(planta.estado());
        } else {
            System.out.println("No hay ninguna planta sembrada en el terreno");
        }
    }

    public Vegetal getPlanta() {
        return planta;
    }
}
