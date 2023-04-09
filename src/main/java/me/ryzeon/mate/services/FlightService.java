package me.ryzeon.mate.services;

import lombok.Getter;
import me.ryzeon.mate.model.IFlight;
import me.ryzeon.mate.model.impl.ConnectionFlight;
import me.ryzeon.mate.model.impl.DirectFlight;
import me.ryzeon.mate.service.IService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/4/23 @ 16:26
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class FlightService implements IService {

    @Getter
    private final List<IFlight> flights = new ArrayList<>();

    @Override
    public void enable() {
        // Peru Flights

        // Lima Flights
        registerDirectFlight("Lima", "Trujillo");
        registerDirectFlight("Lima", "Chiclayo");
        registerDirectFlight("Lima", "Piura");
        registerDirectFlight("Lima", "Iquitos");
        registerDirectFlight("Lima", "Juliaca");
        registerDirectFlight("Lima", "Cusco");
        registerDirectFlight("Lima", "Arequipa");

        // Cusco Flights
        registerDirectFlight("Cusco", "Puerto Maldonado");
        registerDirectFlight("Cusco", "Arequipa");

        // International Flights

        // Ciudad de Mexico Flights
        registerDirectFlight("Ciudad de Mexico", "San Salvador");
        registerDirectFlight("Ciudad de Mexico", "San Jose de Costa Rica");
        registerDirectFlight("Ciudad de Mexico", "Bogota");
        registerDirectFlight("Ciudad de Mexico", "Lima");

        // San Salvador Flights
        registerDirectFlight("San Salvador", "Cancun");
        registerDirectFlight("San Salvador", "Belice");
        registerDirectFlight("San Salvador", "San Pedro de Sula");
        registerDirectFlight("San Salvador", "Roatan");
        registerDirectFlight("San Salvador", "La Habana");
        registerDirectFlight("San Salvador", "Managua");
        registerDirectFlight("San Salvador", "Liberia");
        registerDirectFlight("San Salvador", "San Jose de Costa Rica");
        registerDirectFlight("San Salvador", "Ciudad de Panama");
        registerDirectFlight("San Salvador", "Medellin");
        registerDirectFlight("San Salvador", "Cali");
        registerDirectFlight("San Salvador", "Quito");
        registerDirectFlight("San Salvador", "Guayaquil");
        registerDirectFlight("San Salvador", "Lima");

        // Bogota Flights
        registerDirectFlight("Bogota", "San Salvador");
        registerDirectFlight("Bogota", "San Jose de Costa Rica");
        registerDirectFlight("Bogota", "Ciudad de Panama");
        registerDirectFlight("Bogota", "Ciudad de Guatemala");
        registerDirectFlight("Bogota", "Cancun");
        registerDirectFlight("Bogota", "Santo Domingo");
        registerDirectFlight("Bogota", "Punta Cana");
        registerDirectFlight("Bogota", "San Juan");

        // Lima Flights
        registerDirectFlight("Lima", "San Jose de Costa Rica");
        registerDirectFlight("Lima", "Guayaquil");
        registerDirectFlight("Lima", "Quito");
        registerDirectFlight("Lima", "Santo Domingo");

    }

    void registerDirectFlight(String origin, String destination) {
        IFlight flight = new DirectFlight(origin, destination);
        flights.add(flight);
        flights.add(flight.returnFlight()); // Add return flight
    }

    IFlight createConnectionFlight(String origin, String destination, IFlight connection) {
        for (IFlight flight : flights) {
            if (isDirectFlight(origin, destination, flight) || isPossibleConnectionFlight(origin, destination, flight)) {
                return flight;
            }
        }
        if (connection == null) return new DirectFlight(origin, destination);
        return new ConnectionFlight(origin, destination, connection);
    }

    @Override
    public void disable() {
        // Stuff to do when disabling the service
    }

    boolean isDirectFlight(String from, String to, IFlight flight) {
        return flight.origin().equalsIgnoreCase(from) && flight.destination().equalsIgnoreCase(to);
    }

    boolean isPossibleConnectionFlight(String from, String to, IFlight flight) {
        if (flight.origin().equalsIgnoreCase(from)) {
            if (flight.isDirectFlight() && flight.destination().equalsIgnoreCase(to)) return true;

            // Iterate into connections
            IFlight connection = flight.connection();
            while (connection != null) {
                if (connection.isDirectFlight() && connection.destination().equalsIgnoreCase(to)) return true;
                connection = connection.connection();
            }

        }
        return false;
    }


    public List<IFlight> searchFlights(String from, String to) {
        System.out.println("Searching flights from " + from + " to " + to);
        List<IFlight> flightsConnectFromPoint = flights.stream().filter(flight -> flight.origin().equalsIgnoreCase(from)).collect(Collectors.toList());
        System.out.println("Flights connect from " + from + " : " + flightsConnectFromPoint);
        List<IFlight> flightsConnectToEndpoint = flights.stream().filter(flight -> flight.destination().equalsIgnoreCase(to)).collect(Collectors.toList());
        System.out.println("Flights connect to " + to + " : " + flightsConnectToEndpoint);
        List<IFlight> possibleFlights = new ArrayList<>();

        // Busca vuelos directos entre los puntos de origen y destino
        for (IFlight flight : flights) {
            if (flight.origin().equalsIgnoreCase(from) && flight.destination().equalsIgnoreCase(to)) {
                possibleFlights.add(flight);
            }
        }


        for (IFlight firstFlight : flightsConnectFromPoint) {
            for (IFlight secondFlight : flightsConnectToEndpoint) {
                if (firstFlight.destination().equalsIgnoreCase(secondFlight.origin())) {
                    System.out.println("Found connection flight from " + from + " to " + to + " : " + firstFlight.origin() + " -> " + firstFlight.destination() + " -> " + secondFlight.destination());
                    ConnectionFlight connectionFlight = new ConnectionFlight(from, to, firstFlight);
                    possibleFlights.add(connectionFlight);
                } else {
                    List<IFlight> flightsConnectToFirstFlight = flights.stream().filter(flight -> flight.origin().equalsIgnoreCase(firstFlight.destination())).collect(Collectors.toList());
                    for (IFlight thirdFlight : flightsConnectToFirstFlight) {
                        if (thirdFlight.destination().equalsIgnoreCase(secondFlight.origin())) {
                            System.out.println("Found connection flight from " + from + " to " + to + " : " + firstFlight.origin() + " -> " + firstFlight.destination() + " -> " + thirdFlight.destination() + " -> " + secondFlight.destination());
                            ConnectionFlight connectionFlight = new ConnectionFlight(from, to, firstFlight);
                            possibleFlights.add(connectionFlight);
                        }
                    }
                }
            }
        }

        return possibleFlights;
    }

    public IFlight searchFlight(String from, String to) {
        return searchFlights(from, to).stream().findFirst().orElse(null);
    }
}
