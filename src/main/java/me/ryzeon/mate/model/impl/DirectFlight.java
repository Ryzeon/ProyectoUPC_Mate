package me.ryzeon.mate.model.impl;

import lombok.ToString;
import me.ryzeon.mate.model.IFlight;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/4/23 @ 16:31
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@ToString
public class DirectFlight implements IFlight {
    private final String destination;
    private final String origin;

    public DirectFlight(String origin, String destination) {
        this.destination = destination;
        this.origin = origin;
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
        // "This flight is not a connection flight"
        return null;
    }

    @Override
    public IFlight returnFlight() {
        return new DirectFlight(destination, origin);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DirectFlight) {
            DirectFlight flight = (DirectFlight) obj;
            return (flight.origin().equals(origin) && flight.destination().equals(destination))
                    || (flight.origin().equals(destination) && flight.destination().equals(origin));
        }
        return false;
    }
}
