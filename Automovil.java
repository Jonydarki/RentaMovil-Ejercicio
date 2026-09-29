public class Automovil extends Vehiculo {
    private int pasajeros;
    private boolean transmisionAutomatica;
    public Automovil(String placa, String marca, String modelo, double tarifaDiaria, int pasajeros, boolean transmisionAutomatica) {
        super(placa, marca, modelo, tarifaDiaria);
        this.pasajeros = pasajeros;
        this.transmisionAutomatica = transmisionAutomatica;
    }

    public int getPasajeros() {
        return pasajeros;
    }

    public boolean tieneTransmisionAutomatica() {
        return transmisionAutomatica;
    }

    @Override
    public double calcularCosto(int dias) {
        double costoBase = getTarifaDiaria() * dias;
        if (transmisionAutomatica) {
            costoBase += 50 * dias;
        }
        return costoBase;
    }

    @Override
    public String toString() {
        String transmision;
        if (transmisionAutomatica) {
            transmision = "Automática";
        } else {
            transmision = "Manual";
        }

        String disponibilidad;

        if (estaDisponible()) {
            disponibilidad = "Disponible";
        } else {
            disponibilidad = "Alquilado";
        }

        return "Automóvil{" +
                "placa='" + getPlaca() + '\'' +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", tarifa diaria=Q" + String.format("%.2f", getTarifaDiaria()) +
                ", pasajeros=" + pasajeros +
                ", transmisión=" + transmision +
                ", disponibilidad=" + disponibilidad +
                '}';
    }
}
