package me.ryzeon.mate.model.flight;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/4/23 @ 15:31
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */

public interface IFlight {

    String destination();

    String origin();

    default boolean isDirectFlight() {
        return connection() == null;
    }

    default boolean isConnectionFlight() {
        return connection() != null;
    }

    IFlight connection();

    IFlight returnFlight();

    default int countConnections() {
        if (isDirectFlight()) {
            return 0;
        }
        return 1 + connection().countConnections();
    }

}
