package me.ryzeon.mate.model.flight.impl;

import lombok.Setter;
import lombok.ToString;
import me.ryzeon.mate.model.flight.IFlight;

import java.util.Objects;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/4/23 @ 16:48
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@ToString
public class ConnectionFlight implements IFlight {

    private final String origin, destination;

    @Setter
    private IFlight connection;

    public ConnectionFlight(String origin, String destination, IFlight connection) {
        this.origin = origin;
        this.destination = destination;
        this.connection = connection;
    }

    @Override
    public String destination() {
        return destination;
    }

    @Override
    public String origin() {
        return origin;
    }

    @Override
    public IFlight connection() {
        return connection;
    }

    @Override
    public IFlight returnFlight() {
        return new ConnectionFlight(destination, origin, connection.returnFlight());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConnectionFlight)) {
            return false;
        }
        ConnectionFlight other = (ConnectionFlight) obj;
        return Objects.equals(origin, other.origin)
                && Objects.equals(destination, other.destination)
                && Objects.equals(connection, other.connection);
    }
}
