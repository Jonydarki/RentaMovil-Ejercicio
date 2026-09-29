public class Camion extends Vehiculo {
    private double capacidadToneladas;

    public Camion(String placa, String marca, String modelo, double tarifaDiaria, double capacidadToneladas) {
        super(placa, marca, modelo, tarifaDiaria);
        this.capacidadToneladas = capacidadToneladas;
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }

    @Override
    public double calcularCosto(int dias) {
        double costoBase = getTarifaDiaria() * dias;
        double recargo = 100 * capacidadToneladas * dias;
        return costoBase + recargo;
    }

    @Override
    public String toString() {
        String disponibilidad;
        if (estaDisponible()) {
            disponibilidad = "Disponible";
        } else {
            disponibilidad = "Alquilado";
        }

        return "Camioneta de carga{" +
                "placa='" + getPlaca() + '\'' +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", tarifa diaria=Q" + String.format("%.2f", getTarifaDiaria()) +
                ", capacidad=" + String.format("%.2f", capacidadToneladas) + " toneladas" +
                ", disponibilidad=" + disponibilidad +
                '}';
    }
}