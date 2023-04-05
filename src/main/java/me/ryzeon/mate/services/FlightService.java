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

        calculateConnections();

        System.out.println("Flights: " + flights.size());
        System.out.println("Direct Flights: " + flights.stream().filter(flight -> flight instanceof DirectFlight).collect(Collectors.toSet()).size());
        System.out.println("Connection Flights: " + flights.stream().filter(flight -> flight instanceof ConnectionFlight).collect(Collectors.toSet()).size());
        System.out.println("Priting connections:");
        flights.stream().filter(flight -> flight instanceof ConnectionFlight).forEach(System.out::println);
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
        flights.addAll(connections);
    }

    void dfs(String start, String current, Set<String> visited, Map<String, Set<String>> graph, List<IFlight> connections) {
        visited.add(current);
        for (String neighbor : graph.get(current)) {
            if (!visited.contains(neighbor)) {
                connections.add(createConnectionFlight(start, neighbor, searchFlights(connections, current, neighbor).get(0)));
                dfs(start, neighbor, visited, graph, connections);
            }
        }
    }

    void registerDirectFlight(String origin, String destination) {
        flights.add(new DirectFlight(origin, destination));
    }

    IFlight createConnectionFlight(String origin, String destination, IFlight connection) {
        Optional<IFlight> directFlight = flights.stream()
                .filter(f -> f instanceof DirectFlight)
                .filter(f -> f.origin().equalsIgnoreCase(origin) && f.destination().equalsIgnoreCase(destination))
                .findFirst();

        return directFlight.orElse(new ConnectionFlight(origin, destination, connection));
    }


    @Override
    public void disable() {


    }


    public List<IFlight> searchFlights(List<IFlight> flights, String from, String to) {
        List<IFlight> flightsList;
        if (flights == null)
            flightsList = this.flights;
        else {
            flightsList = flights;
            flightsList.addAll(this.flights);
        }

        // Check if from to contains in origin or destination
        return flightsList
                .stream()
                .filter(flight ->
                        isDirectFlight(from, to, flight) || isPosibleConnectionFlight(from, to, flight)
                )
                // if is return flight clone class and change origin and destination
                .map(flight -> {
                    if (flight.origin().equalsIgnoreCase(to) && flight.destination().equalsIgnoreCase(from)) {
                        return flight.returnFlight();
                    }
                    return flight;
                })
                .collect(Collectors.toList());
    }

    boolean isDirectFlight(String from, String to, IFlight flight) {
        if (flight.origin().equalsIgnoreCase(from) && flight.destination().equalsIgnoreCase(to)) return true;
        if (flight.origin().equalsIgnoreCase(to) && flight.destination().equalsIgnoreCase(from)) return true;
        return false;
    }

    boolean isPosibleConnectionFlight(String from, String to, IFlight flight) {
        IFlight returnFlight = flight.returnFlight();
        if (flight.origin().equalsIgnoreCase(from) || returnFlight.origin().equalsIgnoreCase(from)) {
            if (flight.isDirectFlight() && flight.destination().equalsIgnoreCase(to)) return true;
            if (returnFlight.isDirectFlight() && returnFlight.destination().equalsIgnoreCase(to)) return true;

            // Iterate into connections
            IFlight connection = flight.connection();
            while (connection != null) {
                if (connection.isDirectFlight() && connection.destination().equalsIgnoreCase(to)) return true;
                connection = connection.returnFlight();
                if (connection.isDirectFlight() && connection.destination().equalsIgnoreCase(to)) return true;
                connection = connection.connection();
            }

        }
        return false;
    }

    public IFlight searchFlight(String from, String to) {
        return searchFlights(null, from, to).stream().findFirst().orElse(null);
    }
}
