public class Motocicleta extends Vehiculo {
    private int cilindraje;
    public Motocicleta(String placa, String marca, String modelo, double tarifaDiaria, int cilindraje) {
        super(placa, marca, modelo, tarifaDiaria);
        this.cilindraje = cilindraje;
    }

    public int getCilindraje() {
        return cilindraje;
    }

    @Override
    public double calcularCosto(int dias) {
        double costo = getTarifaDiaria() * dias;

        if (cilindraje > 250) {
            costo += 75;
        } return costo;
    }

    @Override
    public String toString() {
        String disponibilidad;

        if (estaDisponible()) {
            disponibilidad = "Disponible";
        } else {
            disponibilidad = "Alquilado";
        }
        return "Motocicleta{" +
                "placa='" + getPlaca() + '\'' +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", tarifa diaria=Q" + String.format("%.2f", getTarifaDiaria()) +
                ", cilindraje=" + cilindraje + " cc" +
                ", disponibilidad=" + disponibilidad +
                '}';
    }
}
