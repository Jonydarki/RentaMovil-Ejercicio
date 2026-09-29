import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static Procesador procesador = new Procesador();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        cargarVehiculosIniciales();
        ejecutarMenu();
        scanner.close();
    }

    private static void cargarVehiculosIniciales() {
        procesador.registrarVehiculo(
            new Automovil("AUT001","Toyota","Corolla",250.00, 5, false)
        );

        procesador.registrarVehiculo(
            new Automovil("AUT002","Honda","Civic",300.00,5,true)
        );

        procesador.registrarVehiculo(
            new Motocicleta("MOT001","Honda","CB250",150.00, 250)
        );

        procesador.registrarVehiculo(
            new Motocicleta("MOT002","Yamaha","MT-03",200.00,321)
        );

        // Camioneta de 1.5 toneladas
        procesador.registrarVehiculo(
            new Camion("CAM001","Toyota","Hilux",200.00,1.5)
        );

        // Camioneta de 2.5 toneladas
        procesador.registrarVehiculo(
            new Camion("CAM002","Ford","Ranger",275.00,2.5)
        );
    }

    private static void ejecutarMenu() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opción: ");
            System.out.println();
            switch (opcion) {
                case 1:
                    registrarVehiculo();
                    break;
                case 2:
                    procesador.consultarFlota();
                    break;
                case 3:
                    cotizarAlquiler();
                    break;
                case 4:
                    confirmarAlquiler();
                    break;
                case 5:
                    registrarDevolucion();
                    break;
                case 6:
                    procesador.generarReporte();
                    break;
                case 7:
                    salir = true;
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println(
                            "Opción inválida. Intente nuevamente."
                    );
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("  Bienvenido a RENTAMOVIL  ");
        System.out.println("======================================");
        System.out.println("1. Registrar vehículo");
        System.out.println("2. Consultar flota");
        System.out.println("3. Cotizar alquiler");
        System.out.println("4. Confirmar alquiler");
        System.out.println("5. Registrar devolución");
        System.out.println("6. Mostrar reporte general");
        System.out.println("7. Salir");
        System.out.println("======================================");
    }

    private static void registrarVehiculo() {
        System.out.println("Registrar Vehiculo:");
        System.out.println("1. Automóvil");
        System.out.println("2. Motocicleta");
        System.out.println("3. Camioneta de carga");
        int tipo = leerEntero("Seleccione la categoría: ");
        if (tipo < 1 || tipo > 3) {
            System.out.println("Categoría inválida.");
            return;
        }

        String placa = leerTexto("Placa: ");
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        double tarifa = leerDoublePositivo(
                "Tarifa diaria: Q");

        Vehiculo vehiculo;

        switch (tipo) {

            case 1:

                int pasajeros = leerEnteroPositivo(
                        "Cantidad de pasajeros: "
                );

                boolean automatica = leerBooleano(
                        "¿La transmisión es automática? (s/n): "
                );

                vehiculo = new Automovil(
                        placa,
                        marca,
                        modelo,
                        tarifa,
                        pasajeros,
                        automatica
                );

                break;

            case 2:

                int cilindraje = leerEnteroPositivo(
                        "Cilindraje en cc: "
                );

                vehiculo = new Motocicleta(
                        placa,
                        marca,
                        modelo,
                        tarifa,
                        cilindraje
                );

                break;

            case 3:

                double capacidad = leerDoublePositivo(
                        "Capacidad máxima en toneladas: "
                );

                vehiculo = new Camion(
                        placa,
                        marca,
                        modelo,
                        tarifa,
                        capacidad
                );

                break;

            default:
                return;
        }

        if (procesador.registrarVehiculo(vehiculo)) {

            System.out.println(
                    "Vehículo registrado correctamente."
            );

        } else {

            System.out.println(
                    "No fue posible registrar el vehículo."
            );

            System.out.println(
                    "Verifique que los datos sean válidos " +
                    "y que la placa no esté repetida."
            );
        }
    }

    private static void cotizarAlquiler() {

        System.out.println("Cotizar Alquiler");

        String placa = leerTexto("Ingrese la placa: ");

        int dias = leerEnteroPositivo(
                "Cantidad de días: "
        );

        Vehiculo vehiculo = procesador.buscarVehiculo(placa);

        if (vehiculo == null) {

            System.out.println(
                    "No existe un vehículo con esa placa."
            );

            return;
        }

        double costo = procesador.cotizar(placa, dias);

        if (costo < 0) {

            System.out.println(
                    "No fue posible generar la cotización."
            );

            return;
        }

        System.out.println("\nCaracterísticas del vehículo:");
        System.out.println(vehiculo);

        System.out.println(
                "Costo total del alquiler: Q" +
                String.format("%.2f", costo)
        );
    }

    private static void confirmarAlquiler() {

        System.out.println("Confirmar Alquiler");

        String placa = leerTexto("Ingrese la placa: ");

        int dias = leerEnteroPositivo(
                "Cantidad de días: "
        );

        Vehiculo vehiculo = procesador.buscarVehiculo(placa);

        if (vehiculo == null) {

            System.out.println(
                    "No existe un vehículo con esa placa."
            );

            return;
        }

        // Primero se calcula y muestra la cotización
        double costo = procesador.cotizar(placa, dias);

        if (costo < 0) {

            System.out.println(
                    "No fue posible calcular el costo."
            );

            return;
        }

        System.out.println("\nVehículo:");
        System.out.println(vehiculo);

        System.out.println(
                "Total a pagar: Q" +
                String.format("%.2f", costo)
        );

        if (!vehiculo.estaDisponible()) {

            System.out.println(
                    "El vehículo está ocupado y " +
                    "no puede ser alquilado."
            );

            return;
        }

        boolean confirmar = leerBooleano(
                "¿Desea confirmar el alquiler? (s/n): "
        );

        if (!confirmar) {

            System.out.println(
                    "Alquiler cancelado. " +
                    "No se realizaron cambios."
            );

            return;
        }

        if (procesador.alquilar(placa, dias)) {

            System.out.println(
                    "Alquiler confirmado correctamente."
            );

            System.out.println(
                    "Vehículo ocupado."
            );

            System.out.println(
                    "Ingreso registrado: Q" +
                    String.format("%.2f", costo)
            );

        } else {

            System.out.println(
                    "No fue posible confirmar el alquiler."
            );
        }
    }

    private static void registrarDevolucion() {

        System.out.println(
                "Confirmar Devolucion"
        );

        String placa = leerTexto(
                "Ingrese la placa: "
        );

        Vehiculo vehiculo = procesador.buscarVehiculo(placa);

        if (vehiculo == null) {

            System.out.println(
                    "No existe un vehículo con esa placa."
            );

            return;
        }

        if (vehiculo.estaDisponible()) {

            System.out.println(
                    "El vehículo ya se encuentra disponible."
            );

            System.out.println(
                    "No se puede registrar la devolución."
            );

            return;
        }

        if (procesador.devolver(placa)) {

            System.out.println(
                    "Devolución registrada correctamente."
            );

            System.out.println(
                    "El vehículo vuelve a estar disponible."
            );

        } else {

            System.out.println(
                    "No fue posible registrar la devolución."
            );
        }
    }

    private static String leerTexto(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println(
                    "El valor no puede estar vacío."
            );
        }
    }

    private static int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Entrada inválida. " +
                        "Debe ingresar un número entero."
                );
            }
        }
    }

    private static int leerEnteroPositivo(String mensaje) {

        while (true) {

            int valor = leerEntero(mensaje);

            if (valor > 0) {
                return valor;
            }

            System.out.println(
                    "El valor debe ser mayor que cero."
            );
        }
    }

    private static double leerDoublePositivo(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {

                double valor = Double.parseDouble(
                        scanner.nextLine().trim()
                );

                if (valor > 0) {
                    return valor;
                }

                System.out.println(
                        "El valor debe ser mayor que cero."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Entrada inválida. " +
                        "Debe ingresar un número."
                );
            }
        }
    }

    private static boolean leerBooleano(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String respuesta = scanner.nextLine()
                    .trim()
                    .toLowerCase();

            if (respuesta.equals("s") ||
                respuesta.equals("si")) {

                return true;
            }

            if (respuesta.equals("n") ||
                respuesta.equals("no")) {

                return false;
            }

            System.out.println(
                    "Respuesta inválida. " +
                    "Utilice 's' o 'n'."
            );
        }
    }
}