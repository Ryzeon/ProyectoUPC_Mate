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
    }

    public IFlight searhForFlight(String from, String to) {
        for (IFlight flight : flights) {

        }
        throw new IllegalArgumentException("No flight found from " + from + " to " + to);
    }


    private Map<String, Set<String>> buildGraph(List<IFlight> flights) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (IFlight flight : flights) {
            String origin = flight.origin();
            String destination = flight.destination();
            graph.putIfAbsent(origin, new HashSet<>());
            graph.putIfAbsent(destination, new HashSet<>());
            graph.get(origin).add(destination);
            graph.get(destination).add(origin);
        }
        return graph;
    }

    void calculateConnections() {
        Map<String, Set<String>> graph = buildGraph(Arrays.asList(flights.toArray(new IFlight[0])));
        List<IFlight> connections = new ArrayList<>();
        for (String city : graph.keySet()) {
            Set<String> visited = new HashSet<>();
            dfs(city, city, visited, graph, connections);
        }

        // Remove connections flight if are alrady in list
        connections.removeIf(flights::contains);
        flights.addAll(connections);
    }

    void dfs(String start, String current, Set<String> visited, Map<String, Set<String>> graph, List<IFlight> connections) {
//        visited.add(current);
//        for (String neighbor : graph.get(current)) {
//            if (!visited.contains(neighbor)) {
//                IFlight connection = createConnectionFlight(start, neighbor, searchFlights(connections, current, neighbor).stream().findFirst().orElse(null));
//                connections.add(connection);
//                dfs(start, neighbor, visited, graph, connections);
//            }
//        }
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
