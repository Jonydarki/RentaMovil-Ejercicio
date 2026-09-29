import java.util.ArrayList;

public class Procesador {
    private ArrayList<Vehiculo> vehiculos;
    private double ingresos;

    public Procesador() {
        vehiculos = new ArrayList<>();
        ingresos = 0.0;
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return false;
        }
        
        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty()) {
            return false;
        }
        
        if (vehiculo.getTarifaDiaria() <= 0) {
            return false;
        }

        if (buscarVehiculo(vehiculo.getPlaca()) != null) {
            return false;
        }

        if (vehiculo instanceof Automovil) {
            Automovil automovil = (Automovil) vehiculo;
            if (automovil.getPasajeros() <= 0) {
                return false;
            }
        } else if (vehiculo instanceof Motocicleta) {
            Motocicleta motocicleta = (Motocicleta) vehiculo;
            if (motocicleta.getCilindraje() <= 0) {
                return false;
            }
        } else if (vehiculo instanceof Camion) {
            Camion camioneta = (Camion) vehiculo;
            if (camioneta.getCapacidadToneladas() <= 0) {
                return false;
            }
        }

        vehiculos.add(vehiculo);
        return true;
    }

    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return null;
        }
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa.trim())) {
                return vehiculo;
            }
        }
        return null;
    }

    public void consultarFlota() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }
        System.out.println("\n========== CATALOGO DE RENTAMOVIL ==========");
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
        System.out.println("==========================================\n");
    }

    public double cotizar(String placa, int dias) {

        if (dias <= 0) {
            return -1;
        }
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo == null) {
            return -1;
        }
        return vehiculo.calcularCosto(dias);
    }

    public boolean alquilar(String placa, int dias) {
        if (dias <= 0) {
            return false;
        }
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo == null) {
            return false;
        }
        if (!vehiculo.estaDisponible()) {
            return false;
        }

        double costo = vehiculo.calcularCosto(dias);

        ingresos += costo;

        vehiculo.cambiarDisponibilidad(false);

        return true;
    }

    public boolean devolver(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo == null) {
            return false;
        }
        if (vehiculo.estaDisponible()) {
            return false;
        }
        vehiculo.cambiarDisponibilidad(true);
        return true;
    }

    public void generarReporte() {

        int totalAutomoviles = 0;
        int automovilesDisponibles = 0;
        int automovilesAlquilados = 0;

        int totalMotocicletas = 0;
        int motocicletasDisponibles = 0;
        int motocicletasAlquiladas = 0;

        int totalCamionetas = 0;
        int camionetasDisponibles = 0;
        int camionetasAlquiladas = 0;

        for (Vehiculo vehiculo : vehiculos) {

            if (vehiculo instanceof Automovil) {

                totalAutomoviles++;

                if (vehiculo.estaDisponible()) {
                    automovilesDisponibles++;
                } else {
                    automovilesAlquilados++;
                }

            } else if (vehiculo instanceof Motocicleta) {

                totalMotocicletas++;

                if (vehiculo.estaDisponible()) {
                    motocicletasDisponibles++;
                } else {
                    motocicletasAlquiladas++;
                }

            } else if (vehiculo instanceof Camion) {

                totalCamionetas++;

                if (vehiculo.estaDisponible()) {
                    camionetasDisponibles++;
                } else {
                    camionetasAlquiladas++;
                }
            }
        }

        System.out.println("\n========== REPORTE GENERAL ==========");

        System.out.println("\nAutomóviles:");
        System.out.println("  Registrados: " + totalAutomoviles);
        System.out.println("  Disponibles: " + automovilesDisponibles);
        System.out.println("  Alquilados: " + automovilesAlquilados);

        System.out.println("\nMotocicletas:");
        System.out.println("  Registradas: " + totalMotocicletas);
        System.out.println("  Disponibles: " + motocicletasDisponibles);
        System.out.println("  Alquiladas: " + motocicletasAlquiladas);

        System.out.println("\nCamionetas de carga:");
        System.out.println("  Registradas: " + totalCamionetas);
        System.out.println("  Disponibles: " + camionetasDisponibles);
        System.out.println("  Alquiladas: " + camionetasAlquiladas);

        System.out.println("\nIngresos acumulados: Q"
                + String.format("%.2f", ingresos));

        System.out.println("=====================================\n");
    }

    public double getIngresos() {
        return ingresos;
    }
}
